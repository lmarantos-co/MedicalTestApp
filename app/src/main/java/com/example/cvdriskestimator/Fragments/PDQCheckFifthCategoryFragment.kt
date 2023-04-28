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
import com.example.cvdriskestimator.databinding.FragmentPDQCheckFifthCategoryBinding
import com.example.cvdriskestimator.viewModels.CheckGPDQPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.PDQCheckViewModel

private const val ARG_PARAM1 = "patientData"


class PDQCheckFifthCategoryFragment : Fragment() {

    private lateinit var allPatientData : IntArray

    private lateinit var pdqCheckBinding: FragmentPDQCheckFifthCategoryBinding
    private var pdqSixthCategoryFragment = PDQCheckSixthCategoryFragment
    private var pdqFourthCategoryFragment = PDQCheckFourthCategoryFragment
    private lateinit var pdqPatientViewModel: PDQCheckViewModel
    private lateinit var pdqPatientViewModelFactory : CheckGPDQPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections : IntArray = IntArray(3)

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

        pdqCheckBinding = FragmentPDQCheckFifthCategoryBinding.inflate(inflater, container, false)
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

        pdqPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        pdqPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        pdqCheckBinding.leftArrowImgV.setOnClickListener {

            fragmentTransaction(pdqFourthCategoryFragment.newInstance(allPatientData))
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
            allPatientSelections[0] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ27RG)
            allPatientSelections[1] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ28RG)
            allPatientSelections[2] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ29RG)

            if (pdqPatientViewModel.checkPDQTestPatient(allPatientSelections))
            {
                allPatientData = allPatientData!! + allPatientSelections
                fragmentTransaction(pdqSixthCategoryFragment.newInstance(allPatientData))
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

    private fun initialiseFormWithRunningPatientData(allPatientData : IntArray)
    {
        setQuestionRadioGroup(pdqCheckBinding.pdqQ27RG , allPatientData.get(26))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ28RG , allPatientData.get(27))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ29RG , allPatientData.get(28))
    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(pdqCheckBinding.pdqQ27RG , test.patientPDQQ27)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ28RG , test.patientPDQQ28)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ29RG , test.patientPDQQ29)
        } , 1000)
    }

    fun initialisePatientData()
    {
        pdqCheckBinding.pdqQ27RG.clearCheck()
        pdqCheckBinding.pdqQ28RG.clearCheck()
        pdqCheckBinding.pdqQ29RG.clearCheck()
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
            27 ->
            {
                pdqCheckBinding.pdqq27txt.requestFocus()
            }
            28 ->
            {
                pdqCheckBinding.pdqq28txt.requestFocus()
            }

            29 ->
            {
                pdqCheckBinding.pdqq29txt.requestFocus()
            }


        }
    }

    private fun fragmentTransaction(newFragment : Fragment)
    {
        mainActivity.fragmentTransaction(newFragment)
    }

    companion object {
        fun newInstance(param1 : IntArray) = PDQCheckFifthCategoryFragment().apply {
            arguments!!.putIntArray(ARG_PARAM1, param1)

        }
    }
}