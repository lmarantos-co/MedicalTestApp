package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentHistoryBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.realm.Realm
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "patientId"
private const val ARG_PARAM2 = "testName"

class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var bindingHistoryFragment: FragmentHistoryBinding
    private lateinit var testResultsChart: com.github.mikephil.charting.charts.LineChart
    private lateinit var realm : Realm
    private lateinit var mainActivity: MainActivity
    private lateinit var checkFragment: CheckFragment
    private lateinit var diabetesCheckFragment: DiabetesCheckFragment
    private lateinit var gdsCheckFragment: GDSCheckFragment
    private lateinit var hammiltonFragment: HamiltonDepressionFragment
    private lateinit var mdiCheckFragment: MDICheckFragment
    private lateinit var medDietTestFragment: medDietTestFragment
    private lateinit var baiCheckFragment: BAICheckFragment
    private lateinit var bpiCheckFragment: BPICheckFragment
    private lateinit var beckDepressionInventoryFragment: BeckDepressionInventoryFragment
    private lateinit var staiCheckFragment: STAICheckFragment
    private lateinit var dassCheckFragment: DASSCheckFragment
    private lateinit var zungFragment: CheckZUNGFragment
    private lateinit var opqolFragment : OPQOLCheckFragment
    private lateinit var gasCheckFragment: GASCheckFragment1
    private lateinit var sidasCheckFragment: SIDASCheckFragment
    private var registerDoctorFragment =  RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private var dateFromPicked : Boolean = false
    private var dateToPicked : Boolean = false
    private var openDatePicker : Boolean = false
    private var minDateString : String = ""
    private var maxDateString : String = ""
    private var lineChartXPos : Int = 0
    private var lineChartYPos : Int = 0
    private var lineChartWidth : Int = 0
    private var lineChartHeight : Int = 0

    private var SCREEN_HEIGHT : Int = 0
    private var SCREEN_WIDTH : Int = 0

    var clickSource: View? = null
    var touchSource: View? = null
    var offset = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingHistoryFragment = FragmentHistoryBinding.inflate(inflater , container , false)
        return bindingHistoryFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        param1 = this.requireArguments().getString("patientId")!!
        param2 = this.requireArguments().getString("testName")

        bindingHistoryFragment.datePickerLinLayout.visibility = View.GONE

        bindingHistoryFragment.datePickerButton.setOnClickListener {
            if (!openDatePicker)
            {
                bindingHistoryFragment.datePickerLinLayout.visibility = View.VISIBLE
                bindingHistoryFragment.fromDatePicker.visibility = View.VISIBLE
            }
            else
                bindingHistoryFragment.datePickerLinLayout.visibility = View.GONE
            openDatePicker = !openDatePicker
        }

        bindingHistoryFragment.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.backToActivity()
        }

        //set the PopUpMenu
        val popupMenu = PopUpMenu(bindingHistoryFragment.includePopupMenu.termsRelLayout , mainActivity, this,  registerDoctorFragment , null , leaderBoardFragment)

        bindingHistoryFragment.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }

        setColorOnTestTitle()

        var today = Calendar.getInstance()
        var fromSelectDate : Date? = null
        var toSelectDate : Date? = null
        bindingHistoryFragment.fromDatePicker.init(today.get(Calendar.YEAR) , today.get(Calendar.MONTH) , today.get(Calendar.DAY_OF_MONTH))
        { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            fromSelectDate = Date(year , month , day)
            val calendar0: Calendar = Calendar.getInstance()
            calendar0.set(Calendar.YEAR , year)
            calendar0.set(Calendar.MONTH , month)
            calendar0.set(Calendar.DAY_OF_MONTH , day)
            fromSelectDate = calendar0.time
//            calendar0.set(Calendar.HOUR_OF_DAY, fromSelectDate!!.hours)
//            calendar0.set(Calendar.MINUTE, fromSelectDate!!.minutes)
//            calendar0.set(Calendar.SECOND, fromSelectDate!!.seconds)
            dateFromPicked = true
            bindingHistoryFragment.toDatePicker.visibility = View.VISIBLE
            bindingHistoryFragment.fromDatePicker.visibility = View.GONE
            if (dateFromPicked && dateToPicked)
            {
                var allTests = getAllTests(fromSelectDate , toSelectDate)
                dateFromPicked = false
                dateToPicked = false
            }
        }


        bindingHistoryFragment.toDatePicker.init(today.get(Calendar.YEAR) , today.get(Calendar.MONTH) , today.get(Calendar.DAY_OF_MONTH))
        { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            toSelectDate = Date(year, month, day)
            val calendar0: Calendar = Calendar.getInstance()
            calendar0.set(Calendar.YEAR, year)
            calendar0.set(Calendar.MONTH, month)
            calendar0.set(Calendar.DAY_OF_MONTH, day)
            toSelectDate = calendar0.time
//            calendar0.set(Calendar.HOUR_OF_DAY, toSelectDate!!.hours)
//            calendar0.set(Calendar.MINUTE, toSelectDate!!.minutes)
//            calendar0.set(Calendar.SECOND, toSelectDate!!.seconds)
            dateToPicked = true
            if (dateFromPicked && dateToPicked) {
                if (dateFromPicked && dateToPicked) {
                    var allTests = getAllTests(fromSelectDate, toSelectDate)
                    dateFromPicked = false
                    dateToPicked = false
                }
                bindingHistoryFragment.toDatePicker.visibility = View.GONE

            }

        }

