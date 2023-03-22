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
import com.example.cvdriskestimator.databinding.FragmentZungSrdsCheckBinding
import com.example.cvdriskestimator.viewModels.CheckZUNGPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckZUNGPatientViewModelFactory
import java.sql.Date
import java.util.*


class CheckZUNGFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var zungPatientViewModel: CheckZUNGPatientViewModel
    private lateinit var zungCheckBinding : FragmentZungSrdsCheckBinding
    private var allPatientSelections = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1)
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
        zungCheckBinding = FragmentZungSrdsCheckBinding.inflate(inflater , container , false)
        zungPatientViewModel = CheckZUNGPatientViewModel()
        val checkZUNGPatientViewModelFactory = CheckZUNGPatientViewModelFactory()
        zungPatientViewModel = ViewModelProvider(this , checkZUNGPatientViewModelFactory).get(CheckZUNGPatientViewModel::class.java)
        zungCheckBinding.checkZUNGPatientViewModel = zungPatientViewModel
        zungCheckBinding.lifecycleOwner = this
        return zungCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        zungPatientViewModel.passActivity(mainActivity)
        zungPatientViewModel.passFragment(this)
        zungPatientViewModel.initialiseRealm()

        zungCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate" , "")
        var openType = this.arguments!!.getString("openType")


        if (openType == "open_history")
        {
            var historyTest = Test()
            if (patientId != "")
            {
                if (testDate != "")
                {
                    //var date = convertStringToDate(testDate!!)
                    //default time zone
//                    val defaultZoneId: ZoneId = ZoneId.systemDefault()
//                    val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
                    var testDateFormated = convertStringToCalenderDate(testDate)
//                    val localDate = LocalDate.parse(testDateFormated)
//                    val text: String = localDate.format(formatter)
//                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
//                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
//                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
                    historyTest = zungPatientViewModel.fetchHistoryTest(patientId!! , testDateFormated!!)
                }
            }
            if (historyTest.zungTestReesult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                zungPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                zungPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                zungPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        zungPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        zungPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        zungCheckBinding.clearBtn.setOnClickListener {
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

        zungCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ17RG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ18RG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ19RG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(zungCheckBinding.ZUNGQ20RG)



            zungPatientViewModel.checkZUNGTestPatient(allPatientSelections)
        }

        zungCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(zungCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null , leaderBoardFragment)

        zungCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        zungCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        zungCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        zungCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ1RG , test.patientZUNGQ1)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ2RG , test.patientZUNGQ2)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ3RG , test.patientZUNGQ3)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ4RG , test.patientZUNGQ4)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ5RG , test.patientZUNGQ5)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ6RG , test.patientZUNGQ6)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ7RG , test.patientZUNGQ7)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ8RG , test.patientZUNGQ8)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ9RG , test.patientZUNGQ9)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ10RG , test.patientZUNGQ10)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ11RG , test.patientZUNGQ11)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ12RG , test.patientZUNGQ12)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ13RG , test.patientZUNGQ13)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ14RG , test.patientZUNGQ14)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ15RG , test.patientZUNGQ15)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ16RG , test.patientZUNGQ16)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ17RG , test.patientZUNGQ17)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ18RG , test.patientZUNGQ18)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ19RG , test.patientZUNGQ19)
            setQuestionRadioGroup(zungCheckBinding.ZUNGQ20RG , test.patientZUNGQ20)
        } , 1000)
    }

    fun initialisePatientData()
    {
        zungCheckBinding.ZUNGQ1RG.clearCheck()
        zungCheckBinding.ZUNGQ2RG.clearCheck()
        zungCheckBinding.ZUNGQ3RG.clearCheck()
        zungCheckBinding.ZUNGQ4RG.clearCheck()
        zungCheckBinding.ZUNGQ5RG.clearCheck()
        zungCheckBinding.ZUNGQ6RG.clearCheck()
        zungCheckBinding.ZUNGQ7RG.clearCheck()
        zungCheckBinding.ZUNGQ8RG.clearCheck()
        zungCheckBinding.ZUNGQ9RG.clearCheck()
        zungCheckBinding.ZUNGQ10RG.clearCheck()
        zungCheckBinding.ZUNGQ11RG.clearCheck()
        zungCheckBinding.ZUNGQ12RG.clearCheck()
        zungCheckBinding.ZUNGQ13RG.clearCheck()
        zungCheckBinding.ZUNGQ14RG.clearCheck()
        zungCheckBinding.ZUNGQ15RG.clearCheck()
        zungCheckBinding.ZUNGQ16RG.clearCheck()
        zungCheckBinding.ZUNGQ17RG.clearCheck()
        zungCheckBinding.ZUNGQ18RG.clearCheck()
        zungCheckBinding.ZUNGQ19RG.clearCheck()
        zungCheckBinding.ZUNGQ20RG.clearCheck()

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
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if ((rg.id == zungCheckBinding.ZUNGQ1RG.id) || (rg.id == zungCheckBinding.ZUNGQ3RG.id) || (rg.id == zungCheckBinding.ZUNGQ4RG.id) || (rg.id == zungCheckBinding.ZUNGQ7RG.id) || (rg.id == zungCheckBinding.ZUNGQ8RG.id) || (rg.id == zungCheckBinding.ZUNGQ9RG.id)|| (rg.id ==zungCheckBinding.ZUNGQ10RG.id)
            || (rg.id == zungCheckBinding.ZUNGQ13RG.id) || (rg.id == zungCheckBinding.ZUNGQ15RG.id)|| (rg.id ==zungCheckBinding.ZUNGQ19RG.id))
        {
            if (rg.get(0).id == radioButtonId)
                result = 1
            if (rg.get(1).id == radioButtonId)
                result = 2
            if (rg.get(2).id == radioButtonId)
                result = 3
            if (rg.get(3).id == radioButtonId)
                result = 4
        }
        if ((rg.id == zungCheckBinding.ZUNGQ2RG.id) || (rg.id == zungCheckBinding.ZUNGQ5RG.id) || (rg.id == zungCheckBinding.ZUNGQ6RG.id) || (rg.id == zungCheckBinding.ZUNGQ12RG.id) || (rg.id == zungCheckBinding.ZUNGQ14RG.id) || (rg.id == zungCheckBinding.ZUNGQ16RG.id)|| (rg.id ==zungCheckBinding.ZUNGQ17RG.id)
            || (rg.id == zungCheckBinding.ZUNGQ18RG.id) || (rg.id == zungCheckBinding.ZUNGQ20RG.id) || (rg.id == zungCheckBinding.ZUNGQ11RG.id) )
        {
            if (rg.get(0).id == radioButtonId)
                result = 4
            if (rg.get(1).id == radioButtonId)
                result = 3
            if (rg.get(2).id == radioButtonId)
                result = 2
            if (rg.get(3).id == radioButtonId)
                result = 1
        }
        return result
    }

    fun hidePopPutermsLayout()
    {
        zungCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String , questNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questNo)
        {
            1 ->
            {
                zungCheckBinding.ZUNGQ1RG.requestFocus()
            }

            2 ->
            {
                zungCheckBinding.ZUNGQ2RG.requestFocus()
            }

            3 ->
            {
                zungCheckBinding.ZUNGQ3RG.requestFocus()
            }

            4 ->
            {
                zungCheckBinding.ZUNGQ4RG.requestFocus()
            }

            5 ->
            {
                zungCheckBinding.ZUNGQ5RG.requestFocus()
            }

            6 ->
            {
                zungCheckBinding.ZUNGQ6RG.requestFocus()
            }

            7 ->
            {
                zungCheckBinding.ZUNGQ7RG.requestFocus()
            }

            8 ->
            {
                zungCheckBinding.ZUNGQ8RG.requestFocus()
            }

            9 ->
            {
                zungCheckBinding.ZUNGQ9RG.requestFocus()
            }

            10 ->
            {
                zungCheckBinding.ZUNGQ10RG.requestFocus()
            }

            11 ->
            {
                zungCheckBinding.ZUNGQ11RG.requestFocus()
            }

            12 ->
            {
                zungCheckBinding.ZUNGQ12RG.requestFocus()
            }

            13 ->
            {
                zungCheckBinding.ZUNGQ13RG.requestFocus()
            }

            14 ->
            {
                zungCheckBinding.ZUNGQ14RG.requestFocus()
            }

            15 ->
            {
                zungCheckBinding.ZUNGQ15RG.requestFocus()
            }

            16 ->
            {
                zungCheckBinding.ZUNGQ16RG.requestFocus()
            }

            17 ->
            {
                zungCheckBinding.ZUNGQ17RG.requestFocus()
            }

            18 ->
            {
                zungCheckBinding.ZUNGQ18RG.requestFocus()
            }

            19 ->
            {
                zungCheckBinding.ZUNGQ19RG.requestFocus()
            }

            20 ->
            {
                zungCheckBinding.ZUNGQ20RG.requestFocus()
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
            CheckZUNGFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}