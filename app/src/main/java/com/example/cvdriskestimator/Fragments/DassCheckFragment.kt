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
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.DASSTest
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentDassCheckBinding
import com.example.cvdriskestimator.viewModels.CheckDASSPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckDASSPatientViewModelFactory
import java.sql.Date
import java.util.*


class DASSCheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var dassPatientViewModel: CheckDASSPatientViewModel
    private lateinit var dassCheckBinding : FragmentDassCheckBinding
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
        dassCheckBinding = FragmentDassCheckBinding.inflate(inflater , container , false)
        dassPatientViewModel = CheckDASSPatientViewModel()
        val checkDASSPatientViewModelFactory = CheckDASSPatientViewModelFactory()
        dassPatientViewModel = ViewModelProvider(this , checkDASSPatientViewModelFactory).get(CheckDASSPatientViewModel::class.java)
        dassCheckBinding.checkDASSPatientViewModel = dassPatientViewModel
        dassCheckBinding.lifecycleOwner = this
        return dassCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dassPatientViewModel.passActivity(mainActivity)
        dassPatientViewModel.passFragment(this)
        dassPatientViewModel.initialiseRealm()

        dassCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate" , "")
        var openType = this.arguments!!.getString("openType")


        if (openType == "open_history")
        {
            var historyTest = DASSTest()
            if (patientId != "")
            {
                if (testDate != "")
                {
                    //var date = convertStringToDate(testDate!!)
                    //default time zone
//                    val defaultZoneId: ZoneId = ZoneId.systemDefault()
//                    val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
//                    var testDateFormated = convertStringToCalenderDate(testDate)
//                    val localDate = LocalDate.parse(testDateFormated)
//                    val text: String = localDate.format(formatter)
//                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
//                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
//                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
                    historyTest = dassPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.dassAnxietyResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                dassPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                dassPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                dassPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        dassPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        dassPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        dassCheckBinding.clearBtn.setOnClickListener {
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

        dassCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ17RG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ18RG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ19RG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ20RG)
            allPatientSelections[20] = getAsnwerFromRadioGroup(dassCheckBinding.DASSQ21RG)


            dassPatientViewModel.checkDASSTestPatient(allPatientSelections)
        }

        dassCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(dassCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null , leaderBoardFragment)

        dassCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        dassCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        dassCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        dassCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : DASSTest)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(dassCheckBinding.DASSQ1RG , test.patientDASSQ1)
            setQuestionRadioGroup(dassCheckBinding.DASSQ2RG , test.patientDASSQ2)
            setQuestionRadioGroup(dassCheckBinding.DASSQ3RG , test.patientDASSQ3)
            setQuestionRadioGroup(dassCheckBinding.DASSQ4RG , test.patientDASSQ4)
            setQuestionRadioGroup(dassCheckBinding.DASSQ5RG , test.patientDASSQ5)
            setQuestionRadioGroup(dassCheckBinding.DASSQ6RG , test.patientDASSQ6)
            setQuestionRadioGroup(dassCheckBinding.DASSQ7RG , test.patientDASSQ7)
            setQuestionRadioGroup(dassCheckBinding.DASSQ8RG , test.patientDASSQ8)
            setQuestionRadioGroup(dassCheckBinding.DASSQ9RG , test.patientDASSQ9)
            setQuestionRadioGroup(dassCheckBinding.DASSQ10RG , test.patientDASSQ10)
            setQuestionRadioGroup(dassCheckBinding.DASSQ11RG , test.patientDASSQ11)
            setQuestionRadioGroup(dassCheckBinding.DASSQ12RG , test.patientDASSQ12)
            setQuestionRadioGroup(dassCheckBinding.DASSQ13RG , test.patientDASSQ13)
            setQuestionRadioGroup(dassCheckBinding.DASSQ14RG , test.patientDASSQ14)
            setQuestionRadioGroup(dassCheckBinding.DASSQ15RG , test.patientDASSQ15)
            setQuestionRadioGroup(dassCheckBinding.DASSQ16RG , test.patientDASSQ16)
            setQuestionRadioGroup(dassCheckBinding.DASSQ17RG , test.patientDASSQ17)
            setQuestionRadioGroup(dassCheckBinding.DASSQ18RG , test.patientDASSQ18)
            setQuestionRadioGroup(dassCheckBinding.DASSQ19RG , test.patientDASSQ19)
            setQuestionRadioGroup(dassCheckBinding.DASSQ20RG , test.patientDASSQ20)
            setQuestionRadioGroup(dassCheckBinding.DASSQ21RG , test.patientDASSQ21)
        } , 1000)
    }

    fun initialisePatientData()
    {
        dassCheckBinding.DASSQ1RG.clearCheck()
        dassCheckBinding.DASSQ2RG.clearCheck()
        dassCheckBinding.DASSQ3RG.clearCheck()
        dassCheckBinding.DASSQ4RG.clearCheck()
        dassCheckBinding.DASSQ5RG.clearCheck()
        dassCheckBinding.DASSQ6RG.clearCheck()
        dassCheckBinding.DASSQ7RG.clearCheck()
        dassCheckBinding.DASSQ8RG.clearCheck()
        dassCheckBinding.DASSQ9RG.clearCheck()
        dassCheckBinding.DASSQ10RG.clearCheck()
        dassCheckBinding.DASSQ11RG.clearCheck()
        dassCheckBinding.DASSQ12RG.clearCheck()
        dassCheckBinding.DASSQ13RG.clearCheck()
        dassCheckBinding.DASSQ14RG.clearCheck()
        dassCheckBinding.DASSQ15RG.clearCheck()
        dassCheckBinding.DASSQ16RG.clearCheck()
        dassCheckBinding.DASSQ17RG.clearCheck()
        dassCheckBinding.DASSQ18RG.clearCheck()
        dassCheckBinding.DASSQ19RG.clearCheck()
        dassCheckBinding.DASSQ20RG.clearCheck()
        dassCheckBinding.DASSQ21RG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            1 ->
            {
                (rg.getChildAt(0) as RadioButton).isChecked = true
            }
            2 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
            3 ->
            {
                (rg.getChildAt(2) as RadioButton).isChecked = true
            }
            4->
            {
                (rg.getChildAt(3) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if (rg.get(0).id == radioButtonId)
            result = 1
        if (rg.get(1).id == radioButtonId)
            result = 2
        if (rg.get(2).id == radioButtonId)
            result = 3
        if (rg.get(3).id == radioButtonId)
            result = 4
        return result
    }

    fun hidePopPutermsLayout()
    {
        dassCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String , questNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questNo)
        {
            1 ->
            {
                dassCheckBinding.DASSQ1TxtV.requestFocus()
            }

            2 ->
            {
                dassCheckBinding.DASSQ2TxtV.requestFocus()
            }

            3 ->
            {
                dassCheckBinding.DASSQ3TxtV.requestFocus()
            }

            4 ->
            {
                dassCheckBinding.DASSQ4TxtV.requestFocus()
            }

            5 ->
            {
                dassCheckBinding.DASSQ5TxtV.requestFocus()
            }

            6 ->
            {
                dassCheckBinding.DASSQ6TxtV.requestFocus()
            }

            7 ->
            {
                dassCheckBinding.DASSQ7TxtV.requestFocus()
            }

            8 ->
            {
                dassCheckBinding.DASSQ8TxtV.requestFocus()
            }

            9 ->
            {
                dassCheckBinding.DASSQ9TxtV.requestFocus()
            }

            10 ->
            {
                dassCheckBinding.DASSQ10TxtV.requestFocus()
            }

            11 ->
            {
                dassCheckBinding.DASSQ11TxtV.requestFocus()
            }

            12 ->
            {
                dassCheckBinding.DASSQ12TxtV.requestFocus()
            }

            13 ->
            {
                dassCheckBinding.DASSQ13TxtV.requestFocus()
            }

            14 ->
            {
                dassCheckBinding.DASSQ14TxtV.requestFocus()
            }

            15 ->
            {
                dassCheckBinding.DASSQ15TxtV.requestFocus()
            }

            16 ->
            {
                dassCheckBinding.DASSQ16TxtV.requestFocus()
            }

            17 ->
            {
                dassCheckBinding.DASSQ17TxtV.requestFocus()
            }

            18 ->
            {
                dassCheckBinding.DASSQ18TxtV.requestFocus()
            }

            19 ->
            {
                dassCheckBinding.DASSQ19TxtV.requestFocus()
            }

            20 ->
            {
                dassCheckBinding.DASSQ20TxtV.requestFocus()
            }

            21 ->
            {
                dassCheckBinding.DASSQ21TxtV.requestFocus()
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
            DASSCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}