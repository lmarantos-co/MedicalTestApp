package com.example.cvdriskestimator.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentDiabetesCheckBinding
import com.example.cvdriskestimator.viewModels.CheckDiabetesPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckDiabetesPatientViewModelFactory
import java.lang.reflect.Method
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [DiabetesCheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiabetesCheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var diabetesCheckBinding : FragmentDiabetesCheckBinding
    private var checkDiabetesPatientViewModel =  CheckDiabetesPatientViewModel()

    private  var loginFragment: LoginFragment = LoginFragment.newInstance()
    private  var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popUpMenu: PopUpMenu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        CheckDiabetesPatientViewModelFactory()
        checkDiabetesPatientViewModel = ViewModelProviders.of(this).get(CheckDiabetesPatientViewModel::class.java)

        // Inflate the layout for this fragment
        diabetesCheckBinding = FragmentDiabetesCheckBinding.inflate(inflater, container, false)
        diabetesCheckBinding.lifecycleOwner = this
        diabetesCheckBinding.checkDiabetesPatientViewModel = checkDiabetesPatientViewModel

        checkDiabetesPatientViewModel.setMainActivity(mainActivity)
        checkDiabetesPatientViewModel.setDiabetesCheckFragment(this)
        initPatientData()
        checkDiabetesPatientViewModel.initRealm()
        return diabetesCheckBinding.root
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?)
    {

        val userName = activity!!.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

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
//                    var testDateFormated = convertStringToCalenderDate(testDate)
//                    val localDate = LocalDate.parse(testDateFormated)
//                    val text: String = localDate.format(formatter)
//                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
//                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
//                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
                    historyTest = checkDiabetesPatientViewModel.fetchHistoryTest(patientId!! , testDate)
                }
            }
            if (historyTest.diabetesTestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                checkDiabetesPatientViewModel.setPatientDataOnForm()
            }
            if (openType == "addNew")
            {
                checkDiabetesPatientViewModel.initialiseUserDummy()
//                checkDiabetesPatientViewModel.setPatientDataOnForm()
            }
            if (openType == "history")
            {
                checkDiabetesPatientViewModel.history()
            }
        }



        //observe live data change
        checkDiabetesPatientViewModel.patientDATA.observe(viewLifecycleOwner) {
        }

        checkDiabetesPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
            setPatientData(it)
        }

        diabetesCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }

        diabetesCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hideTermsOfUseLayout()
        }

        diabetesCheckBinding.formConLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

