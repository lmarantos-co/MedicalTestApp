package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
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
import io.realm.RealmResults

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

    private var SCREEN_HEIGHT : Int = 0


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
        calculateScreenHeight()
        getAllTests()
    }

    private fun getAllTests()
    {
        realm = Realm.getDefaultInstance()
        //get all the tests related with the specific test and patient
        var tests : RealmResults<Test>? = null
        when (param2)
        {
            "CardioVascularDisease" ->
            {
                tests = realm.where(Test::class.java).isNotNull("SSB") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "DIABETES" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientPAM") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Major Depression Index" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientMDIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Beck Anxiety Index" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientBAIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Mediterranean Diet Test" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientMDSQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Brief Pain Inventory" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientBPIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Geriatric Deprression Scale" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientGDSQ6") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
        }
        bindingHistoryFragment.testResultsLineChart.layoutParams.height = SCREEN_HEIGHT / 4
        initTestResultsScopeDataChart(tests!!)
        initTestResultsListView(tests!!)
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

            //create the data set
            dataSet1 = LineDataSet(null, Html.fromHtml("PSS").toString())
            dataSet1.axisDependency = YAxis.AxisDependency.LEFT
            dataSet1.lineWidth = 2f
            dataSet1.color = Color.MAGENTA
            dataSet1.isHighlightEnabled = false
            dataSet1.setDrawValues(true)
            dataSet1.setDrawCircles(false)
            dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet1.cubicIntensity = 0.2f

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
            dataSet2.setDrawValues(true)
            dataSet2.setDrawCircles(false)
            dataSet2.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet2.cubicIntensity = 0.2f

            for (i in 0 until allTests.size)
            {
                var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBPITestInterferenceResult!!)
                dataSet1.addEntry(entry)
            }

            data.addDataSet(dataSet1)
            data.addDataSet(dataSet2)

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
                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("CVD %").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(true)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.cvdTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)

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

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("DIABETES %").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(true)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.diabetesTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)

               }

               "Major Derpression Index" ->
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
                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("MDI").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(true)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientMDITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)

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
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("BAI").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(true)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientBAITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)

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
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("MDS").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(true)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientMDSTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)

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
                   }

                   //create the data set
                   dataSet1 = LineDataSet(null, Html.fromHtml("GDS").toString())
                   dataSet1.axisDependency = YAxis.AxisDependency.LEFT
                   dataSet1.lineWidth = 2f
                   dataSet1.color = Color.BLUE
                   dataSet1.isHighlightEnabled = false
                   dataSet1.setDrawValues(true)
                   dataSet1.setDrawCircles(false)
                   dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                   dataSet1.cubicIntensity = 0.2f

                   for (i in 0 until allTests.size)
                   {
                       var entry = Entry(i.toFloat() , allTests.get(i)!!.patientGDSTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
               }
           }
        }

        testResultsChart = bindingHistoryFragment.testResultsLineChart
        // enable description text
        testResultsChart.description.isEnabled = true
        val chartDescription = Description()
        chartDescription.text = "${param2} Test Results (OVER TIME)"
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

        val rightAxis: YAxis = testResultsChart.axisRight
        rightAxis.isEnabled = false

        testResultsChart.axisLeft.setDrawGridLines(false)
        testResultsChart.xAxis.setDrawGridLines(false)
        testResultsChart.setDrawBorders(false)
    }

    private fun initTestResultsListView(allTests : RealmResults<Test>)
    {
        var dateArrayList = ArrayList<String>()
        for (test in allTests)
        {
            dateArrayList.add("${test.testDate}")
        }

        var scoreArrayList = ArrayList<String>()
        when (param2)
        {
            "CardioVascularDisease" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("${test.cvdTestResult} %")
                }
            }
            "DIABETES" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("${test.diabetesTestResult} %")
                }
            }
            "Major Depression Index" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("${test.patientMDITestResult}")
                }
            }
            "Beck Anxiety Index" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("${test.patientBAITestResult}")
                }
            }
            "Mediterranean Diet Test" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("${test.patientMDSTestResult}")
                }
            }
            "Brief Pain Inventory" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("PSS : ${test.patientBPITestSeverityResult} , PIS : ${test.patientBPITestInterferenceResult}")
                }
            }
            "Geriatric Deprression Scale" ->
            {
                for (test in allTests)
                {
                    scoreArrayList.add("${test.patientGDSTestResult}")
                }
            }
        }

        val dateAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.textcenter, dateArrayList)
        val scoreAdapter = ArrayAdapter(mainActivity.applicationContext, R.layout.textcenter, scoreArrayList)

        bindingHistoryFragment.testResultDateListView.adapter = dateAdapter
        bindingHistoryFragment.testResultScoreListView.adapter = scoreAdapter

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun calculateScreenHeight()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        SCREEN_HEIGHT = displayMetrics.heightPixels
    }
}