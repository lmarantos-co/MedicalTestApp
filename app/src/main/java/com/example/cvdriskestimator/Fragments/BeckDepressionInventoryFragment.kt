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
import com.example.cvdriskestimator.viewModels.*
import java.sql.Date
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [BAICheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BeckDepressionInventoryFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var bdiPatientViewModel: CheckBDIPatientViewModel
    private lateinit var bdiCheckBinding : FragmentBeckDepressionInventoryBinding
    private var allPatientSelections = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1, 1)
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
        bdiCheckBinding = FragmentBeckDepressionInventoryBinding.inflate(inflater , container , false)
        bdiPatientViewModel = CheckBDIPatientViewModel()
        val checkBDIViewModelFactory = CheckBDIViewModelFactory()
        bdiPatientViewModel = ViewModelProvider(this , checkBDIViewModelFactory).get(CheckBDIPatientViewModel::class.java)
        bdiCheckBinding.checkBDIViewModel = bdiPatientViewModel
        bdiCheckBinding.lifecycleOwner = this
        return bdiCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdiPatientViewModel.passActivity(mainActivity)
        bdiPatientViewModel.passFragment(this)
        bdiPatientViewModel.initialiseRealm()

        bdiCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate" , "")
        var openType = this.arguments!!.getString("openType")


        var historyTest = Test()
        if (patientId != "")
        {
            if (testDate != "")
            {
//                var date = convertStringToCalenderDate(testDate!!)
                historyTest = bdiPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
            }

        }
        if (historyTest.patientBDITestResult != null)
        {
            setPatientData(historyTest)
        }
        else
        {
            if (openType == "updateLast")
            {
                bdiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                bdiPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                bdiPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        bdiPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        bdiPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        bdiCheckBinding.clearBtn.setOnClickListener {
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

        bdiCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ2RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ17RG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ18RG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ19RG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ20RG)
            allPatientSelections[20] = getAsnwerFromRadioGroup(bdiCheckBinding.BDIQ21RG)

            bdiPatientViewModel.checkBDITestPatient(allPatientSelections)
        }

        bdiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(bdiCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null , leaderBoardFragment)

        bdiCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        bdiCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        bdiCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        bdiCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(bdiCheckBinding.BDIQ1RG , test.patientBDIQ1)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ2RG , test.patientBDIQ2)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ3RG , test.patientBDIQ3)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ4RG , test.patientBDIQ4)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ5RG , test.patientBDIQ5)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ6RG , test.patientBDIQ6)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ7RG , test.patientBDIQ7)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ8RG , test.patientBDIQ8)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ9RG , test.patientBDIQ9)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ10RG , test.patientBDIQ10)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ11RG , test.patientBDIQ11)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ12RG , test.patientBDIQ12)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ13RG , test.patientBDIQ13)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ14RG , test.patientBDIQ14)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ15RG , test.patientBDIQ15)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ16RG , test.patientBDIQ16)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ17RG , test.patientBDIQ17)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ18RG , test.patientBDIQ18)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ19RG , test.patientBDIQ19)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ20RG , test.patientBDIQ20)
            setQuestionRadioGroup(bdiCheckBinding.BDIQ21RG , test.patientBDIQ21)

        } , 1000)
    }

    fun initialisePatientData()
    {
        bdiCheckBinding.BDIQ1RG.clearCheck()
        bdiCheckBinding.BDIQ2RG.clearCheck()
        bdiCheckBinding.BDIQ3RG.clearCheck()
        bdiCheckBinding.BDIQ4RG.clearCheck()
        bdiCheckBinding.BDIQ5RG.clearCheck()
        bdiCheckBinding.BDIQ6RG.clearCheck()
        bdiCheckBinding.BDIQ7RG.clearCheck()
        bdiCheckBinding.BDIQ8RG.clearCheck()
        bdiCheckBinding.BDIQ9RG.clearCheck()
        bdiCheckBinding.BDIQ10RG.clearCheck()
        bdiCheckBinding.BDIQ11RG.clearCheck()
        bdiCheckBinding.BDIQ12RG.clearCheck()
        bdiCheckBinding.BDIQ13RG.clearCheck()
        bdiCheckBinding.BDIQ14RG.clearCheck()
        bdiCheckBinding.BDIQ15RG.clearCheck()
        bdiCheckBinding.BDIQ16RG.clearCheck()
        bdiCheckBinding.BDIQ17RG.clearCheck()
        bdiCheckBinding.BDIQ18RG.clearCheck()
        bdiCheckBinding.BDIQ19RG.clearCheck()
        bdiCheckBinding.BDIQ20RG.clearCheck()
        bdiCheckBinding.BDIQ21RG.clearCheck()

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
        bdiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String , questNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questNo)
        {
            1 ->
            {
                bdiCheckBinding.BDIQ1TxtV.requestFocus()
            }

            2 ->
            {
                bdiCheckBinding.BDIQ2TxtV.requestFocus()
            }

            3 ->
            {
                bdiCheckBinding.BDIQ3TxtV.requestFocus()
            }

            4 ->
            {
                bdiCheckBinding.BDIQ4TxtV.requestFocus()
            }

            5 ->
            {
                bdiCheckBinding.BDIQ5TxtV.requestFocus()
            }

            6 ->
            {
                bdiCheckBinding.BDIQ6TxtV.requestFocus()
            }

            7 ->
            {
                bdiCheckBinding.BDIQ7TxtV.requestFocus()
            }

            8 ->
            {
                bdiCheckBinding.BDIQ8TxtV.requestFocus()
            }

            9 ->
            {
                bdiCheckBinding.BDIQ9TxtV.requestFocus()
            }

            10 ->
            {
                bdiCheckBinding.BDIQ10TxtV.requestFocus()
            }

            11 ->
            {
                bdiCheckBinding.BDIQ11TxtV.requestFocus()
            }

            12 ->
            {
                bdiCheckBinding.BDIQ12TxtV.requestFocus()
            }

            13 ->
            {
                bdiCheckBinding.BDIQ13TxtV.requestFocus()
            }

            14 ->
            {
                bdiCheckBinding.BDIQ14TxtV.requestFocus()
            }

            15 ->
            {
                bdiCheckBinding.BDIQ15TxtV.requestFocus()
            }

            16 ->
            {
                bdiCheckBinding.BDIQ16TxtV.requestFocus()
            }

            17 ->
            {
                bdiCheckBinding.BDIQ17TxtV.requestFocus()
            }

            18 ->
            {
                bdiCheckBinding.BDIQ18TxtV.requestFocus()
            }

            19 ->
            {
                bdiCheckBinding.BDIQ19TxtV.requestFocus()
            }

            20 ->
            {
                bdiCheckBinding.BDIQ20TxtV.requestFocus()
            }

            21 ->
            {
                bdiCheckBinding.BDIQ21TxtV.requestFocus()
            }

        }
    }

    private fun convertStringToCalenderDate(testDate : String) : java.util.Date
    {
        val month = testDate.split(" ")
        var monthNo : String = ""
        when (month.get(1))
        {
            "Jan" -> {
                monthNo = "0"
            }
            "Feb" -> {
                monthNo = "1"
            }
            "Mar" -> {
                monthNo = "2"
            }
            "Apr" -> {
                monthNo = "3"
            }
            "May" -> {
                monthNo = "4"
            }
            "Jun" -> {
                monthNo = "5"
            }
            "Jul" -> {
                monthNo = "6"
            }
            "Aug" -> {
                monthNo = "7"
            }
            "Sep" -> {
                monthNo = "8"
            }
            "Oct" -> {
                monthNo = "9"
            }
            "Nov" -> {
                monthNo = "10"
            }
            "Dec" -> {
                monthNo = "11"
            }
        }
        var day = month.get(2)
        var hour = month.get(3)
        var year = month.get(5)
        var date = "${year}-${monthNo}-${day}"
        val calender = Calendar.getInstance()
        calender.set(Calendar.YEAR , year.toInt())
        calender.set(Calendar.MONTH , monthNo.toInt())
        calender.set(Calendar.DAY_OF_MONTH , day.toInt())
        return calender.time
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
            BeckDepressionInventoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}