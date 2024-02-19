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
import com.example.cvdriskestimator.RealmDB.BAITest
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentBAICheckBinding
import com.example.cvdriskestimator.viewModels.CheckBAIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckBAIPatientViewModelFactory
import java.sql.Date
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [BAICheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BAICheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var baiPatientViewModel: CheckBAIPatientViewModel
    private lateinit var baiCheckBinding : FragmentBAICheckBinding
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
        baiCheckBinding = FragmentBAICheckBinding.inflate(inflater , container , false)
        baiPatientViewModel = CheckBAIPatientViewModel()
        val checkBAIPatientViewModelFactory = CheckBAIPatientViewModelFactory()
        baiPatientViewModel = ViewModelProvider(this , checkBAIPatientViewModelFactory).get(CheckBAIPatientViewModel::class.java)
        baiCheckBinding.checkBAIPatientViewModel = baiPatientViewModel
        baiCheckBinding.lifecycleOwner = this
        return baiCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baiPatientViewModel.passActivity(mainActivity)
        baiPatientViewModel.passFragment(this)
        baiPatientViewModel.initialiseRealm()

        baiCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.requireArguments().getString("patientId")
        var testDate = this.requireArguments().getString("testDate" , "")
        var openType = this.requireArguments().getString("openType")


        if (openType == "open_history")
        {
            var historyTest = BAITest()
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
                    historyTest = baiPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.patientBAITestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                baiPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                baiPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        baiPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        baiPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        baiCheckBinding.clearBtn.setOnClickListener {
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

        baiCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ17RG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ18RG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ19RG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ20RG)
            allPatientSelections[20] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ21RG)



            baiPatientViewModel.checkBAITestPatient(allPatientSelections)
        }

        baiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(baiCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null , leaderBoardFragment)

        baiCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        baiCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        baiCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        baiCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : BAITest)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(baiCheckBinding.BAIQ1RG , test.patientBAIQ1)
            setQuestionRadioGroup(baiCheckBinding.BAIQ2RG , test.patientBAIQ2)
            setQuestionRadioGroup(baiCheckBinding.BAIQ3RG , test.patientBAIQ3)
            setQuestionRadioGroup(baiCheckBinding.BAIQ4RG , test.patientBAIQ4)
            setQuestionRadioGroup(baiCheckBinding.BAIQ5RG , test.patientBAIQ5)
            setQuestionRadioGroup(baiCheckBinding.BAIQ6RG , test.patientBAIQ6)
            setQuestionRadioGroup(baiCheckBinding.BAIQ7RG , test.patientBAIQ7)
            setQuestionRadioGroup(baiCheckBinding.BAIQ8RG , test.patientBAIQ8)
            setQuestionRadioGroup(baiCheckBinding.BAIQ9RG , test.patientBAIQ9)
            setQuestionRadioGroup(baiCheckBinding.BAIQ10RG , test.patientBAIQ10)
            setQuestionRadioGroup(baiCheckBinding.BAIQ11RG , test.patientBAIQ11)
            setQuestionRadioGroup(baiCheckBinding.BAIQ12RG , test.patientBAIQ12)
            setQuestionRadioGroup(baiCheckBinding.BAIQ13RG , test.patientBAIQ13)
            setQuestionRadioGroup(baiCheckBinding.BAIQ14RG , test.patientBAIQ14)
            setQuestionRadioGroup(baiCheckBinding.BAIQ15RG , test.patientBAIQ15)
            setQuestionRadioGroup(baiCheckBinding.BAIQ16RG , test.patientBAIQ16)
            setQuestionRadioGroup(baiCheckBinding.BAIQ17RG , test.patientBAIQ17)
            setQuestionRadioGroup(baiCheckBinding.BAIQ18RG , test.patientBAIQ18)
            setQuestionRadioGroup(baiCheckBinding.BAIQ19RG , test.patientBAIQ19)
            setQuestionRadioGroup(baiCheckBinding.BAIQ20RG , test.patientBAIQ20)
            setQuestionRadioGroup(baiCheckBinding.BAIQ21RG , test.patientBAIQ21)
        } , 1000)
    }

    fun initialisePatientData()
    {
        baiCheckBinding.BAIQ1RG.clearCheck()
        baiCheckBinding.BAIQ2RG.clearCheck()
        baiCheckBinding.BAIQ3RG.clearCheck()
        baiCheckBinding.BAIQ4RG.clearCheck()
        baiCheckBinding.BAIQ5RG.clearCheck()
        baiCheckBinding.BAIQ6RG.clearCheck()
        baiCheckBinding.BAIQ7RG.clearCheck()
        baiCheckBinding.BAIQ8RG.clearCheck()
        baiCheckBinding.BAIQ9RG.clearCheck()
        baiCheckBinding.BAIQ10RG.clearCheck()
        baiCheckBinding.BAIQ11RG.clearCheck()
        baiCheckBinding.BAIQ12RG.clearCheck()
        baiCheckBinding.BAIQ13RG.clearCheck()
        baiCheckBinding.BAIQ14RG.clearCheck()
        baiCheckBinding.BAIQ15RG.clearCheck()
        baiCheckBinding.BAIQ16RG.clearCheck()
        baiCheckBinding.BAIQ17RG.clearCheck()
        baiCheckBinding.BAIQ18RG.clearCheck()
        baiCheckBinding.BAIQ19RG.clearCheck()
        baiCheckBinding.BAIQ20RG.clearCheck()
        baiCheckBinding.BAIQ21RG.clearCheck()

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
        if (rg.get(0).id == radioButtonId)
            result = 0
        if (rg.get(1).id == radioButtonId)
            result = 1
        if (rg.get(2).id == radioButtonId)
            result = 2
        if (rg.get(3).id == radioButtonId)
            result = 3
        return result
    }

    fun hidePopPutermsLayout()
    {
        baiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String , questNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questNo)
        {
            1 ->
            {
                baiCheckBinding.BAIQ1TxtV.requestFocus()
            }

            2 ->
            {
                baiCheckBinding.BAIQ2TxtV.requestFocus()
            }

            3 ->
            {
                baiCheckBinding.BAIQ3TxtV.requestFocus()
            }

            4 ->
            {
                baiCheckBinding.BAIQ4TxtV.requestFocus()
            }

            5 ->
            {
                baiCheckBinding.BAIQ5TxtV.requestFocus()
            }

            6 ->
            {
                baiCheckBinding.BAIQ6TxtV.requestFocus()
            }

            7 ->
            {
                baiCheckBinding.BAIQ7TxtV.requestFocus()
            }

            8 ->
            {
                baiCheckBinding.BAIQ8TxtV.requestFocus()
            }

            9 ->
            {
                baiCheckBinding.BAIQ9TxtV.requestFocus()
            }

            10 ->
            {
                baiCheckBinding.BAIQ10TxtV.requestFocus()
            }

            11 ->
            {
                baiCheckBinding.BAIQ11TxtV.requestFocus()
            }

            12 ->
            {
                baiCheckBinding.BAIQ12TxtV.requestFocus()
            }

            13 ->
            {
                baiCheckBinding.BAIQ13TxtV.requestFocus()
            }

            14 ->
            {
                baiCheckBinding.BAIQ14TxtV.requestFocus()
            }

            15 ->
            {
                baiCheckBinding.BAIQ15TxtV.requestFocus()
            }

            16 ->
            {
                baiCheckBinding.BAIQ16TxtV.requestFocus()
            }

            17 ->
            {
                baiCheckBinding.BAIQ17TxtV.requestFocus()
            }

            18 ->
            {
                baiCheckBinding.BAIQ18TxtV.requestFocus()
            }

            19 ->
            {
                baiCheckBinding.BAIQ19TxtV.requestFocus()
            }

            20 ->
            {
                baiCheckBinding.BAIQ20TxtV.requestFocus()
            }

            21 ->
            {
                baiCheckBinding.BAIQ21TxtV.requestFocus()
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
            BAICheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}