//        bindingHistoryFragment.testResultsLineChart.post{
//            lineChartXPos = bindingHistoryFragment.testResultsLineChart.x.toInt()
//            lineChartYPos = bindingHistoryFragment.testResultsLineChart.y.toInt()
//            lineChartHeight = bindingHistoryFragment.testResultsLineChart.height
//            lineChartWidth = bindingHistoryFragment.testResultsLineChart.width
//
//            testResultsChart.getDescription().setEnabled(true);
//            var description : Description = Description()
//
//            description.setText("${minDateString} --- ${maxDateString}");
//            description.setTextSize(15f);
//            description.xOffset = - (mainActivity.resources.displayMetrics.widthPixels / 2 * mainActivity.resources.displayMetrics.scaledDensity)
//    //        description.setPosition((lineChartXPos + 10).toFloat() , (lineChartHeight - 100).toFloat())
//            testResultsChart.description = description
//
//        }

        calculateScreenWidth()
        calculateScreenHeight()

        bindingHistoryFragment.testResultsLineChart.layoutParams.height = SCREEN_HEIGHT / 3

//        bindingHistoryFragment.testResultDateListView.layoutParams.height = (SCREEN_HEIGHT / 3.5).toInt()

        setUniformMotionForListViews()

        getAllTests(null , null)
    }

    private fun getAllTests(fromDate : Date?  , toDate : Date?) : RealmResults<Test>
    {
        realm = Realm.getDefaultInstance()
        //get all the tests related with the specific test and patient
        var tests : RealmResults<Test>? = null
        var last10Tests : RealmResults<Test>? = null

        when (param2)
        {
            "CardioVascularDisease" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("CardioVascularDisease")
                tests = realm.where(Test::class.java).isNotNull("SSB") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//
//                }

                setDatesFromTestsToChartSubTitle(tests)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("SSB").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "DIABETES" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("DIABETES")
                tests = realm.where(Test::class.java).isNotNull("patientPAM") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientPAM").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Major Depression Index" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Major Depression Index")
                tests = realm.where(Test::class.java).isNotNull("patientMDIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientMDIQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "BPI" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Brief Pain Inventory")
                tests = realm.where(Test::class.java).isNotNull("patientBPIQ1") .equalTo("patientId" , param1).equalTo("testName" , "Brief Pain Inventory").findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientBPIQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Beck Anxiety Index" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Beck Anxiety Index")
                tests = realm.where(Test::class.java).isNotNull("patientBAIQ1") .equalTo("patientId" , param1).equalTo("testName" , "Beck Anxiety Index").findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientBPIQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "MDS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Mediterranean Diet Test")
                tests = realm.where(Test::class.java).isNotNull("patientMDSQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientMDSQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Brief Pain Inventory" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Brief Pain Inventory")
                tests = realm.where(Test::class.java).isNotNull("patientBPIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientBPIQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Beck Depression Inventory" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Beck Depression Inventory")
                tests = realm.where(Test::class.java).isNotNull("patientBDIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientBPIQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Geriatric Depression Scale" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Geriatric Depression Scale")
                tests = realm.where(Test::class.java).isNotNull("patientGDSQ6") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientGDSQ6").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Hammilton Depression" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Hammilton Depression")
                tests = realm.where(Test::class.java).isNotNull("patientHAMDQ3") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientHAMDQ3").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "STAI" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("STAI")
                tests = realm.where(Test::class.java).isNotNull("patientSTAISQ3") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientSTAISQ3").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "DASS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("DASS")
                tests = realm.where(Test::class.java).isNotNull("patientDASSQ3") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientDASSQ3").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "ZUNG" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("ZUNG")
                tests = realm.where(Test::class.java).isNotNull("patientZUNGQ3") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientZUNGQ3").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "OPQOL Test" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("OPQOL-35")
                tests = realm.where(Test::class.java).isNotNull("patientOPQOLQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientOPQOLQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "GAS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Geriatric Anxiety Scale")
                tests = realm.where(Test::class.java).isNotNull("patientGASQ6") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientGASQ6").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "SIDAS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Suicidal Ideation Attributes Scale")
                tests = realm.where(Test::class.java).isNotNull("patientSIDASQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
//                if (tests.size > 10)
//                {
//                    tests.forEachIndexed { index, test ->
//                        if ( index > (tests!!.size - 11))
//                            last10Tests!!.add(index , test)
//                    }
//                    setDatesFromTestsToChartSubTitle(last10Tests!!)
//                }
                setDatesFromTestsToChartSubTitle(tests!!)
                if (fromDate != null)
                {
                    tests = realm.where(Test::class.java).isNotNull("patientSIDASQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
        }
        bindingHistoryFragment.testResultsLineChart.layoutParams.height = SCREEN_HEIGHT / 4
        initTestResultsScopeDataChart(tests!!)
        initTestResultsListView(tests!!)
        return tests!!
    }

    private fun setDatesFromTestsToChartSubTitle(tests : RealmResults<Test>)
    {
        var maxDate = Date(1990 , 1 ,1)
        var minDate : Date = Calendar.getInstance().getTime()

        val calendar1: Calendar = Calendar.getInstance()
        calendar1.set(Calendar.YEAR , 1990)
        calendar1.set(Calendar.MONTH , 1)
        calendar1.set(Calendar.DAY_OF_MONTH , 1)
        maxDate = calendar1.time

        val calendar2: Calendar = Calendar.getInstance()
//        calendar2.set(Calendar.YEAR , maxDate.year)
//        calendar2.set(Calendar.MONTH , maxDate.month)
//        calendar2.set(Calendar.DAY_OF_MONTH , maxDate.day)
        minDate = calendar2.time

        for (test in tests)
        {
            if (test.testDate!!.before(minDate))
            {
                minDate = test.testDate!!
            }

            if (test.testDate!!.after(maxDate))
            {
                maxDate = test.testDate!!
            }
        }
        val lMindate: LocalDate = LocalDate.from(minDate.toInstant().atZone(ZoneOffset.UTC))
        val lMaxdate: LocalDate = LocalDate.from(maxDate.toInstant().atZone(ZoneOffset.UTC))
        val sMinDate = DateTimeFormatter.ISO_DATE.format(lMindate) // uuuu-MM-dd
        val sMaxDate = DateTimeFormatter.ISO_DATE.format(lMaxdate) // uuuu-MM-dd
        minDateString = sMinDate
        maxDateString = sMaxDate

   //     bindingHistoryFragment.testDates.setText(sMinDate + "   " + sMaxDate)

    }

    private fun initTestResultsScopeDataChart(allTests : RealmResults<Test>)
    {
        //get the min and max values of the scores
        var minScore : Float = 1000f
        var maxScore : Float = -100f

        var minPSScore : Float = 1000f
        var maxPSScore : Float= -100F

        var minPIScore : Float = 1000f
        var maxPIScore : Float= -100F



        val data = LineData()

        var dataSet1 : LineDataSet
        var dataSet2 : LineDataSet
        var dataSet3 : LineDataSet


        if (param2 == "BPI")
        {
            for (test in allTests)
            {
                if (minPSScore < test.patientBPITestSeverityResult!!)
                {
                    minPSScore = test.patientBPITestSeverityResult!!
                }

                if (maxPSScore > test.patientBPITestSeverityResult!!)
                {
                    maxPSScore = test.patientBPITestSeverityResult!!
                }

                if (minPIScore < test.patientBPITestInterferenceResult!!)
                {
                    minPIScore = test.patientBPITestInterferenceResult!!
                }

                if (maxPIScore > test.patientBPITestInterferenceResult!!)
                {
                    maxPIScore = test.patientBPITestInterferenceResult!!
                }
            }

            if (minPSScore > 1)
                minPSScore -= 1
            else
                minPSScore = 0f
            if (minPIScore > 1)
                minPIScore -= 1
            else
                minPIScore = 0f
            if (maxPSScore < 9)
                maxPSScore += 1
            else
                maxPSScore = 10f
            if (maxPIScore < 9)
                maxPIScore += 1
            else
                maxPIScore = 10f

            //create the data set
            dataSet1 = LineDataSet(null, Html.fromHtml("PSS").toString())
            dataSet1.axisDependency = YAxis.AxisDependency.LEFT
            dataSet1.lineWidth = 2f
            dataSet1.color = Color.MAGENTA
            dataSet1.isHighlightEnabled = false
            dataSet1.setDrawCircles(false)
            dataSet1.setDrawValues(false)
            dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet1.cubicIntensity = 0.2f
            dataSet1.clear()

            for (i in 0 until allTests.size)
            {
                var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBPITestSeverityResult!!)
                dataSet1.addEntry(entry)
            }

            dataSet2 = LineDataSet(null, Html.fromHtml("PIS").toString())
            dataSet2.axisDependency = YAxis.AxisDependency.LEFT
            dataSet2.lineWidth = 2f
            dataSet2.color = Color.BLUE
            dataSet2.isHighlightEnabled = false
            dataSet2.setDrawCircles(false)
            dataSet2.setDrawValues(false)
            dataSet2.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet2.cubicIntensity = 0.2f
            dataSet2.clear()

            for (i in 0 until allTests.size)
            {
                var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBPITestInterferenceResult!!)
                dataSet2.addEntry(entry)
            }

            data.addDataSet(dataSet1)
            data.addDataSet(dataSet2)
            data.notifyDataChanged()

        }
        else
        {
           when(param2)
           {
               "CardioVascularDisease" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.cvdTestResult!!)
                       {
                           minScore = test.cvdTestResult!!.toFloat()
                       }

                       if (maxScore < test.cvdTestResult!!)
                       {
                           maxScore = test.cvdTestResult!!.toFloat()
                       }
                   }

                   if (minScore > 10)
                       minScore -= 10
                   else
                       minScore = 0f
                   if (maxScore < 90)
                       maxScore += 10
                   else
                       maxScore = 100f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("CVD %").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.cvdTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "DIABETES" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.diabetesTestResult!!)
                       {
                           minScore = test.diabetesTestResult!!.toFloat()
                       }

                       if (maxScore < test.diabetesTestResult!!)
                       {
                           maxScore = test.diabetesTestResult!!.toFloat()
                       }
                   }

//                   if (minScore > 10)
//                       minScore -= 10
//                   else
//                       minScore = 0f
//                   if (maxScore < 90)
//                       maxScore += 10
//                   else
//                       maxScore = 100f

                   minScore = 0f
                   maxScore = 1f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("DIABETES %").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.diabetesTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }

               "Major Depression Index" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientMDITestResult!!)
                       {
                           minScore = test.patientMDITestResult!!.toFloat()
                       }

                       if (maxScore < test.patientMDITestResult!!)
                       {
                           maxScore = test.patientMDITestResult!!.toFloat()
                       }
                   }

                   if (minScore > 5)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 50)
                       maxScore += 5
                   else
                       maxScore = 55f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("MDI").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(false)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientMDITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "Beck Anxiety Index" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientBAITestResult!!)
                       {
                           minScore = test.patientBAITestResult!!.toFloat()
                       }

                       if (maxScore < test.patientBAITestResult!!)
                       {
                           maxScore = test.patientBAITestResult!!.toFloat()
                       }

                       if (minScore > 5)
                           minScore -= 5
                       else
                           minScore = 0f
                       if (maxScore < 55)
                           maxScore += 5
                       else
                           maxScore = 63f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("BAI").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBAITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "BPI" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientBPITestSeverityResult!!)
                       {
                           minScore = test.patientBPITestSeverityResult!!.toFloat()
                       }

                       if (maxScore < test.patientBPITestSeverityResult!!)
                       {
                           maxScore = test.patientBPITestSeverityResult!!.toFloat()
                       }

                       if (minScore > 2)
                           minScore -= 1
                       else
                           minScore = 0f
                       if (maxScore < 8)
                           maxScore += 1
                       else
                           maxScore = 10f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("BPI Severity").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBPITestSeverityResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

                   for (test in allTests)
                   {
                       if (minScore > test.patientBPITestSeverityResult!!)
                       {
                           minScore = test.patientBPITestSeverityResult!!.toFloat()
                       }

                       if (maxScore < test.patientBPITestSeverityResult!!)
                       {
                           maxScore = test.patientBPITestSeverityResult!!.toFloat()
                       }

                       if (minScore > 2)
                           minScore -= 1
                       else
                           minScore = 0f
                       if (maxScore < 8)
                           maxScore += 1
                       else
                           maxScore = 10f
                   }

                   //create the data set
                   dataSet2 = LineDataSet(null, Html.fromHtml("BPI Interference").toString())
                   dataSet2.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet2.lineWidth = 2f
                   dataSet2.color = Color.GREEN
                   dataSet2.isHighlightEnabled = false
                   dataSet2.setDrawCircles(false)
                   dataSet2.setDrawValues(false)
                   dataSet2.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet2.cubicIntensity = 0.2f
                   dataSet2.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBPITestInterferenceResult!!.toFloat())
                       dataSet2.addEntry(entry)
                   }

                   data.addDataSet(dataSet2)
                   data.notifyDataChanged()

               }


               "Beck Depression Inventory" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientBDITestResult!!)
                       {
                           minScore = test.patientBDITestResult!!.toFloat()
                       }

                       if (maxScore < test.patientBDITestResult!!)
                       {
                           maxScore = test.patientBDITestResult!!.toFloat()
                       }

                       if (minScore > 10)
                           minScore -= 5
                       else
                           minScore = 0f
                       if (maxScore < 100)
                           maxScore += 5
                       else
                           maxScore = 110f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("BDI").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBDITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "MDS" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientMDSTestResult!!)
                       {
                           minScore = test.patientMDSTestResult!!.toFloat()
                       }

                       if (maxScore < test.patientMDSTestResult!!)
                       {
                           maxScore = test.patientMDSTestResult!!.toFloat()
                       }
                       if (minScore > 5)
                           minScore -= 5
                       else
                           minScore = 0f
                       if (maxScore < 50)
                           maxScore += 5
                       else
                           maxScore = 55f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("MDS").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientMDSTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "Geriatric Depression Scale" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientGDSTestResult!!)
                       {
                           minScore = test.patientGDSTestResult!!.toFloat()
                       }

                       if (maxScore < test.patientGDSTestResult!!)
                       {
                           maxScore = test.patientGDSTestResult!!.toFloat()
                       }

                       if (minScore > 3)
                           minScore -= 2
                       else
                           minScore = 0f
                       if (maxScore < 12)
                           maxScore += 2
                       else
                           maxScore = 15f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("GDS").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientGDSTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }

               "GAS" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientGASTestResult!!)
                       {
                           minScore = test.patientGASTestResult!!.toFloat()
                       }

                       if (maxScore < test.patientGASTestResult!!)
                       {
                           maxScore = test.patientGASTestResult!!.toFloat()
                       }

                       if (minScore > 3)
                           minScore -= 2
                       else
                           minScore = 0f
                       if (maxScore < 12)
                           maxScore += 2
                       else
                           maxScore = 15f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("Gerietric Anxiety Scale").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientGASTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }

               "Hammilton Depression" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientHAMDTestResult!!)
                       {
                           minScore = test.patientHAMDTestResult!!.toFloat()
                       }

                       if (maxScore < test.patientHAMDTestResult!!)
                       {
                           maxScore = test.patientHAMDTestResult!!.toFloat()
                       }
                   }

                   if (minScore > 10)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 75)
                       maxScore += 10
                   else
                       maxScore = 85f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("Hammilton").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientHAMDTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "DASS" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.dassAnxietyResult!!)
                       {
                           minScore = test.dassAnxietyResult!!.toFloat()
                       }

                       if (maxScore < test.dassAnxietyResult!!)
                       {
                           maxScore = test.dassAnxietyResult!!.toFloat()
                       }
                   }

                   if (minScore > 8)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 17)
                       maxScore += 5
                   else
                       maxScore = 25f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("DASS anxiety").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.dassAnxietyResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

                   for (test in allTests)
                   {
                       if (minScore > test.dassStressResult!!)
                       {
                           minScore = test.dassStressResult!!.toFloat()
                       }

                       if (maxScore < test.dassStressResult!!)
                       {
                           maxScore = test.dassStressResult!!.toFloat()
                       }
                   }

                   if (minScore > 8)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 27)
                       maxScore += 5
                   else
                       maxScore = 35f

                   //create the data set
                   dataSet2 = LineDataSet(null, Html.fromHtml("DASS stress").toString())
                   dataSet2.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet2.lineWidth = 2f
                   dataSet2.color = Color.GREEN
                   dataSet2.isHighlightEnabled = false
                   dataSet2.setDrawCircles(false)
                   dataSet2.setDrawValues(false)
                   dataSet2.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet2.cubicIntensity = 0.2f
                   dataSet2.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.dassStressResult!!.toFloat())
                       dataSet2.addEntry(entry)
                   }

                   data.addDataSet(dataSet2)
                   data.notifyDataChanged()

                   for (test in allTests)
                   {
                       if (minScore > test.dassDepressionResult!!)
                       {
                           minScore = test.dassDepressionResult!!.toFloat()
                       }

                       if (maxScore < test.dassDepressionResult!!)
                       {
                           maxScore = test.dassDepressionResult!!.toFloat()
                       }
                   }

                   if (minScore > 8)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 22)
                       maxScore += 5
                   else
                       maxScore = 30f

                   //create the data set
                   dataSet3 = LineDataSet(null, Html.fromHtml("DASS depression").toString())
                   dataSet3.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet3.lineWidth = 2f
                   dataSet3.color = Color.YELLOW
                   dataSet3.isHighlightEnabled = false
                   dataSet3.setDrawCircles(false)
                   dataSet3.setDrawValues(false)
                   dataSet3.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet3.cubicIntensity = 0.2f
                   dataSet3.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.dassDepressionResult!!.toFloat())
                       dataSet3.addEntry(entry)
                   }

                   data.addDataSet(dataSet3)
                   data.notifyDataChanged()


               }

               "STAI" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientSTAITScore!!)
                       {
                           minScore = test.patientSTAITScore!!.toFloat()
                       }

                       if (maxScore < test.patientSTAITScore!!)
                       {
                           maxScore = test.patientSTAITScore!!.toFloat()
                       }
                   }

                   if (minScore > 10)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 65)
                       maxScore += 10
                   else
                       maxScore = 80f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("STAI Trait").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientSTAITScore!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

                   for (test in allTests)
                   {
                       if (minScore > test.patientSTAISScore!!)
                       {
                           minScore = test.patientSTAISScore!!.toFloat()
                       }

                       if (maxScore < test.patientSTAISScore!!)
                       {
                           maxScore = test.patientSTAISScore!!.toFloat()
                       }
                   }

                   if (minScore > 10)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 65)
                       maxScore += 10
                   else
                       maxScore = 80f

                   //create the data set
                   dataSet2 = LineDataSet(null, Html.fromHtml("STAI Stress").toString())
                   dataSet2.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet2.lineWidth = 2f
                   dataSet2.color = Color.GREEN
                   dataSet2.isHighlightEnabled = false
                   dataSet2.setDrawCircles(false)
                   dataSet2.setDrawValues(false)
                   dataSet2.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet2.cubicIntensity = 0.2f
                   dataSet2.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientSTAISScore!!.toFloat())
                       dataSet2.addEntry(entry)
                   }

                   data.addDataSet(dataSet2)
                   data.notifyDataChanged()

               }

               "ZUNG" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.zungTestReesult!!)
                       {
                           minScore = test.zungTestReesult!!.toFloat()
                       }

                       if (maxScore < test.zungTestReesult!!)
                       {
                           maxScore = test.zungTestReesult!!.toFloat()
                       }
                   }

                   if (minScore > 10)
                       minScore -= 5
                   else
                       minScore = 0f
                   if (maxScore < 65)
                       maxScore += 10
                   else
                       maxScore = 80f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("ZUNG").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.zungTestReesult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "SIDAS" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.sidasTestResult!!)
                       {
                           minScore = test.sidasTestResult!!.toFloat()
                       }

                       if (maxScore < test.sidasTestResult!!)
                       {
                           maxScore = test.sidasTestResult!!.toFloat()
                       }
                   }

