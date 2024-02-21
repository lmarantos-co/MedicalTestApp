package com.example.cvdriskestimator.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.CVDTest
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentCheckBinding
import com.example.cvdriskestimator.viewModels.CheckPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckPatientViewModelFactory
import java.util.*


class CheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var checkBinding: FragmentCheckBinding
    private lateinit var checkPatientViewModel: CheckPatientViewModel
    private lateinit var historyFragment: HistoryFragment
    private var selectedPatientTest = Test()

    private  var loginFragment: LoginFragment = LoginFragment.newInstance()
    private  var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popUpMenuComp: PopUpMenu
    private var mteTitleFormHeight : Int = 0


    //data required for the calculation
    private  var sex : String = ""
    private var race : String = ""

    //end of data


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val actionBar: androidx.appcompat.app.ActionBar? = mainActivity.supportActionBar
//        actionBar!!.setDisplayHomeAsUpEnabled(true)
//        actionBar!!.setHomeButtonEnabled(true)
//        actionBar!!.setDisplayShowHomeEnabled(true)
//        actionBar!!.hide()
//        mainActivity.supportActionBar!!.hide()
        //initialize the viewmodel
        val factory = CheckPatientViewModelFactory()
        checkPatientViewModel = ViewModelProvider(this , factory)[CheckPatientViewModel::class.java]

        // Inflate the layout for this fragment
        checkBinding = FragmentCheckBinding.inflate(inflater, container, false)
        checkBinding.checkPatientViewModel = checkPatientViewModel
        checkBinding.lifecycleOwner = this
        checkPatientViewModel.setActivity(mainActivity)
        checkPatientViewModel.setFragment(this)
        return checkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        val userName = requireActivity().getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.requireArguments().getString("patientId")
        var testDate = this.requireArguments().getString("testDate" , "")
        var openType = this.requireArguments().getString("openType")

        if (openType == "open_history")
        {
            var historyTest = CVDTest()
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
                    historyTest = checkPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.cvdTestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                checkPatientViewModel.setPatientDataOnForm()
            }
            if (openType == "addNew")
            {
                checkPatientViewModel.setUserDummyData()
               // checkPatientViewModel.setPatientDataOnForm()
            }
            if (openType == "history")
            {
                checkPatientViewModel.history()
            }
        }

        //observe live data change
        checkPatientViewModel.patientDATA.observe(viewLifecycleOwner , Observer {
        })

        checkPatientViewModel.testDATA.observe(viewLifecycleOwner , Observer {
            if (it != null)
                setPatientData(it)
        })

        checkBinding.formConLayout.setBackgroundColor(mainActivity.resources.getColor(R.color.MidnightBlue))

        checkBinding.includeCvdTitleForm.cvdTitleForm.post {
            mteTitleFormHeight = checkBinding.includeCvdTitleForm.cvdTitleForm.layoutParams.height
        }

        checkBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenuComp.showPopUp(checkBinding.includeCvdTitleForm.userIcon)
        }

        popUpMenuComp = PopUpMenu(checkBinding.includePopUpMenu.termsRelLayout , mainActivity , this, loginFragment ,registerFragment , null, leaderBoardFragment)

        checkBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        checkBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun initUI(view : View) {


        checkBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }

        checkBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        checkBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hideTermsOfUseLayout()
        }

        checkBinding.includeCvdTitleForm.userIcon.alpha = 1f
