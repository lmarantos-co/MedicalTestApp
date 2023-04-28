package com.example.cvdriskestimator.Fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentPDQFirstCategoryCheckBinding
import com.example.cvdriskestimator.viewModels.PDQCheckViewModel
import com.example.cvdriskestimator.viewModels.CheckGPDQPatientViewModelFactory


private const val ARG_PARAM1 = "patientData"


class PDQCheckFirstCategoryFragment : Fragment() {

    private lateinit var pdqCheckBinding: FragmentPDQFirstCategoryCheckBinding
    private var pdqSecondCategoryFragment = PDQCheckSecondCategoryFragment()
    private lateinit var pdqPatientViewModel: PDQCheckViewModel
    private lateinit var pdqPatientViewModelFactory : CheckGPDQPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections : IntArray = IntArray(10)


    private lateinit var viewModel: PDQCheckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pdqCheckBinding = FragmentPDQFirstCategoryCheckBinding.inflate(inflater, container, false)
        pdqPatientViewModel = PDQCheckViewModel()
        pdqPatientViewModelFactory = CheckGPDQPatientViewModelFactory()
        pdqPatientViewModel =
            ViewModelProvider(this, pdqPatientViewModelFactory).get(PDQCheckViewModel::class.java)
        pdqCheckBinding.let {
            it.pdqCheckViewModel = pdqPatientViewModel
            it.lifecycleOwner = this
        }
        return pdqCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pdqPatientViewModel.passActivity(mainActivity)
        pdqPatientViewModel.passFragment(this)
        pdqPatientViewModel.initialiseRealm()

        pdqCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
        if (userName != "tempUser")
        {
            pdqPatientViewModel.setPatientDataOnForm(userName!!)
        }
        else
        {
            pdqPatientViewModel.initialiseUserDummy()
            pdqPatientViewModel.setPatientDataOnForm(userName)
        }

        if (savedInstanceState!= null)
        {
            var allPatientData = savedInstanceState!!.getIntArray("patientData")
            if (allPatientData != null)
            {
                initialiseFormWithRunningPatientData(allPatientData)
            }
        }


        pdqPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        pdqPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        pdqCheckBinding.rightArrowImgV.setOnClickListener {
          allPatientSelections[0] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ1RG)
          allPatientSelections[1] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ2RG)
          allPatientSelections[2] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ3RG)
          allPatientSelections[3] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ4RG)
          allPatientSelections[4] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ5RG)
          allPatientSelections[5] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ6RG)
          allPatientSelections[6] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ7RG)
          allPatientSelections[7] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ8RG)
          allPatientSelections[8] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ9RG)
          allPatientSelections[9] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ10RG)
            if (pdqPatientViewModel.checkPDQTestPatient(allPatientSelections))
            {

                var bundle = Bundle()
                bundle.putIntArray("allPatientData" , allPatientSelections)

                pdqSecondCategoryFragment.arguments = bundle
                fragmentTransaction(pdqSecondCategoryFragment)
            }

        }

        pdqCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(pdqCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null ,leaderBoardFragment)

        pdqCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        pdqCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        pdqCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        pdqCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }
    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(pdqCheckBinding.pdqQ1RG , test.patientPDQQ1)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ2RG , test.patientPDQQ2)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ3RG , test.patientPDQQ3)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ4RG , test.patientPDQQ4)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ5RG , test.patientPDQQ5)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ6RG , test.patientPDQQ6)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ7RG , test.patientPDQQ7)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ8RG , test.patientPDQQ8)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ9RG , test.patientPDQQ9)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ10RG , test.patientPDQQ10)
        } , 1000)
    }

    fun initialisePatientData()
    {
        pdqCheckBinding.pdqQ1RG.clearCheck()
        pdqCheckBinding.pdqQ2RG.clearCheck()
        pdqCheckBinding.pdqQ3RG.clearCheck()
        pdqCheckBinding.pdqQ4RG.clearCheck()
        pdqCheckBinding.pdqQ5RG.clearCheck()
        pdqCheckBinding.pdqQ6RG.clearCheck()
        pdqCheckBinding.pdqQ7RG.clearCheck()
        pdqCheckBinding.pdqQ8RG.clearCheck()
        pdqCheckBinding.pdqQ9RG.clearCheck()
        pdqCheckBinding.pdqQ10RG.clearCheck()

    }

    private fun initialiseFormWithRunningPatientData(allPatientData : IntArray)
    {
        setQuestionRadioGroup(pdqCheckBinding.pdqQ1RG , allPatientData.get(0))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ2RG , allPatientData.get(1))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ3RG , allPatientData.get(2))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ4RG , allPatientData.get(3))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ5RG , allPatientData.get(4))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ6RG , allPatientData.get(5))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ7RG , allPatientData.get(6))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ8RG , allPatientData.get(7))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ9RG , allPatientData.get(8))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ10RG , allPatientData.get(9))
    }

    private fun setQuestionRadioGroup(rg : RadioGroup  , answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            0 ->
            {
                (rg.getChildAt(0) as RadioButton).isChecked = true
            }
            1 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
            2 ->
            {
                (rg.getChildAt(2) as RadioButton).isChecked = true
            }
            3->
            {
                (rg.getChildAt(3) as RadioButton).isChecked = true
            }
            4 ->
            {
                (rg.getChildAt(4) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAnswerFromRadioGroup(rg : RadioGroup) : Int
    {
        //get the Checkid
        var checkId = rg.checkedRadioButtonId
        when (checkId)
        {
            rg.getChildAt(0).id ->
            {
                return 0
            }
            rg.getChildAt(1).id ->
            {
                return 1
            }
            rg.getChildAt(2).id ->
            {
                return 2
            }
            rg.getChildAt(3).id ->
            {
                return 3
            }
            rg.getChildAt(4).id ->
            {
                return 4
            }
        }
        return 0
    }

    fun hidePopPutermsLayout()
    {
        pdqCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                pdqCheckBinding.pdqq1txt.requestFocus()
            }
            2 ->
            {
                pdqCheckBinding.pdqq2txt.requestFocus()
            }

            3 ->
            {
                pdqCheckBinding.pdqq3txt.requestFocus()
            }
            4 ->
            {
                pdqCheckBinding.pdqq4txt.requestFocus()
            }

            5 ->
            {
                pdqCheckBinding.pdqq5txt.requestFocus()
            }
            6 ->
            {
                pdqCheckBinding.pdqq6txt.requestFocus()
            }

            7 ->
            {
                pdqCheckBinding.pdqq7txt.requestFocus()
            }

            8 ->
            {
                pdqCheckBinding.pdqq8txt.requestFocus()
            }

            9 ->
            {
                pdqCheckBinding.pdqq9txt.requestFocus()
            }
            10 ->
            {
                pdqCheckBinding.pdqq10txt.requestFocus()
            }

        }
    }

    private fun fragmentTransaction(newFragment : Fragment)
    {
        mainActivity.fragmentTransaction(newFragment)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PDQCheckViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


}