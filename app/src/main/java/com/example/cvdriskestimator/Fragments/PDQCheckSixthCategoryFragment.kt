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
import com.example.cvdriskestimator.databinding.FragmentPDQCheckSixthCategoryBinding
import com.example.cvdriskestimator.viewModels.CheckGPDQPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.PDQCheckViewModel

private const val ARG_PARAM1 = "patientData"


class PDQCheckSixthCategoryFragment : Fragment() {

    private lateinit var allPatientData : IntArray

    private lateinit var pdqCheckBinding: FragmentPDQCheckSixthCategoryBinding
    private var pdqSeventhCategoryFragment = PDQCheckSeventhCategoryFragment
    private var pdqFifthCategoryFragment = PDQCheckFifthCategoryFragment
    private lateinit var pdqPatientViewModel: PDQCheckViewModel
    private lateinit var pdqPatientViewModelFactory : CheckGPDQPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
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

        pdqCheckBinding = FragmentPDQCheckSixthCategoryBinding.inflate(inflater, container, false)
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

            fragmentTransaction(pdqFifthCategoryFragment.newInstance(allPatientData))
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
            allPatientSelections[0] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ30RG)
            allPatientSelections[1] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ31RG)
            allPatientSelections[2] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ32RG)
            allPatientSelections[3] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ33RG)


            if (pdqPatientViewModel.checkPDQTestPatient(allPatientSelections))
            {
                allPatientData = allPatientData!! + allPatientSelections
                fragmentTransaction(pdqSeventhCategoryFragment.newInstance(allPatientData))
            }

        }

        pdqCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(pdqCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,registerFragment , null ,leaderBoardFragment)

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
        setQuestionRadioGroup(pdqCheckBinding.pdqQ30RG , allPatientData.get(29))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ31RG , allPatientData.get(30))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ32RG , allPatientData.get(32))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ33RG , allPatientData.get(33))

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(pdqCheckBinding.pdqQ30RG , test.patientPDQQ30)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ31RG , test.patientPDQQ31)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ32RG , test.patientPDQQ32)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ33RG , test.patientPDQQ33)

        } , 1000)
    }

    fun initialisePatientData()
    {
        pdqCheckBinding.pdqQ30RG.clearCheck()
        pdqCheckBinding.pdqQ31RG.clearCheck()
        pdqCheckBinding.pdqQ32RG.clearCheck()
        pdqCheckBinding.pdqQ33RG.clearCheck()

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
            30 ->
            {
                pdqCheckBinding.pdqq30txt.requestFocus()
            }
            31 ->
            {
                pdqCheckBinding.pdqq31txt.requestFocus()
            }

            32 ->
            {
                pdqCheckBinding.pdqq32txt.requestFocus()
            }
            33 ->
            {
                pdqCheckBinding.pdqq33txt.requestFocus()
            }

        }
    }

    private fun fragmentTransaction(newFragment : Fragment)
    {
        mainActivity.fragmentTransaction(newFragment)
    }

    companion object {
        fun newInstance(param1 : IntArray) = PDQCheckSixthCategoryFragment().apply {
            arguments!!.putIntArray(ARG_PARAM1, param1)
        }
    }
}