package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.core.view.get
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentGDSCheckBinding
import com.example.cvdriskestimator.databinding.FragmentMDICheckBinding
import com.example.cvdriskestimator.viewModels.CheckGDSPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckGDSViewModel
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModelFactory
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class GDSCheckFragment : Fragment() {

    private lateinit var gdsCheckBinding: FragmentGDSCheckBinding
    private lateinit var gdsPatientViewModel: CheckGDSViewModel
    private lateinit var gdsPatientViewModelFactory : CheckGDSPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
//    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1, 1, 1, 1 ,1 , 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        gdsCheckBinding = FragmentGDSCheckBinding.inflate(inflater , container , false)
        gdsPatientViewModel = CheckGDSViewModel()
        gdsPatientViewModelFactory = CheckGDSPatientViewModelFactory()
        gdsPatientViewModel =  ViewModelProvider(this, gdsPatientViewModelFactory).get(
            CheckGDSViewModel::class.java)
        gdsCheckBinding.checkGDSViewModel = gdsPatientViewModel
        gdsCheckBinding.lifecycleOwner = this
        return gdsCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gdsPatientViewModel.passActivity(mainActivity)
        gdsPatientViewModel.passFragment(this)
        gdsPatientViewModel.initialiseRealm()

        gdsCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

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
                    historyTest = gdsPatientViewModel.fetchHistoryTest(patientId!! , testDateFormated!!)
                }
            }
            if (historyTest.patientGDSTestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                gdsPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                gdsPatientViewModel.initialiseUserDummy()
//                bpiPatientViewModel.setPatientDataOnForm()
            }
            if (openType == "history")
            {
                gdsPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        gdsPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        gdsPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        gdsCheckBinding.clearBtn.setOnClickListener {

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
                .show()        }

        gdsCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS1QRG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS2QRG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS3QRG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS4QRG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS5QRG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS6QRG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS7QRG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS8QRG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS9QRG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS10QRG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS11QRG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS12QRG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS13QRG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS14QRG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS15QRG)


            gdsPatientViewModel.checkGDSTestPatient(allPatientSelections)
        }

        gdsCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(gdsCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null ,leaderBoardFragment)

        gdsCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        gdsCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        gdsCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        gdsCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

        gdsCheckBinding.historyBtn.setOnClickListener {
            gdsPatientViewModel.history()
        }
    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(gdsCheckBinding.GDS1QRG , test.patientGDSQ1)
            setQuestionRadioGroup(gdsCheckBinding.GDS2QRG , test.patientGDSQ2)
            setQuestionRadioGroup(gdsCheckBinding.GDS3QRG , test.patientGDSQ3)
            setQuestionRadioGroup(gdsCheckBinding.GDS4QRG , test.patientGDSQ4)
            setQuestionRadioGroup(gdsCheckBinding.GDS5QRG , test.patientGDSQ5)
            setQuestionRadioGroup(gdsCheckBinding.GDS6QRG , test.patientGDSQ6)
            setQuestionRadioGroup(gdsCheckBinding.GDS7QRG , test.patientGDSQ7)
            setQuestionRadioGroup(gdsCheckBinding.GDS8QRG , test.patientGDSQ8)
            setQuestionRadioGroup(gdsCheckBinding.GDS9QRG , test.patientGDSQ9)
            setQuestionRadioGroup(gdsCheckBinding.GDS10QRG , test.patientGDSQ10)
            setQuestionRadioGroup(gdsCheckBinding.GDS11QRG , test.patientGDSQ11)
            setQuestionRadioGroup(gdsCheckBinding.GDS12QRG , test.patientGDSQ12)
            setQuestionRadioGroup(gdsCheckBinding.GDS13QRG , test.patientGDSQ13)
            setQuestionRadioGroup(gdsCheckBinding.GDS14QRG , test.patientGDSQ14)
            setQuestionRadioGroup(gdsCheckBinding.GDS15QRG , test.patientGDSQ15)

        } , 1000)
    }

    fun initialisePatientData()
    {
        gdsCheckBinding.GDS1QRG.clearCheck()
        gdsCheckBinding.GDS2QRG.clearCheck()
        gdsCheckBinding.GDS3QRG.clearCheck()
        gdsCheckBinding.GDS4QRG.clearCheck()
        gdsCheckBinding.GDS5QRG.clearCheck()
        gdsCheckBinding.GDS6QRG.clearCheck()
        gdsCheckBinding.GDS7QRG.clearCheck()
        gdsCheckBinding.GDS8QRG.clearCheck()
        gdsCheckBinding.GDS9QRG.clearCheck()
        gdsCheckBinding.GDS10QRG.clearCheck()
        gdsCheckBinding.GDS11QRG.clearCheck()
        gdsCheckBinding.GDS12QRG.clearCheck()
        gdsCheckBinding.GDS13QRG.clearCheck()
        gdsCheckBinding.GDS14QRG.clearCheck()
        gdsCheckBinding.GDS15QRG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            1 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
            0 ->
            {
                (rg.getChildAt(0) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if ((rg.id != gdsCheckBinding.GDS1QRG.id) && (rg.id != gdsCheckBinding.GDS5QRG.id)
            && (rg.id != gdsCheckBinding.GDS7QRG.id) && (rg.id != gdsCheckBinding.GDS11QRG.id)
            && (rg.id != gdsCheckBinding.GDS13QRG.id))
        {
            if (rg.get(1).id == radioButtonId)
                result = 1
            if (rg.get(0).id == radioButtonId)
                result = 0
        }
        else
        {
            if (rg.get(1).id == radioButtonId)
                result = 0
            if (rg.get(0).id == radioButtonId)
                result = 1
        }
        return result
    }

    fun hidePopPutermsLayout()
    {
        gdsCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                gdsCheckBinding.GDS1QRG.requestFocus()
            }

            2 ->
            {
                gdsCheckBinding.GDS2QRG.requestFocus()
            }

            3 ->
            {
                gdsCheckBinding.GDS3QRG.requestFocus()
            }

            4 ->
            {
                gdsCheckBinding.GDS4QRG.requestFocus()
            }

            5 ->
            {
                gdsCheckBinding.GDS5QRG.requestFocus()
            }

            6 ->
            {
                gdsCheckBinding.GDS6QRG.requestFocus()
            }

            7 ->
            {
                gdsCheckBinding.GDS7QRG.requestFocus()
            }

            8 ->
            {
                gdsCheckBinding.GDS8QRG.requestFocus()
            }

            9 ->
            {
                gdsCheckBinding.GDS9QRG.requestFocus()
            }

            10 ->
            {
                gdsCheckBinding.GDS10QRG.requestFocus()
            }

            11 ->
            {
                gdsCheckBinding.GDS11QRG.requestFocus()
            }

            12 ->
            {
                gdsCheckBinding.GDS12QRG.requestFocus()
            }

            13 ->
            {
                gdsCheckBinding.GDS13QRG.requestFocus()
            }

            14 ->
            {
                gdsCheckBinding.GDS14QRG.requestFocus()
            }

            15 ->
            {
                gdsCheckBinding.GDS15QRG.requestFocus()
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
        /**

         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GDSCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}