//                   if (minScore > 10)
//                       minScore -= 10
//                   else
//                       minScore = 0f
//                   if (maxScore < 90)
//                       maxScore += 10
//                   else
//                       maxScore = 100f

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("SIDAS").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.sidasTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }


               "GAS" ->
               {
                   for (test in allTests)
                   {
                       if (minScore > test.patientGASTestResult!!)
                       {
                           minScore = test.patientGASTestResult!!.toFloat()
                       }

                       if (maxScore < test.patientGASTestResult!!)
                       {
                           maxScore = test.patientGASTestResult!!.toFloat()
                       }

                       if (minScore > 3)
                           minScore -= 2
                       else
                           minScore = 0f
                       if (maxScore < 12)
                           maxScore += 2
                       else
                           maxScore = 15f
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("Gerietric Anxiety Scale").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawCircles(false)
                   dataSet1.setDrawValues(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f
                   dataSet1.clear()

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientGASTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }
           }
        }

        testResultsChart = bindingHistoryFragment.testResultsLineChart
        // enable description text
        testResultsChart.description.isEnabled = true
        val chartDescription = Description()
        chartDescription.text = "${minDateString} ------------ ${maxDateString}"
        chartDescription.textSize = 15f
        chartDescription.yOffset =  5f
        testResultsChart.description = chartDescription

        // enable touch gestures
        testResultsChart.setTouchEnabled(true)
        // enable scaling and dragging
        testResultsChart.isDragEnabled = true
        testResultsChart.setScaleEnabled(true)
        testResultsChart.setDrawGridBackground(false)

        // if disabled, scaling can be done on x- and y-axis separately
        testResultsChart.setPinchZoom(true)

        // set an alternative background color
        testResultsChart.setBackgroundColor(Color.WHITE)

        data.setValueTextColor(Color.BLACK)


        // add empty data
        testResultsChart.data = data

        // get the legend (only possible after setting data)
        val l: Legend = testResultsChart.legend

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.textColor = Color.WHITE
        l.isEnabled = true
        l.formSize = 25f
        l.textSize = 2f
        l.textColor = resources.getColor(R.color.black)
        l.xEntrySpace = 5f // set the space between the legend entries on the x-axis
        l.yEntrySpace = 20f // set the space between the legend entries on the y-axis

        val xl: XAxis = testResultsChart.xAxis
        xl.textColor = Color.BLACK
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true
        xl.setDrawLabels(false)
