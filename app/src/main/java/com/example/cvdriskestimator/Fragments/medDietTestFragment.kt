package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentMedDietTestBinding
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckMedDietTestViewModel
import com.example.cvdriskestimator.viewModels.CheckMedDietTestViewModelFactory
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [medDietTestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class medDietTestFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var medDietTestViewModel: CheckMedDietTestViewModel
    private lateinit var medDietTestBinding: FragmentMedDietTestBinding
    private lateinit var checkMedDietTestViewModelFactory: CheckMedDietTestViewModelFactory
    private lateinit var registerFragment: RegisterFragment
    private lateinit var loginFragment: LoginFragment
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popUpMenu: PopUpMenu
    private  var allPatientValues = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1)

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
        medDietTestViewModel = CheckMedDietTestViewModel()
        checkMedDietTestViewModelFactory = CheckMedDietTestViewModelFactory()
        medDietTestViewModel =  ViewModelProvider(this, checkMedDietTestViewModelFactory).get(CheckMedDietTestViewModel::class.java)
        medDietTestViewModel.setActivity(mainActivity)
        medDietTestViewModel.setFragment(this)
        medDietTestViewModel.initialiseRealm()
        medDietTestBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_med_diet_test, container , false)
        medDietTestBinding.lifecycleOwner = this
        return medDietTestBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medDietTestBinding.includeCvdTitleForm.userIcon.alpha = 1f

        medDietTestBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate")
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
//                    var testDateFormated = convertStringToCalenderDate(testDate!!)
//                    val localDate = LocalDate.parse(testDateFormated)
//                    val text: String = localDate.format(formatter)
//                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
//                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
//                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
                    historyTest = medDietTestViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.patientMDSTestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                medDietTestViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                medDietTestViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                medDietTestViewModel.history()
            }
        }


        //set the observer for the patient data coming from view model
        medDietTestViewModel.patientData.observe(viewLifecycleOwner) {
        }

        medDietTestViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        medDietTestBinding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.backToActivity()
        }
        initUI(view)
    }



    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ1 , test.patientMDSQ1)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ2 , test.patientMDSQ2)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ3 , test.patientMDSQ3)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ4 , test.patientMDSQ4)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ5 , test.patientMDSQ5)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ6 , test.patientMDSQ6)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ7 , test.patientMDSQ7)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ8 , test.patientMDSQ8)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ9 , test.patientMDSQ9)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ10 , test.patientMDSQ10)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ11 , test.patientMDSQ11)
        } , 1000)
    }

    fun initialisePatientData()
    {
        medDietTestBinding.medDietScoreRGQ1.clearCheck()
        medDietTestBinding.medDietScoreRGQ2.clearCheck()
        medDietTestBinding.medDietScoreRGQ3.clearCheck()
        medDietTestBinding.medDietScoreRGQ4.clearCheck()
        medDietTestBinding.medDietScoreRGQ5.clearCheck()
        medDietTestBinding.medDietScoreRGQ6.clearCheck()
        medDietTestBinding.medDietScoreRGQ7.clearCheck()
        medDietTestBinding.medDietScoreRGQ8.clearCheck()
        medDietTestBinding.medDietScoreRGQ9.clearCheck()
        medDietTestBinding.medDietScoreRGQ10.clearCheck()
        medDietTestBinding.medDietScoreRGQ11.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup , checkRadioButton : Int?)
    {
        when (checkRadioButton)
        {
            0 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            1 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            2 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            3 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            4 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            5 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if (rg.get(0).id == radioButtonId)
            result = 0
        if (rg.get(1).id == radioButtonId)
            result = 1
        if (rg.get(2).id == radioButtonId)
            result = 2
        if (rg.get(3).id == radioButtonId)
            result = 3
        if (rg.get(4).id == radioButtonId)
            result = 4
        if (rg.get(5).id == radioButtonId)
            result = 5
        return result
    }

    fun hidePopPutermsLayout()
    {
        medDietTestBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                medDietTestBinding.Question1TxtV.requestFocus()
            }

            2 ->
            {
                medDietTestBinding.Question2TxtV.requestFocus()
            }

            3 ->
            {
                medDietTestBinding.Question3TxtV.requestFocus()
            }

            4 ->
            {
                medDietTestBinding.Question4TxtV.requestFocus()
            }

            5 ->
            {
                medDietTestBinding.Question5TxtV.requestFocus()
            }

            7 ->
            {
                medDietTestBinding.Question7TxtV.requestFocus()
            }

            8 ->
            {
                medDietTestBinding.Question8TxtV.requestFocus()
            }

            9 ->
            {
                medDietTestBinding.Question9TxtV.requestFocus()
            }

            10 ->
            {
                medDietTestBinding.Question10TxtV.requestFocus()
            }

            11 ->
            {
                medDietTestBinding.Question11TxtV.requestFocus()
            }


        }
    }

    private fun initUI(view : View)
    {
        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()

        medDietTestBinding.clearBtn.setOnClickListener {
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

        popUpMenu = PopUpMenu(medDietTestBinding.includePopUpMenu.termsRelLayout , mainActivity , this , loginFragment , registerFragment , null , leaderBoardFragment)
//        medDietTestBinding.includeCvdTitleForm.userIcon.setOnClickListener {
//            medDietTestBinding.includePopUpMenu.includePopupMenu.visibility = View.VISIBLE
//        }

        medDietTestBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenu.showPopUp(medDietTestBinding.includeCvdTitleForm.userIcon)
        }

        medDietTestBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        medDietTestBinding.submitBtn.setOnClickListener {
            //get the patient values into the arraylist
            allPatientValues[0] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ1)
            allPatientValues[1] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ2)
            allPatientValues[2] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ3)
            allPatientValues[3] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ4)
            allPatientValues[4] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ5)
            allPatientValues[5] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ6)
            allPatientValues[6] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ7)
            allPatientValues[7] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ8)
            allPatientValues[8] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ9)
            allPatientValues[9] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ10)
            allPatientValues[10] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ11)
            medDietTestViewModel.checkMDSTestPatient(allPatientValues)
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
            medDietTestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}