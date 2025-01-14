package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentPDQSecondCategoryCheckBinding
import com.example.cvdriskestimator.viewModels.CheckGPDQPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.PDQCheckViewModel


private const val ARG_PARAM1 = "patientData"


class PDQCheckSecondCategoryFragment : Fragment() {

    private lateinit var allPatientData : IntArray

    private lateinit var pdqCheckBinding: FragmentPDQSecondCategoryCheckBinding
    private var pdqThirdCategoryFragment = PDQCheckThridCategoryFragment()
    private var pdqFirstCategoryFragment = PDQCheckFirstCategoryFragment()
    private lateinit var pdqPatientViewModel: PDQCheckViewModel
    private lateinit var pdqPatientViewModelFactory : CheckGPDQPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections : IntArray = IntArray(6)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        pdqCheckBinding = FragmentPDQSecondCategoryCheckBinding.inflate(inflater, container, false)
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


        pdqCheckBinding.leftArrowImgV.setOnClickListener {

            var bundle = Bundle()
            bundle.putIntArray("allPatientData" , allPatientData)
            pdqFirstCategoryFragment.arguments = bundle
            fragmentTransaction(pdqFirstCategoryFragment)
        }

        if (allPatientData.size > 10)
            initialiseFormWithRunningPatientData(allPatientData)

        pdqCheckBinding.rightArrowImgV.setOnClickListener {
            allPatientSelections[0] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ11RG)
            allPatientSelections[1] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ12RG)
            allPatientSelections[2] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ13RG)
            allPatientSelections[3] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ14RG)
            allPatientSelections[4] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ15RG)
            allPatientSelections[5] = getAnswerFromRadioGroup(pdqCheckBinding.pdqQ16RG)

            if (pdqPatientViewModel.checkPDQTestPatient(allPatientSelections))
            {
                allPatientData = allPatientData!! + allPatientSelections
                var bundle = Bundle()
                bundle.putIntArray("allPatientData" , allPatientData)
                pdqThirdCategoryFragment.arguments = bundle
                fragmentTransaction(pdqThirdCategoryFragment)
            }

        }

        pdqCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(pdqCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this, registerFragment , null , leaderBoardFragment)

        pdqCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }

        pdqPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        pdqPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
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
            setQuestionRadioGroup(pdqCheckBinding.pdqQ11RG , test.patientPDQQ11)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ12RG , test.patientPDQQ12)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ13RG , test.patientPDQQ13)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ14RG , test.patientPDQQ14)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ15RG , test.patientPDQQ15)
            setQuestionRadioGroup(pdqCheckBinding.pdqQ16RG , test.patientPDQQ16)
        } , 1000)
    }

    fun initialisePatientData()
    {
        pdqCheckBinding.pdqQ11RG.clearCheck()
        pdqCheckBinding.pdqQ12RG.clearCheck()
        pdqCheckBinding.pdqQ13RG.clearCheck()
        pdqCheckBinding.pdqQ14RG.clearCheck()
        pdqCheckBinding.pdqQ15RG.clearCheck()
        pdqCheckBinding.pdqQ16RG.clearCheck()

    }

    private fun initialiseFormWithRunningPatientData(allPatientData : IntArray)
    {
        setQuestionRadioGroup(pdqCheckBinding.pdqQ11RG , allPatientData.get(10))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ12RG , allPatientData.get(11))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ13RG , allPatientData.get(12))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ14RG , allPatientData.get(13))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ15RG , allPatientData.get(14))
        setQuestionRadioGroup(pdqCheckBinding.pdqQ16RG , allPatientData.get(15))

    }


    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            11 ->
            {
                pdqCheckBinding.pdqq11txt.requestFocus()
            }
            12 ->
            {
                pdqCheckBinding.pdqq12txt.requestFocus()
            }

            13 ->
            {
                pdqCheckBinding.pdqq13txt.requestFocus()
            }
            14 ->
            {
                pdqCheckBinding.pdqq14txt.requestFocus()
            }

            15 ->
            {
                pdqCheckBinding.pdqq15txt.requestFocus()
            }
            16 ->
            {
                pdqCheckBinding.pdqq16txt.requestFocus()
            }

        }
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


    private fun fragmentTransaction(newFragment : Fragment)
    {
        mainActivity.fragmentTransaction(newFragment)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

}
