package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentBeckDepressionInventoryBinding
import com.example.cvdriskestimator.databinding.FragmentHamiltonDepressionBinding
import com.example.cvdriskestimator.viewModels.*
import java.sql.Date
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [BAICheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HamiltonDepressionFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var hadmPatientViewModel: CheckHAMDPatientViewModel
    private lateinit var hamDCheckBinding : FragmentHamiltonDepressionBinding
    private var allPatientSelections = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1)
    private lateinit var popupMenu: PopUpMenu

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //assign the binding to the xml layout
        hamDCheckBinding = FragmentHamiltonDepressionBinding.inflate(inflater , container , false)
        hadmPatientViewModel = CheckHAMDPatientViewModel()
        val CheckHAMDPatientViewModelFactory = CheckHAMDPatientViewModelFactory()
        hadmPatientViewModel = ViewModelProvider(this , CheckHAMDPatientViewModelFactory).get(CheckHAMDPatientViewModel::class.java)
        hamDCheckBinding.checkHAMDViewModel = hadmPatientViewModel
        hamDCheckBinding.lifecycleOwner = this
        return hamDCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hadmPatientViewModel.passActivity(mainActivity)
        hadmPatientViewModel.passFragment(this)
        hadmPatientViewModel.initialiseRealm()

        hamDCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate" , "")
        var openType = this.arguments!!.getString("openType")


        var historyTest = Test()
        if (patientId != "")
        {
            if (testDate != "")
            {
                var date = convertStringToDate(testDate!!)
                historyTest = hadmPatientViewModel.fetchHistoryTest(patientId!! , date!!)
            }
        }
        if (historyTest.cvdTestResult != null)
        {
            setPatientData(historyTest)
        }
        else
        {
            if (openType == "updateLast")
            {
                hadmPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                hadmPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                hadmPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        hadmPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        hadmPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        hamDCheckBinding.clearBtn.setOnClickListener {
            AlertDialog.Builder(this.activity)
                .setTitle("Clear All Data")
                .setMessage("Are you sure you want to delete the user data?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation

                        initialisePatientData()

                    })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        hamDCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(hamDCheckBinding.HAMDQ17RG)

            hadmPatientViewModel.checkHAMDTestPatient(allPatientSelections)
        }

        hamDCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(hamDCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null , leaderBoardFragment)

        hamDCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        hamDCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        hamDCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        hamDCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ1RG , test.patientBDIQ1)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ2RG , test.patientBDIQ2)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ3RG , test.patientBDIQ3)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ4RG , test.patientBDIQ4)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ5RG , test.patientBDIQ5)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ6RG , test.patientBDIQ6)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ7RG , test.patientBDIQ7)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ8RG , test.patientBDIQ8)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ9RG , test.patientBDIQ9)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ10RG , test.patientBDIQ10)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ11RG , test.patientBDIQ11)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ2RG , test.patientBDIQ12)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ3RG , test.patientBDIQ13)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ14RG , test.patientBDIQ14)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ15RG , test.patientBDIQ15)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ16RG , test.patientBDIQ16)
            setQuestionRadioGroup(hamDCheckBinding.HAMDQ17RG , test.patientBDIQ17)


        } , 1000)
    }

    fun initialisePatientData()
    {
        hamDCheckBinding.HAMDQ1RG.clearCheck()
        hamDCheckBinding.HAMDQ2RG.clearCheck()
        hamDCheckBinding.HAMDQ3RG.clearCheck()
        hamDCheckBinding.HAMDQ4RG.clearCheck()
        hamDCheckBinding.HAMDQ5RG.clearCheck()
        hamDCheckBinding.HAMDQ6RG.clearCheck()
        hamDCheckBinding.HAMDQ7RG.clearCheck()
        hamDCheckBinding.HAMDQ8RG.clearCheck()
        hamDCheckBinding.HAMDQ9RG.clearCheck()
        hamDCheckBinding.HAMDQ10RG.clearCheck()
        hamDCheckBinding.HAMDQ11RG.clearCheck()
        hamDCheckBinding.HAMDQ12RG.clearCheck()
        hamDCheckBinding.HAMDQ13RG.clearCheck()
        hamDCheckBinding.HAMDQ14RG.clearCheck()
        hamDCheckBinding.HAMDQ15RG.clearCheck()
        hamDCheckBinding.HAMDQ16RG.clearCheck()
        hamDCheckBinding.HAMDQ17RG.clearCheck()


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
            4->
            {
                (rg.getChildAt(4) as RadioButton).isChecked = true
            }
            5->
            {
                (rg.getChildAt(5) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        var numOfRadioButtons = rg.childCount
        val radioButtonId = rg.checkedRadioButtonId
        for (i in 0..numOfRadioButtons - 1)
        {
            if (rg.get(i).id == radioButtonId)
            {
                result = i
            }
        }
        return result
    }

    fun hidePopPutermsLayout()
    {
        hamDCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String , questNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questNo)
        {
            1 ->
            {
                hamDCheckBinding.HAMDQ1RG.requestFocus()
            }

            2 ->
            {
                hamDCheckBinding.HAMDQ2RG.requestFocus()
            }

            3 ->
            {
                hamDCheckBinding.HAMDQ3RG.requestFocus()
            }

            4 ->
            {
                hamDCheckBinding.HAMDQ4RG.requestFocus()
            }

            5 ->
            {
                hamDCheckBinding.HAMDQ5RG.requestFocus()
            }

            6 ->
            {
                hamDCheckBinding.HAMDQ6RG.requestFocus()
            }

            7 ->
            {
                hamDCheckBinding.HAMDQ7RG.requestFocus()
            }

            8 ->
            {
                hamDCheckBinding.HAMDQ8RG.requestFocus()
            }

            9 ->
            {
                hamDCheckBinding.HAMDQ9RG.requestFocus()
            }

            10 ->
            {
                hamDCheckBinding.HAMDQ10RG.requestFocus()
            }

            11 ->
            {
                hamDCheckBinding.HAMDQ11RG.requestFocus()
            }

            12 ->
            {
                hamDCheckBinding.HAMDQ12RG.requestFocus()
            }

            13 ->
            {
                hamDCheckBinding.HAMDQ13RG.requestFocus()
            }

            14 ->
            {
                hamDCheckBinding.HAMDQ14RG.requestFocus()
            }

            15 ->
            {
                hamDCheckBinding.HAMDQ15RG.requestFocus()
            }

            16 ->
            {
                hamDCheckBinding.HAMDQ16RG.requestFocus()
            }

            17 ->
            {
                hamDCheckBinding.HAMDQ17RG.requestFocus()
            }

        }
    }

    private fun convertStringToDate(date: String): java.util.Date {
        var allDateParts = date.split(" ")
        var monthName = allDateParts.get(1)
        var monthNo: Int = 0
        when (monthName) {
            "Jan" -> {
                monthNo = 0
            }
            "Feb" -> {
                monthNo = 1
            }
            "Mar" -> {
                monthNo = 2
            }
            "Apr" -> {
                monthNo = 3
            }
            "May" -> {
                monthNo = 4
            }
            "Jun" -> {
                monthNo = 5
            }
            "Jul" -> {
                monthNo = 6
            }
            "Aug" -> {
                monthNo = 7
            }
            "Sep" -> {
                monthNo = 8
            }
            "Oct" -> {
                monthNo = 9
            }
            "Nov" -> {
                monthNo = 10
            }
            "Dec" -> {
                monthNo = 11
            }
        }
        var dateName = allDateParts.get(2)
        var time = allDateParts.get(3).toString().split(":")
        var hour = time.get(0)
        var min = time.get(1)
        var sec = time.get(2)
        var year = date.get(5)
        var date = Date(year.toInt(), monthNo, dateName.toInt())
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
        calendar.set(Calendar.MINUTE, min.toInt())
        calendar.set(Calendar.SECOND, sec.toInt())
        return calendar.time
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HamiltonDepressionFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}