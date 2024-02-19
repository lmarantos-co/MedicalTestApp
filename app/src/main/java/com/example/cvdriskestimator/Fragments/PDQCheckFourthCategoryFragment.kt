package com.example.cvdriskestimator.Fragments

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentPDQCheckFourthCategoryBinding
import com.example.cvdriskestimator.viewModels.CheckGPDQPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.PDQCheckViewModel

private const val ARG_PARAM1 = "patientData"


class PDQCheckFourthCategoryFragment : Fragment() {


    private lateinit var allPatientData : IntArray

    private lateinit var pdqCheckBinding: FragmentPDQCheckFourthCategoryBinding
    private var pdqFifthCategoryFragment = PDQCheckFifthCategoryFragment
    private var pdqThirdCategoryFragment = PDQCheckThridCategoryFragment()
    private lateinit var pdqPatientViewModel: PDQCheckViewModel
    private lateinit var pdqPatientViewModelFactory : CheckGPDQPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections : IntArray = IntArray(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //get the data from the previous fragment
            allPatientData = arguments!!.getIntArray("patientData")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pdqCheckBinding = FragmentPDQCheckFourthCategoryBinding.inflate(inflater, container, false)
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
//        if (userName != "tempUser")
//        {
//            pdqPatientViewModel.setPatientDataOnForm(userName!!)
//        }
//        else
//        {
//            pdqPatientViewModel.initialiseUserDummy()
//            pdqPatientViewModel.setPatientDataOnForm(userName)
//        }

        pdqPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        pdqPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        pdqCheckBinding.leftArrowImgV.setOnClickListener {

            var bundle = Bundle()
            bundle.putIntArray("allPatientData" , allPatientData)
            fragmentTransaction(pdqThirdCategoryFragment)
        }

        if (savedInstanceState!= null)
        {
            var allPatientData = savedInstanceState!!.getIntArray("patientData")
            if (allPatientData != null)
            {
                initialiseFormWithRunningPatientData(allPatientData)
            }
        }


        pdqCheckBinding.rightArrowImgV.setOnClickListener {
            allPatientSelections[0] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ23RG)
            allPatientSelections[1] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ24RG)
            allPatientSelections[2] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ25RG)
            allPatientSelections[3] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ26RG)

            if (pdqPatientViewModel.checkPDQTestPatient(allPatientSelections))
            {
                allPatientData = allPatientData!! + allPatientSelections
                fragmentTransaction(pdqFifthCategoryFragment.newInstance(allPatientData))
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
            setQuestionRadioGroup(pdqCheckBinding.pdqQ23RG , test.patientPDQQ23)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ24RG , test.patientPDQQ24)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ25RG , test.patientPDQQ25)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ26RG , test.patientPDQQ26)

        } , 1000)
    }

    fun initialisePatientData()
    {
        pdqCheckBinding.pdqQ23RG.clearCheck()
        pdqCheckBinding.pdqQ24RG.clearCheck()
        pdqCheckBinding.pdqQ25RG.clearCheck()
        pdqCheckBinding.pdqQ26RG.clearCheck()
    }

    private fun initialiseFormWithRunningPatientData(allPatientData : IntArray)
    {
        setQuestionRadioGroup(pdqCheckBinding.pdqQ23RG , allPatientData.get(22))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ24RG , allPatientData.get(23))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ25RG , allPatientData.get(24))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ26RG , allPatientData.get(25))
    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
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
            23 ->
            {
                pdqCheckBinding.pdqq23txt.requestFocus()
            }
            24 ->
            {
                pdqCheckBinding.pdqq24txt.requestFocus()
            }

            25 ->
            {
                pdqCheckBinding.pdqq25txt.requestFocus()
            }
            26 ->
            {
                pdqCheckBinding.pdqq26txt.requestFocus()
            }


        }
    }

    private fun fragmentTransaction(newFragment : Fragment)
    {
        mainActivity.fragmentTransaction(newFragment)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1 : IntArray) =
            PDQCheckFourthCategoryFragment().apply {
                arguments = Bundle().apply {
                    arguments!!.putIntArray(ARG_PARAM1, param1)
                }
            }
    }
}