//        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setDrawGridLines(false);
//        xl.setValueFormatter(IndexAxisValueFormatter(getChartValues(minScore , maxScore)))

        val leftAxis: YAxis = testResultsChart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(false)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.valueFormatter = IndexAxisValueFormatter(getChartValues(minScore , maxScore))

        leftAxis.setLabelCount(5, true);
        leftAxis.setAxisMaxValue(maxScore);
        leftAxis.setAxisMinValue(minScore);

        if (param2 == "BPI")
        {
            if (minPSScore < minPIScore)
                leftAxis.axisMinimum = 0.0f
            else
                leftAxis.axisMinimum = 0.0f

            if (maxPSScore > maxPSScore)
                leftAxis.axisMaximum = 10.0f
            else
                leftAxis.axisMaximum = 10.0f
        }
        else
        {
            leftAxis.axisMinimum = minScore
            leftAxis.axisMaximum = maxScore
        }


        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
//        leftAxis.valueFormatter = DefaultAxisValueFormatter(4)
        leftAxis.spaceMin = 50f
        leftAxis.isGranularityEnabled = true
        leftAxis.granularity = 20f


        val rightAxis: YAxis = testResultsChart.axisRight
        rightAxis.isEnabled = false

        testResultsChart.axisLeft.setDrawGridLines(false)


        testResultsChart.xAxis.setDrawGridLines(false)
        testResultsChart.setDrawBorders(false)
        testResultsChart.invalidate()
    }

    private fun getChartValues(minScore: Float, maxScore: Float): ArrayList<String>? {

        //for 5 values
        var valueStep = (maxScore - minScore) / 5
        var listOfValues = ArrayList<String>()
        listOfValues.add((minScore + valueStep).toString())
        listOfValues.add((minScore + valueStep * 2).toString())
        listOfValues.add((minScore + valueStep * 3).toString())
        listOfValues.add((minScore + valueStep * 4).toString())
        listOfValues.add((minScore + valueStep * 5).toString())

        return listOfValues

    }

    private fun setUniformMotionForListViews()
    {

        bindingHistoryFragment.testResultDateListView.setOnTouchListener(OnTouchListener { v, event ->
            if (touchSource == null) touchSource = v
            if (v === touchSource) {
                bindingHistoryFragment.testResultScoreListView.dispatchTouchEvent(event)
                if (event.action == MotionEvent.ACTION_UP) {
                    clickSource = v
                    touchSource = null
                }
            }
            false
        })

        bindingHistoryFragment.testResultScoreListView.setOnTouchListener(OnTouchListener { v, event ->
            if (touchSource == null) touchSource = v
            if (v === touchSource) {
                bindingHistoryFragment.testResultDateListView.dispatchTouchEvent(event)
                if (event.action == MotionEvent.ACTION_UP) {
                    clickSource = v
                    touchSource = null
                }
            }
            false
        })

        bindingHistoryFragment.testResultDateListView.setOnScrollListener(object :
            AbsListView.OnScrollListener {

            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (view === clickSource) bindingHistoryFragment.testResultScoreListView.setSelectionFromTop(
                    firstVisibleItem,
                    view.getChildAt(0).top + offset
                )
            }

           override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}
        })

        bindingHistoryFragment.testResultScoreListView.setOnScrollListener(object :
            AbsListView.OnScrollListener {

            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (view === clickSource) bindingHistoryFragment.testResultDateListView.setSelectionFromTop(
                    firstVisibleItem,
                    view.getChildAt(0).top + offset
                )
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}
        })
    }

    private fun initTestResultsListView(allTests : RealmResults<Test>)
    {
        var dateArrayList = ArrayList<String>()
        var scoreArrayList = ArrayList<String>()
        var sizeOfSpaceCharacter = getStringSize(" ")
        var numOfSpaces : Int = 0
        if (SCREEN_WIDTH < 1000)
            numOfSpaces = ((SCREEN_WIDTH / (2)) / sizeOfSpaceCharacter)
        if (SCREEN_WIDTH > 1000)
            numOfSpaces = ((SCREEN_WIDTH / (5)).toInt() / sizeOfSpaceCharacter)


        when (param2)
        {
            "CardioVascularDisease" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.cvdTestResult} %")
                }
            }
            "DIABETES" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("%,.2f".format(test.diabetesTestResult!!.toFloat()))
                }
            }
            "Major Depression Index" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientMDITestResult}")
                }
            }
            "Beck Anxiety Index" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add(("${test.patientBAITestResult}"))
                }
            }
            "MDS" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientMDSTestResult}")
                }
            }
            "BPI" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("PSS : ${test.patientBPITestSeverityResult} , PIS : ${test.patientBPITestInterferenceResult}")
                    bindingHistoryFragment.testResultDateListView
                }
            }
            "GAS" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientGASTestResult}")
                }
            }
            "Hammilton Depression" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientHAMDTestResult}")
                }
            }
            "DASS" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("Stress : ${test.dassStressResult} - Anxiety : ${test.dassAnxietyResult} - Depression : ${test.dassDepressionResult}")
                }
            }
            "STAI" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("Trait : ${test.patientSTAITScore} - Stress : ${test.patientSTAISScore}")
                }
            }
            "Beck Depression Inventory" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientBDITestResult}")
                }
            }
            "ZUNG" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.zungTestReesult}")
                }
            }
            "OPQOL Test" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.OPQOLTestReesult}")
                }
            }
            "Geriatric Anxiety Scale" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientGASTestResult}")
                }
            }
            "SIDAS" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.sidasTestResult}")
                }
            }
        }

        val dateAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.textvview_history_xml, dateArrayList)
        var scoreAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.textview_history_right, scoreArrayList)
        when (param2)
        {
            "BPI" ->
            {
                scoreAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.adapter_small_textview, scoreArrayList)
            }

            "STAI" ->
            {
                scoreAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.adapter_small_textview, scoreArrayList)
            }

            "DASS" ->
            {
                scoreAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.adapter_small_textview, scoreArrayList)
            }
        }

        dateAdapter.notifyDataSetChanged()
        scoreAdapter.notifyDataSetChanged()

