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
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.databinding.FragmentPDQCheckThridCategoryBinding
import com.example.cvdriskestimator.databinding.FragmentPDQSecondCategoryCheckBinding
import com.example.cvdriskestimator.viewModels.CheckGPDQPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.PDQCheckViewModel

private const val ARG_PARAM1 = "patientData"


class PDQCheckThridCategoryFragment : Fragment() {

    private lateinit var allPatientData : IntArray

    private lateinit var pdqCheckBinding: FragmentPDQCheckThridCategoryBinding
    private var pdqFourthCategoryFragment = PDQCheckFourthCategoryFragment
    private var pdqSecondCategoryFragment = PDQCheckSecondCategoryFragment()
    private lateinit var pdqPatientViewModel: PDQCheckViewModel
    private lateinit var pdqPatientViewModelFactory : CheckGPDQPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections : IntArray = IntArray(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pdqCheckBinding = FragmentPDQCheckThridCategoryBinding.inflate(inflater, container, false)
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

        allPatientData = this.arguments!!.getIntArray("allPatientData")!!


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
            setPatientData(it)
        }

        pdqCheckBinding.leftArrowImgV.setOnClickListener {

            var bundle = Bundle()
            bundle.putIntArray("allPatientData" , allPatientData)
            pdqSecondCategoryFragment.arguments
            fragmentTransaction(pdqSecondCategoryFragment)
        }

        if (allPatientData.size > 16)
            initialiseFormWithRunningPatientData(allPatientData)


        pdqCheckBinding.rightArrowImgV.setOnClickListener {
            allPatientSelections[0] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ17RG)
            allPatientSelections[1] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ18RG)
            allPatientSelections[2] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ19RG)
            allPatientSelections[3] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ20RG)
            allPatientSelections[4] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ21RG)
            allPatientSelections[5] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ22RG)

            if (pdqPatientViewModel.checkPDQTestPatient(allPatientSelections))
            {
                allPatientData = allPatientData!! + allPatientSelections
                fragmentTransaction(pdqFourthCategoryFragment.newInstance(allPatientData))
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

    private fun setPatientData(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(pdqCheckBinding.pdqQ17RG , patient.patientPDQQ17)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ18RG , patient.patientPDQQ18)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ19RG , patient.patientPDQQ19)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ20RG , patient.patientPDQQ20)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ21RG , patient.patientPDQQ21)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ22RG , patient.patientPDQQ22)
        } , 1000)
    }

    fun initialisePatientData()
    {
        pdqCheckBinding.pdqQ17RG.clearCheck()
        pdqCheckBinding.pdqQ18RG.clearCheck()
        pdqCheckBinding.pdqQ19RG.clearCheck()
        pdqCheckBinding.pdqQ20RG.clearCheck()
        pdqCheckBinding.pdqQ21RG.clearCheck()
        pdqCheckBinding.pdqQ22RG.clearCheck()

    }

    private fun initialiseFormWithRunningPatientData(allPatientData : IntArray)
    {
        setQuestionRadioGroup(pdqCheckBinding.pdqQ17RG , allPatientData.get(16))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ18RG , allPatientData.get(17))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ19RG , allPatientData.get(18))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ20RG , allPatientData.get(19))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ21RG , allPatientData.get(20))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ22RG , allPatientData.get(21))

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
            17 ->
            {
                pdqCheckBinding.pdqq17txt.requestFocus()
            }
            18 ->
            {
                pdqCheckBinding.pdqq18txt.requestFocus()
            }

            19 ->
            {
                pdqCheckBinding.pdqq19txt.requestFocus()
            }
            20 ->
            {
                pdqCheckBinding.pdqq20txt.requestFocus()
            }
            21 ->
            {
                pdqCheckBinding.pdqq21txt.requestFocus()
            }
            22 ->
            {
                pdqCheckBinding.pdqq22txt.requestFocus()
            }

        }
    }

    private fun fragmentTransaction(newFragment : Fragment)
    {
        mainActivity.fragmentTransaction(newFragment)
    }

}