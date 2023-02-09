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
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.CustomClasses.CustomAdapter
import com.example.cvdriskestimator.CustomClasses.RecyclerItemClickListenr
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
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter
import io.realm.Realm
import io.realm.RealmList
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
    private lateinit var mdiCheckFragment: MDICheckFragment
    private lateinit var medDietTestFragment: medDietTestFragment
    private lateinit var baiCheckFragment: BAICheckFragment
    private lateinit var bpiCheckFragment: BPICheckFragment
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

        param1 = this.arguments!!.getString("patientId")!!
        param2 = this.arguments!!.getString("testName")

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

        bindingHistoryFragment.testResultDateListView.layoutParams.height = (SCREEN_HEIGHT / 3.5).toInt()

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
            "Beck Anxiety Index" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Beck Anxiety Index")
                tests = realm.where(Test::class.java).isNotNull("patientBAIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
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
                    tests = realm.where(Test::class.java).isNotNull("patientBAIQ1").between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Mediterranean Diet Test" ->
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
            "STAI" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("STAI")
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
            "DASS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("DASS")
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
            "ZUNG" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("ZUNG")
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
        calendar1.set(Calendar.YEAR , minDate.year)
        calendar1.set(Calendar.MONTH , minDate.month)
        calendar1.set(Calendar.DAY_OF_MONTH , minDate.day)
        minDate = calendar1.time

        val calendar2: Calendar = Calendar.getInstance()
        calendar2.set(Calendar.YEAR , maxDate.year)
        calendar2.set(Calendar.MONTH , maxDate.month)
        calendar2.set(Calendar.DAY_OF_MONTH , maxDate.day)
        maxDate = calendar2.time

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

                   if (minScore > 10)
                       minScore -= 10
                   else
                       minScore = 0f
                   if (maxScore < 90)
                       maxScore += 10
                   else
                       maxScore = 100f

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

               "Mediterranean Diet Test" ->
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
           }
        }

        testResultsChart = bindingHistoryFragment.testResultsLineChart
        // enable description text
        testResultsChart.description.isEnabled = true
        val chartDescription = Description()
        chartDescription.text = "${minDateString} ------------ ${maxDateString}"
        chartDescription.textSize = 15f
        chartDescription.yOffset = - 5f
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
        xl.valueFormatter = DefaultAxisValueFormatter(4)
        xl.setDrawGridLines(true)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true
        xl.setDrawLabels(false)

        val leftAxis: YAxis = testResultsChart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(false)

        if (param2 == "BPI")
        {
            if (minPSScore < minPIScore)
                leftAxis.axisMinimum = minPSScore
            else
                leftAxis.axisMinimum = minPIScore

            if (maxPSScore > maxPSScore)
                leftAxis.axisMaximum = maxPSScore
            else
                leftAxis.axisMaximum = maxPIScore
        }
        else
        {
            leftAxis.axisMaximum = maxScore
            leftAxis.axisMinimum = minScore
        }

        leftAxis.setDrawGridLines(true)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.valueFormatter = DefaultAxisValueFormatter(4)
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
                    dateArrayList.add("${format.format(test.testDate)}${emptyString}${test.cvdTestResult} %")
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
                    dateArrayList.add("${format.format(test.testDate)}${emptyString}${test.diabetesTestResult} %")
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
                    dateArrayList.add("${format.format(test.testDate)}${emptyString}${test.patientMDITestResult}")
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
                    dateArrayList.add("${format.format(test.testDate)}${emptyString}${test.patientBAITestResult}")
                }
            }
            "Mediterranean Diet Test" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}${emptyString}${test.patientMDSTestResult}")
                }
            }
            "Brief Pain Inventory" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    dateArrayList.add("${format.format(test.testDate)} - PSS : ${test.patientBPITestSeverityResult} , PIS : ${test.patientBPITestInterferenceResult}")
                }
            }
            "Geriatric Deprression Scale" ->
            {
                for (test in allTests)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}${emptyString}${test.patientGDSTestResult}")
                }
            }
        }

        val dateAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.textcenter, dateArrayList)
        val scoreAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.textcenter, scoreArrayList)

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
            var testDate = allTests.get(position)!!.testDate.toString()
            showPatientTest(param1!! , testDate , param2!!)
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
            "Mediterranean Diet Test" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_blue))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_blue))

            }
            "Brief Pain Inventory" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.light_yellow))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.light_yellow))

            }
            "Geriatric Deprression Scale" ->
            {
                bindingHistoryFragment.testReulstLinLayout.background.setTint(mainActivity.getColor(R.color.purple_3))
                bindingHistoryFragment.testNameTxtV.background.setTint(mainActivity.getColor(R.color.purple_3))
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
                "Mediterranean Diet Test" ->
                {
                    medDietTestFragment = medDietTestFragment()
                    medDietTestFragment.arguments = bundle
                    mainActivity.fragmentTransaction(medDietTestFragment)
                }
                "Brief Pain Inventory" ->
                {
                    bpiCheckFragment = BPICheckFragment()
                    bpiCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(medDietTestFragment)
                }
                "Geriatric Deprression Scale" ->
                {
                    gdsCheckFragment = GDSCheckFragment()
                    gdsCheckFragment.arguments = bundle
                    mainActivity.fragmentTransaction(medDietTestFragment)

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