//        var customRecViewAdapter = CustomAdapter(dateArrayList , scoreArrayList)
//        bindingHistoryFragment.testResultDateRecycleView.adapter = customRecViewAdapter


        bindingHistoryFragment.testResultDateListView.adapter = dateAdapter
        bindingHistoryFragment.testResultScoreListView.adapter = scoreAdapter

//        bindingHistoryFragment.testResultDateRecycleView.addOnItemTouchListener(
//            RecyclerItemClickListenr(mainActivity.applicationContext, bindingHistoryFragment.testResultDateRecycleView, object : RecyclerItemClickListenr.OnItemClickListener {
//
//            override fun onItemClick(view: View, position: Int) {
//                //do your work here..
//            }
//            override fun onItemLongClick(view: View?, position: Int) {
//            }
//        })
//        )

//        bindingHistoryFragment.testResultScoreListView.setOnItemClickListener { parent, view, position, id ->
//            var testDate = allTests.get(position)!!.testDate.toString()
//            showPatientTest(param1!! , testDate , param2!!)
//        }
//
        bindingHistoryFragment.testResultDateListView.setOnItemClickListener { parent, view, position, id ->
            var testID = allTests.get(position)!!.testId
            showPatientTest(param1!! , testID , param2!!)
        }
    }

    private fun setColorOnTestTitle()
    {
        var testName = param2!!
        when (testName)
        {
            "CardioVascularDisease" ->
            {
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.orange_6))
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.orange_6))

            }
            "DIABETES" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.green_5))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.green_5))

            }
            "Major Depression Index" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.purple_3))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.purple_3))


            }
            "Beck Anxiety Index" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_blue))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_blue))

            }
            "MDS" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_blue))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_blue))

            }
            "BPI" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_yellow))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_yellow))

            }
            "Geriatric Deprression Scale" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.purple_3))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.purple_3))
            }
            "Hammilton Depression" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.HAM_pink))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.HAM_pink))
            }
            "DASS" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.green_dass))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.green_dass))
            }
            "STAI" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_blue))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_blue))
            }
            "ZUNG" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.zung_purple))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.zung_purple))
            }
            "Beck Depression Inventory" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_blue))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_blue))

            }
            "OPQOL Test" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_blue))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_blue))

            }
            "Geriatric Anxiety Scale" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.purple_3))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.purple_3))
            }
            "SIDAS" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.Coral))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.Coral))
            }
        }
    }

    private fun showPatientTest(patientId : String , testDate : String , testName : String)
    {
        //fetch the test from realm database
        realm.executeTransaction {
            var bundle = Bundle()
            bundle.putString("patientId" , patientId)
            bundle.putString("testDate" , testDate)
            bundle.putString("openType" , "open_history")
            var testName = param2!!
            when (testName)
            {
                "CardioVascularDisease" ->
                {
                    checkFragment = CheckFragment()
                    checkFragment.arguments = bundle
                    mainActivity.fragmentTransaction(checkFragment)
                }
                "DIABETES" ->
                {
                    diabetesCheckFragment = DiabetesCheckFragment()
                    diabetesCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(diabetesCheckFragment)
                }
                "Major Depression Index" ->
                {
                    mdiCheckFragment = MDICheckFragment()
                    mdiCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(mdiCheckFragment)
                }
                "Beck Anxiety Index" ->
                {
                    baiCheckFragment = BAICheckFragment()
                    baiCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(baiCheckFragment)
                }
                "MDS" ->
                {
                    medDietTestFragment = medDietTestFragment()
                    medDietTestFragment.arguments = bundle
                    mainActivity.fragmentTransaction(medDietTestFragment)
                }
                "BPI" ->
                {
                    bpiCheckFragment = BPICheckFragment()
                    bpiCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(bpiCheckFragment)
                }
                "Geriatric Depression Scale" ->
                {
                    gdsCheckFragment = GDSCheckFragment()
                    gdsCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(gdsCheckFragment)
                }
                "Hammilton Depression" ->
                {
                    hammiltonFragment = HamiltonDepressionFragment()
                    hammiltonFragment.arguments = bundle
                    mainActivity.fragmentTransaction(hammiltonFragment)
                }
                "Beck Depression Inventory" ->
                {
                    beckDepressionInventoryFragment = BeckDepressionInventoryFragment()
                    beckDepressionInventoryFragment.arguments = bundle
                    mainActivity.fragmentTransaction(beckDepressionInventoryFragment)
                }
                "DASS" ->
                {
                    dassCheckFragment = DASSCheckFragment()
                    dassCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(dassCheckFragment)
                }
                "STAI" ->
                {
                    staiCheckFragment = STAICheckFragment()
                    staiCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(staiCheckFragment)
                }
                "ZUNG" ->
                {
                    zungFragment = CheckZUNGFragment()
                    zungFragment.arguments = bundle
                    mainActivity.fragmentTransaction(zungFragment)
                }
                "OPQOL Test" ->
                {
                    opqolFragment = OPQOLCheckFragment()
                    opqolFragment.arguments = bundle
                    mainActivity.fragmentTransaction(opqolFragment)
                }
                "GAS" ->
                {
                    gasCheckFragment = GASCheckFragment1()
                    gasCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(gasCheckFragment)
                }
                "SIDAS" ->
                {
                    sidasCheckFragment = SIDASCheckFragment()
                    sidasCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(sidasCheckFragment)
                }
            }

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun getStringSize(string : String) : Int
    {
        val Paint = Paint()
        var textWidth = Paint.measureText(string)
        return textWidth.toInt()
    }

    private fun calculateScreenWidth()    {
        val displayMetrics = DisplayMetrics()
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        SCREEN_WIDTH = displayMetrics.widthPixels
    }

    private fun calculateScreenHeight()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        SCREEN_HEIGHT = displayMetrics.heightPixels
    }
}