//        setUserIconDimens()
        setAllComponentsWithoudDB()
        getSex()
        checkBinding.clearBtn.setOnClickListener {

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
                .show()
        }

        checkBinding.submitBtn.setOnClickListener {
            hideSoftKeyboard()
            checkPatientViewModel.checkOnAllDataConstraints(
                checkBinding.ageEdTxt.text.toString(),
                checkBinding.sexRG,
                checkBinding.sbbEdTxt.text.toString(),
                checkBinding.tchEdTxt.text.toString(),
                checkBinding.hchEdTxt.text.toString(),
                checkBinding.SmokeRGr,
                checkBinding.TreatRadioGroup
            )

        }
    }

    private fun hideSoftKeyboard()
    {
        val view : View = mainActivity.window.decorView.rootView
        val imm = mainActivity.applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken , 0)
    }

    private fun setAllComponentsWithoudDB() {

        //set onClick listeners on the info views
        checkBinding.imgVInfo.setOnClickListener {
            Toast.makeText(
                requireActivity().applicationContext,
                resources.getString(R.string.age_info),
                Toast.LENGTH_LONG
            ).show()
        }

        checkBinding.imgVInfo2.setOnClickListener {
            Toast.makeText(
                requireActivity().applicationContext,
                resources.getString(R.string.smoker_info),
                Toast.LENGTH_LONG
            ).show()
        }

    }



    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        var widthPixel = displayMetrics.widthPixels

        checkBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        checkBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        checkBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        checkBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }




    fun initPatientData() {
        checkBinding.ageEdTxt.setText("")
        checkBinding.ageEdTxt.invalidate()
        checkBinding.sbbEdTxt.setText("")
        checkBinding.tchEdTxt.setText("")
        checkBinding.hchEdTxt.setText("")
        checkBinding.sexRG.clearCheck()
        checkBinding.raceRG.clearCheck()
        checkBinding.SmokeRGr.clearCheck()
        checkBinding.TreatRadioGroup.clearCheck()
    }

    private fun setPatientData(test : CVDTest)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initPatientData()
            checkBinding.ageEdTxt.setText(test.patientAge)
            checkBinding.ageEdTxt.invalidate()
            setRaceStatus(test.patientRace!!)
            setSexStatus(test.patientSex!!)
            checkBinding.sbbEdTxt.setText(test.SSB)
            checkBinding.tchEdTxt.setText(test.TCH)
            checkBinding.hchEdTxt.setText(test.HDL)
            setSmokingStatus(test.smoker!!)
            setTreatmentStatus(test.treatment!!)
        }, 1000)

    }


    private fun getSex() : String
    {
        var sex : String = ""
        when (checkBinding.sexRG.checkedRadioButtonId)
        {
            R.id.maleRB ->
                sex = "MALE"
            R.id.femaleRB ->
                sex = "FEMALE"
        }
        return sex
    }

    private fun setSexStatus(sex : String)
    {
        when(sex)
        {
            "MALE" ->
            {
                checkBinding.sexRG.check(R.id.maleRB)
            }
            "FEMALE" ->
            {
                checkBinding.sexRG.check(R.id.femaleRB)
            }
        }
    }

    private fun setRaceStatus(race : String)
    {
        when(race){
            "White" ->
            {
                checkBinding.raceRG.check(R.id.raceRB1)
            }
            "African American" ->
            {
                checkBinding.raceRG.check(R.id.raceRB2)
            }
            "Other" ->
            {
                checkBinding.raceRG.check(R.id.raceRB3)
            }
        }
    }

    private fun setSmokingStatus(smokingStatus : String)
    {
        when(smokingStatus)
        {
            "Current" ->
            {
                checkBinding.SmokeRGr.check(R.id.smokeRB1)
            }
            "Former" ->
            {
                checkBinding.SmokeRGr.check(R.id.smokeRB2)
            }
            "Never" ->
            {
                checkBinding.SmokeRGr.check(R.id.smokeRB3)
            }
        }
    }


    private fun setTreatmentStatus(TreatmentStatus : String)
    {
        when(TreatmentStatus)
        {
            "Yes" ->
            {
                checkBinding.TreatRadioGroup.check(R.id.treatRButton1)
            }
            "No" ->
            {
                checkBinding.TreatRadioGroup.check(R.id.treatRadioButton2)
            }
        }
    }


    fun displayAgeError(error : String)
    {
        checkBinding.ageEdTxt.error = error
        checkBinding.ageEdTxt.requestFocus()
    }

    fun displaySBPError(error : String)
    {
        checkBinding.sbbEdTxt.error = error
        checkBinding.sbbEdTxt.requestFocus()
    }

    fun displayTCHError(error : String)
    {
        checkBinding.tchEdTxt.error = error
        checkBinding.tchEdTxt.requestFocus()
    }

    fun displayHDLError(error : String)
    {
        checkBinding.hchEdTxt.error = error
        checkBinding.hchEdTxt.requestFocus()
    }

    fun displayRadioGroupError(groupNo : Int)
    {
        when(groupNo)
        {
            1 ->
            {
                checkBinding.sexTxtView.requestFocus()
                Toast.makeText(mainActivity.applicationContext , "Please select Sex Status.", Toast.LENGTH_LONG).show()
            }

            2 ->
            {
                checkBinding.smkTxtV
                Toast.makeText(mainActivity.applicationContext , "Please select Smoking Status.", Toast.LENGTH_LONG).show()
            }

            3 ->
            {
                checkBinding.treatTxtV
                Toast.makeText(mainActivity.applicationContext , "Please select Treatmet Status.", Toast.LENGTH_LONG).show()
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

    private fun convertStringToLocalDateFormater(date : String) : String
    {
        val month = date.split(" ")
        var monthNo : String = ""
        when (month.get(1))
        {
            "Jan" -> {
                monthNo = "0"
            }
            "Feb" -> {
                monthNo = "01"
            }
            "Mar" -> {
                monthNo = "02"
            }
            "Apr" -> {
                monthNo = "03"
            }
            "May" -> {
                monthNo = "04"
            }
            "Jun" -> {
                monthNo = "05"
            }
            "Jul" -> {
                monthNo = "06"
            }
            "Aug" -> {
                monthNo = "07"
            }
            "Sep" -> {
                monthNo = "08"
            }
            "Oct" -> {
                monthNo = "09"
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

        return date
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
        var year = allDateParts.get(5)
        //var date = Date(year.toInt(), monthNo, dateName.toInt())
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR , year.toInt())
        calendar.set(Calendar.MONTH , monthNo)
        calendar.set(Calendar.DAY_OF_MONTH , dateName.toInt())
//        calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
//        calendar.set(Calendar.MINUTE, min.toInt())
//        calendar.set(Calendar.SECOND, sec.toInt())
        var date = calendar.time
        return calendar.time
    }

    private fun showTermsOfUseLayout() {

        checkBinding.includePopUpMenu.termsRelLayout.visibility = View.VISIBLE
    }


    private fun hideTermsOfUseLayout() {
        checkBinding.includePopUpMenu.termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CheckFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CheckFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}