//        setUserIconDimens()

        diabetesCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        diabetesCheckBinding.clearBtn.setOnClickListener {

            AlertDialog.Builder(this.activity)
                .setTitle("Clear All Data")
                .setMessage("Are you sure you want to delete the user data?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation

                        initPatientData()

                    })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()        }

        diabetesCheckBinding.submitBtn.setOnClickListener {
            hideSoftKeyboard()
            var correctRGInput : Boolean = true
            var toastMessageShown : Boolean = false
            //quick form validation for RadioGroup
            if (getPatientSex() == "")
            {
                Toast.makeText(mainActivity.applicationContext, "Please select SEX Status.", Toast.LENGTH_LONG).show()
                diabetesCheckBinding.sextxtV.requestFocus()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getPrescribedAntiHypertensionMedication() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, "Please select Prescribed Antihypertensive Medication Status.", Toast.LENGTH_LONG).show()
                diabetesCheckBinding.steroidstxtV.requestFocus()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getPrescribedSteroids() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, " Please select PrescribedSteroids Status" , Toast.LENGTH_LONG).show()
                diabetesCheckBinding.pamtxtV.requestFocus()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getSiblingWithDiabetes() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, " Please select Sibling Family History Status" , Toast.LENGTH_LONG).show()
                diabetesCheckBinding.siblingstxtV.requestFocus()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getSmokingStatus() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, " Please select Smoking Status" , Toast.LENGTH_LONG).show()
                diabetesCheckBinding.smkTxtV.requestFocus()
                correctRGInput = false
                toastMessageShown = true
            }

            //call the method to pass the arguments to view model for verification and process
            if (correctRGInput)
            {
                checkDiabetesPatientViewModel.checkPatientForDiabetesData(
                    getPatientSex(), getPrescribedAntiHypertensionMedication(), getPrescribedSteroids(),
                    getPatientAge(), getPatientBMI(), getSiblingWithDiabetes(), getSmokingStatus()
                )
            }

        }

        //initialize popUpMenu Component
        popUpMenu = PopUpMenu(diabetesCheckBinding.includePopUpMenu.termsRelLayout, mainActivity, this, loginFragment, registerFragment , null ,leaderBoardFragment)

        diabetesCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenu.showPopUp(diabetesCheckBinding.includeCvdTitleForm.userIcon)
        }

        diabetesCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        diabetesCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

    }

    private fun hideSoftKeyboard()
    {
        val view : View = mainActivity.window.decorView.rootView
        val imm = mainActivity.applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken , 0)
    }

    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthPixel = displayMetrics.widthPixels

        diabetesCheckBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        diabetesCheckBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        diabetesCheckBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        diabetesCheckBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }



    private fun getPatientSex() : String
    {
        var patientSex : String = ""
        when(diabetesCheckBinding.sexRG.checkedRadioButtonId) {
            diabetesCheckBinding.sexRG.getChildAt(0).id -> {
                patientSex = "MALE"
            }
            diabetesCheckBinding.sexRG.getChildAt(1).id -> {
                patientSex = "FEMALE"
            }

        }
        return patientSex
    }


    private fun getPrescribedAntiHypertensionMedication() : String
    {
        var patientPAM = ""
        when(diabetesCheckBinding.pamRG.checkedRadioButtonId) {
            diabetesCheckBinding.pamRG.getChildAt(0).id -> {
                patientPAM = "YES"
            }
            diabetesCheckBinding.pamRG.getChildAt(1).id -> {
                patientPAM = "NO"
            }
        }
        return patientPAM
    }

    private fun getPrescribedSteroids() : String
    {
        var patientSteroids = ""
        when (diabetesCheckBinding.steroidsRG.checkedRadioButtonId) {
            diabetesCheckBinding.steroidsRG.getChildAt(0).id -> {
                patientSteroids = "YES"
            }
            diabetesCheckBinding.steroidsRG.getChildAt(1).id -> {
                patientSteroids = "NO"
            }
        }
            return patientSteroids
    }

    private fun getPatientAge() : String
    {
        val patientAge: String = diabetesCheckBinding.editTextAge.text.toString()
        return patientAge
    }

    private fun getPatientBMI() : String
    {
        val patientBMI : String = diabetesCheckBinding.editTextBMI.text.toString()
        return patientBMI
    }

    private fun getSiblingWithDiabetes() : String
    {
        var diabeteSibling = ""
        when(diabetesCheckBinding.siblingsRG.checkedRadioButtonId) {
            diabetesCheckBinding.siblingsYesBothRB.id -> {
                diabeteSibling = "Parent And Sibling With Diabetes"
            }
            diabetesCheckBinding.siblingsYesOrRB.id -> {
                diabeteSibling = "Parent Or Sibling With Diabetes"
            }
            diabetesCheckBinding.siblingsNoRB.id -> {
                diabeteSibling = "No Diabetes"
            }
        }
        return diabeteSibling

    }

    private fun getSmokingStatus() : String
    {
        var smokingStatus =""
        when(diabetesCheckBinding.SmokeRGr.checkedRadioButtonId) {
            diabetesCheckBinding.smokeRB1.id -> {
                smokingStatus = "Current"
            }
            diabetesCheckBinding.smokeRB2.id -> {
                smokingStatus = "Former"
            }
            diabetesCheckBinding.smokeRB3.id -> {
                smokingStatus = "Never"
            }
        }
        return smokingStatus
    }


    fun displayAgeError(error : String)
    {
        diabetesCheckBinding.editTextAge.error = error
    }

    fun displayBMIError(error : String)
    {
        diabetesCheckBinding.editTextBMI.error = error
    }

    fun initPatientData()
    {
        //set sex on UI
        diabetesCheckBinding.sexRG.clearCheck()
        diabetesCheckBinding.pamRG.clearCheck()
        diabetesCheckBinding.steroidsRG.clearCheck()
        diabetesCheckBinding.editTextBMI.setText("")
        diabetesCheckBinding.editTextAge.setText("")
        diabetesCheckBinding.siblingsRG.clearCheck()
        diabetesCheckBinding.SmokeRGr.clearCheck()
    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed(kotlinx.coroutines.Runnable {
            initPatientData()
            val sex = test.patientSex
            //set sex on UI
            if (sex == "MALE")
                diabetesCheckBinding.sexRG.check(R.id.maleRB1)
            if (sex == "FEMALE")
                diabetesCheckBinding.sexRG.check(R.id.femaleRB1)

            val pam = test.patientPAM
            if (pam == "YES")
            {
                diabetesCheckBinding.pamRG.check(R.id.pampositiveRB1)
            }
            if (pam == "NO")
                diabetesCheckBinding.pamRG.check(R.id.pamnegative)

            val steroids = test.patientSteroids
            if (steroids == "YES")
            {
                diabetesCheckBinding.steroidsRG.check(R.id.steroidspositiveRB1)
            }
            if (steroids == "NO")
                diabetesCheckBinding.steroidsRG.check(R.id.steroidsnegativeRB2)


            var BMI = test.patientBMI
            diabetesCheckBinding.editTextBMI.setText(BMI.toString())

            var age = test.patientAge
            diabetesCheckBinding.editTextAge.setText(age.toString())

            var siblings = test.patientSiblings
            if (siblings == "Parent And Sibling With Diabetes")
            {
                diabetesCheckBinding.siblingsRG.check(R.id.siblingsYesBothRB)
            }
            if (siblings == "Parent Or Sibling With Diabetes")
            {
                diabetesCheckBinding.siblingsRG.check(R.id.siblingsYesOrRB)
            }
            if (siblings == "No Diabetes")
            {
                diabetesCheckBinding.siblingsRG.check(R.id.siblingsNoRB)
            }

            var smokingStatus = test.smoker
            if (smokingStatus == "Current")
            {
                diabetesCheckBinding.SmokeRGr.check(R.id.smokeRB1)
            }

            if (smokingStatus == "Former")
            {
                diabetesCheckBinding.SmokeRGr.check(R.id.smokeRB2)
            }

            if (smokingStatus == "Never")
            {
                diabetesCheckBinding.SmokeRGr.check(R.id.smokeRB3)
            }
        } , 1000)

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



    private fun hideTermsOfUseLayout() {
        diabetesCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DiabetesCheckFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DiabetesCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}