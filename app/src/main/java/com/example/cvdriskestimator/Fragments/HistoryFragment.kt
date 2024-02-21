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
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.BAITest
import com.example.cvdriskestimator.RealmDB.BDITest
import com.example.cvdriskestimator.RealmDB.BPITest
import com.example.cvdriskestimator.RealmDB.CVDTest
import com.example.cvdriskestimator.RealmDB.DASSTest
import com.example.cvdriskestimator.RealmDB.DiabetesTest
import com.example.cvdriskestimator.RealmDB.GDSTest
import com.example.cvdriskestimator.RealmDB.HAMTest
import com.example.cvdriskestimator.RealmDB.MDITest
import com.example.cvdriskestimator.RealmDB.MDSTest
import com.example.cvdriskestimator.RealmDB.STAITest
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.RealmDB.ZUNGTest
import com.example.cvdriskestimator.customClasses.PopUpMenu
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
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
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
    private var loginDoctorFragment =  LoginFragment.newInstance()
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
        val popupMenu = PopUpMenu(bindingHistoryFragment.includePopupMenu.termsRelLayout , mainActivity, this,  loginDoctorFragment, registerDoctorFragment , null , leaderBoardFragment)

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

    private fun getAllTests(fromDate : Date?  , toDate : Date?) : RealmResults<Any>
    {
        realm = Realm.getDefaultInstance()
        //get all the tests related with the specific test and patient
        var tests : RealmResults<Any>? = null
        var last10Tests : RealmResults<Test>? = null

        when (param2)
        {
            "CardioVascularDisease" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("CardioVascularDisease")
                tests = realm.where(CVDTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(CVDTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "DIABETES" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("DIABETES")
                tests = realm.where(DiabetesTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(DiabetesTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Major Depression Index" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Major Depression Index")
                tests = realm.where(MDITest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(MDITest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "BPI" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Brief Pain Inventory")
                tests = realm.where(BPITest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(BPITest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Beck Anxiety Index" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Beck Anxiety Index")
                tests = realm.where(BAITest::class.java).isNotNull("patientBAIQ1") .equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(BAITest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "MDS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Mediterranean Diet Test")
                tests = realm.where(MDSTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(MDSTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Brief Pain Inventory" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Brief Pain Inventory")
                tests = realm.where(BPITest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(BPITest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Beck Depression Inventory" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Beck Depression Inventory")
                tests = realm.where(BDITest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(BDITest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Geriatric Depression Scale" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Geriatric Depression Scale")
                tests = realm.where(GDSTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(GDSTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "Hammilton Depression" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("Hammilton Depression")
                tests = realm.where(HAMTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(HAMTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "STAI" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("STAI")
                tests = realm.where(STAITest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(STAITest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "DASS" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("DASS")
                tests = realm.where(DASSTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(DASSTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
            "ZUNG" ->
            {
                bindingHistoryFragment.testNameTxtV.setText("ZUNG")
                tests = realm.where(ZUNGTest::class.java).equalTo("patientId" , param1).findAll() as RealmResults<Any>
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
                    tests = realm.where(ZUNGTest::class.java).between("testDate" , fromDate!! , toDate!!).equalTo("patientId" , param1).findAll() as RealmResults<Any>
                    setDatesFromTestsToChartSubTitle(tests)
                }
            }
        }
        bindingHistoryFragment.testResultsLineChart.layoutParams.height = SCREEN_HEIGHT / 4
        initTestResultsScopeDataChart(tests!!)
        initTestResultsListView(tests!!)
        return tests!!
    }

    private fun setDatesFromTestsToChartSubTitle(tests : RealmResults<Any>)
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

        var allTests : Any


        //identify the realmList class by using kotlin reflection
        if (isInstanceOfRealmResults(tests, BAITest::class.java))
        {
            allTests =  tests as RealmResults<BAITest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, BDITest::class.java))
        {
            allTests =  tests as RealmResults<BDITest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, BPITest::class.java))
        {
            allTests =  tests as RealmResults<BDITest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, CVDTest::class.java))
        {
            allTests =  tests as RealmResults<CVDTest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, DASSTest::class.java))
        {
            allTests =  tests as RealmResults<DASSTest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, DiabetesTest::class.java))
        {
            allTests =  tests as RealmResults<DiabetesTest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, GDSTest::class.java))
        {
            allTests =  tests as RealmResults<GDSTest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, HAMTest::class.java))
        {
            allTests =  tests as RealmResults<HAMTest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, MDITest::class.java))
        {
            allTests =  tests as RealmResults<MDITest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, MDSTest::class.java))
        {
            allTests =  tests as RealmResults<MDSTest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, STAITest::class.java))
        {
            allTests =  tests as RealmResults<STAITest>

            for (test in allTests)
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
        }

        if (isInstanceOfRealmResults(tests, ZUNGTest::class.java))
        {
            allTests =  tests as RealmResults<ZUNGTest>

            for (test in allTests)
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
        }

        val lMindate: LocalDate = LocalDate.from(minDate.toInstant().atZone(ZoneOffset.UTC))
        val lMaxdate: LocalDate = LocalDate.from(maxDate.toInstant().atZone(ZoneOffset.UTC))
        val sMinDate = DateTimeFormatter.ISO_DATE.format(lMindate) // uuuu-MM-dd
        val sMaxDate = DateTimeFormatter.ISO_DATE.format(lMaxdate) // uuuu-MM-dd
        minDateString = sMinDate
        maxDateString = sMaxDate

   //     bindingHistoryFragment.testDates.setText(sMinDate + "   " + sMaxDate)

    }

    fun isInstanceOfRealmResults(`object`: RealmResults<*>, clazz: Class<*>): Boolean {
        val type: Type = `object`.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val parameterizedType = type as ParameterizedType
            val typeArguments: Array<Type> = parameterizedType.actualTypeArguments
            if (typeArguments.size > 0) {
                val typeArgument: Type = typeArguments[0]
                if (typeArgument is Class<*>) {
                    val typeClass = typeArgument as Class<*>
                    return clazz.isAssignableFrom(typeClass)
                }
            }
        }
        return false
    }


    fun <T> convertToRealmResults(anyObject: RealmResults<Any>?, clazz: Class<T>): RealmResults<T>? {
        if (anyObject is RealmResults<*>) {
            if (clazz.isAssignableFrom(anyObject.firstOrNull()?.javaClass)) {
                @Suppress("UNCHECKED_CAST")
                return anyObject as RealmResults<T>
            }
        }
        return null // Or handle the case where `anyObject` is not a RealmResults object or the types don't match
    }

    private fun initTestResultsScopeDataChart(allTests : RealmResults<Any>)
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

        var castedAllBAITest : Any =""
        var castedAllBDITest : Any = ""
        var castedAllBPITest : Any = ""
        var castedAllDASSTest : Any = ""
        var castedAllDiabetesTest : Any = ""
        var castedAllCVDTest : Any = ""
        var castedAllGDSTest : Any = ""
        var castedAllHAMTest : Any = ""
        var castedAllMDITest : Any = ""
        var castedAllMDSTest : Any = ""
        var castedAllSTAITest : Any = ""
        var castedAllZUNGTest : Any = ""




        if (isInstanceOfRealmResults(allTests , BAITest::class.java))
        {
            castedAllBAITest = convertToRealmResults(allTests , BAITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , BDITest::class.java))
        {
            castedAllBDITest = convertToRealmResults(allTests , BDITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , BPITest::class.java))
        {
            castedAllBPITest = convertToRealmResults(allTests , BPITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , CVDTest::class.java))
        {
            castedAllCVDTest = convertToRealmResults(allTests , CVDTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , DASSTest::class.java))
        {
            castedAllDASSTest = convertToRealmResults(allTests , DASSTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , DiabetesTest::class.java))
        {
            castedAllDiabetesTest = convertToRealmResults(allTests , DiabetesTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , GDSTest::class.java))
        {
            castedAllGDSTest = convertToRealmResults(allTests , GDSTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , HAMTest::class.java))
        {
            castedAllHAMTest = convertToRealmResults(allTests , HAMTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , MDITest::class.java))
        {
            castedAllMDITest = convertToRealmResults(allTests , MDITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , MDSTest::class.java))
        {
            castedAllMDSTest = convertToRealmResults(allTests , MDSTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , STAITest::class.java))
        {
            castedAllSTAITest = convertToRealmResults(allTests , STAITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , ZUNGTest::class.java))
        {
            castedAllZUNGTest = convertToRealmResults(allTests , ZUNGTest::class.java)!!
        }

        if (param2 == "BPI")
        {
            for (test in castedAllBPITest as RealmResults<BPITest>)
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
                var entry = Entry(i.toFloat() , castedAllBPITest.get(i)!!.patientBPITestSeverityResult!!)
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
                var entry = Entry(i.toFloat() , castedAllBPITest.get(i)!!.patientBPITestInterferenceResult!!)
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
                   for (test in castedAllCVDTest as RealmResults<CVDTest>)
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
                       var entry = Entry(i.toFloat() , castedAllCVDTest.get(i)!!.cvdTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "DIABETES" ->
               {
                   for (test in castedAllDiabetesTest as RealmResults<DiabetesTest>)
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

                   for (i in 0 until castedAllDiabetesTest.size)
                   {
                       var entry = Entry(i.toFloat() , castedAllDiabetesTest.get(i)!!.diabetesTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }

               "Major Depression Index" ->
               {
                   for (test in castedAllMDITest as RealmResults<MDITest>)
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
                       var entry = Entry(i.toFloat() , castedAllMDITest.get(i)!!.patientMDITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "Beck Anxiety Index" ->
               {
                   for (test in castedAllBAITest as RealmResults<BAITest>)
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
                       var entry = Entry(i.toFloat() , castedAllBAITest.get(i)!!.patientBAITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "BPI" ->
               {
                   for (test in castedAllBDITest as RealmResults<BPITest>)
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

                   for (i in 0 until castedAllBDITest.size)
                   {
                       var entry = Entry(i.toFloat() , castedAllBDITest.get(i)!!.patientBPITestSeverityResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

                   for (test in castedAllBPITest as RealmResults<BPITest>)
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
                       var entry = Entry(i.toFloat() , castedAllBPITest.get(i)!!.patientBPITestInterferenceResult!!.toFloat())
                       dataSet2.addEntry(entry)
                   }

                   data.addDataSet(dataSet2)
                   data.notifyDataChanged()

               }


               "Beck Depression Inventory" ->
               {
                   for (test in castedAllBDITest as RealmResults<BDITest>)
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
                       var entry = Entry(i.toFloat() , castedAllBDITest.get(i)!!.patientBDITestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "MDS" ->
               {
                   for (test in castedAllMDSTest as RealmResults<MDSTest>)
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
                       var entry = Entry(i.toFloat() , castedAllMDSTest.get(i)!!.patientMDSTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "Geriatric Depression Scale" ->
               {
                   for (test in castedAllGDSTest as RealmResults<GDSTest>)
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
                       var entry = Entry(i.toFloat() , castedAllGDSTest.get(i)!!.patientGDSTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()
               }

               "Hammilton Depression" ->
               {
                   for (test in castedAllHAMTest as RealmResults<HAMTest>)
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
                       var entry = Entry(i.toFloat() , castedAllHAMTest.get(i)!!.patientHAMDTestResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

               }

               "DASS" ->
               {
                   for (test in castedAllDASSTest as RealmResults<DASSTest>)
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
                       var entry = Entry(i.toFloat() , castedAllDASSTest.get(i)!!.dassAnxietyResult!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

                   for (test in castedAllDASSTest)
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
                       var entry = Entry(i.toFloat() , castedAllDASSTest.get(i)!!.dassStressResult!!.toFloat())
                       dataSet2.addEntry(entry)
                   }

                   data.addDataSet(dataSet2)
                   data.notifyDataChanged()

                   for (test in castedAllDASSTest)
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
                       var entry = Entry(i.toFloat() , castedAllDASSTest.get(i)!!.dassDepressionResult!!.toFloat())
                       dataSet3.addEntry(entry)
                   }

                   data.addDataSet(dataSet3)
                   data.notifyDataChanged()


               }

               "STAI" ->
               {
                   for (test in castedAllSTAITest as RealmResults<STAITest>)
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

                   for (i in 0 until castedAllSTAITest.size)
                   {
                       var entry = Entry(i.toFloat() , castedAllSTAITest.get(i)!!.patientSTAITScore!!.toFloat())
                       dataSet1.addEntry(entry)
                   }

                   data.addDataSet(dataSet1)
                   data.notifyDataChanged()

                   for (test in castedAllSTAITest)
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
                       var entry = Entry(i.toFloat() , castedAllSTAITest.get(i)!!.patientSTAISScore!!.toFloat())
                       dataSet2.addEntry(entry)
                   }

                   data.addDataSet(dataSet2)
                   data.notifyDataChanged()

               }

               "ZUNG" ->
               {
                   for (test in castedAllZUNGTest as RealmResults<ZUNGTest>)
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
                       var entry = Entry(i.toFloat() , castedAllZUNGTest.get(i)!!.zungTestReesult!!.toFloat())
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

    private fun initTestResultsListView(allTests : RealmResults<Any>)
    {
        var dateArrayList = ArrayList<String>()
        var scoreArrayList = ArrayList<String>()
        var sizeOfSpaceCharacter = getStringSize(" ")
        var numOfSpaces : Int = 0
        if (SCREEN_WIDTH < 1000)
            numOfSpaces = ((SCREEN_WIDTH / (2)) / sizeOfSpaceCharacter)
        if (SCREEN_WIDTH > 1000)
            numOfSpaces = ((SCREEN_WIDTH / (5)).toInt() / sizeOfSpaceCharacter)

        var castedAllBAITest : Any =""
        var castedAllBDITest : Any = ""
        var castedAllBPITest : Any = ""
        var castedAllDASSTest : Any = ""
        var castedAllDiabetesTest : Any = ""
        var castedAllCVDTest : Any = ""
        var castedAllGDSTest : Any = ""
        var castedAllHAMTest : Any = ""
        var castedAllMDITest : Any = ""
        var castedAllMDSTest : Any = ""
        var castedAllSTAITest : Any = ""
        var castedAllZUNGTest : Any = ""




        if (isInstanceOfRealmResults(allTests , BAITest::class.java))
        {
            castedAllBAITest = convertToRealmResults(allTests , BAITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , BDITest::class.java))
        {
            castedAllBDITest = convertToRealmResults(allTests , BDITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , BPITest::class.java))
        {
            castedAllBPITest = convertToRealmResults(allTests , BPITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , CVDTest::class.java))
        {
            castedAllCVDTest = convertToRealmResults(allTests , CVDTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , DASSTest::class.java))
        {
            castedAllDASSTest = convertToRealmResults(allTests , DASSTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , DiabetesTest::class.java))
        {
            castedAllDiabetesTest = convertToRealmResults(allTests , DiabetesTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , GDSTest::class.java))
        {
            castedAllGDSTest = convertToRealmResults(allTests , GDSTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , HAMTest::class.java))
        {
            castedAllHAMTest = convertToRealmResults(allTests , HAMTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , MDITest::class.java))
        {
            castedAllMDITest = convertToRealmResults(allTests , MDITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , MDSTest::class.java))
        {
            castedAllMDSTest = convertToRealmResults(allTests , MDSTest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , STAITest::class.java))
        {
            castedAllSTAITest = convertToRealmResults(allTests , STAITest::class.java)!!
        }

        if (isInstanceOfRealmResults(allTests , ZUNGTest::class.java))
        {
            castedAllZUNGTest = convertToRealmResults(allTests , ZUNGTest::class.java)!!
        }


        when (param2)
        {
            "CardioVascularDisease" ->
            {
                for (test in castedAllCVDTest as RealmResults<CVDTest>)
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
                for (test in castedAllDiabetesTest as RealmResults<DiabetesTest>)
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
                for (test in castedAllMDITest as RealmResults<MDITest>)
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
                for (test in castedAllBAITest as RealmResults<BAITest>)
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
                for (test in castedAllMDSTest as RealmResults<MDSTest>)
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
                for (test in castedAllBPITest as RealmResults<BPITest>)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("PSS : ${test.patientBPITestSeverityResult} , PIS : ${test.patientBPITestInterferenceResult}")
                    bindingHistoryFragment.testResultDateListView
                }
            }
            "Geriatric Depression Scale" ->
            {
                for (test in castedAllGDSTest as RealmResults<GDSTest>)
                {
                    val format = SimpleDateFormat("yyy MM dd")
                    var emptyString = ""
                    for (i in 0..numOfSpaces)
                    {
                        emptyString += " "
                    }
                    dateArrayList.add("${format.format(test.testDate)}")
                    scoreArrayList.add("${test.patientGDSTestResult}")
                }
            }
            "Hammilton Depression" ->
            {
                for (test in castedAllHAMTest as RealmResults<HAMTest>)
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
                for (test in castedAllDASSTest as RealmResults<DASSTest>)
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
                for (test in castedAllSTAITest as RealmResults<STAITest>)
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
                for (test in castedAllBDITest as RealmResults<BDITest>)
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
                for (test in castedAllZUNGTest as RealmResults<ZUNGTest>)
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

            if (isInstanceOfRealmResults(allTests , BAITest::class.java))
            {
                var baiTests = castedAllBAITest as RealmResults<BAITest>
                var testID = baiTests.get(position)!!.testId
                showPatientTest(param1!! , testID , param2!!)
            }

            if (isInstanceOfRealmResults(allTests , BDITest::class.java))
            {
                var bdiTests = castedAllBDITest as RealmResults<BDITest>
                var testID = bdiTests.get(position)!!.testId
                showPatientTest(param1!! , testID , param2!!)
            }

            if (isInstanceOfRealmResults(allTests , BPITest::class.java))
            {
                castedAllBPITest = convertToRealmResults(allTests , BPITest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , CVDTest::class.java))
            {
                castedAllCVDTest = convertToRealmResults(allTests , CVDTest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , DASSTest::class.java))
            {
                castedAllDASSTest = convertToRealmResults(allTests , DASSTest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , DiabetesTest::class.java))
            {
                castedAllDiabetesTest = convertToRealmResults(allTests , DiabetesTest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , GDSTest::class.java))
            {
                castedAllGDSTest = convertToRealmResults(allTests , GDSTest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , HAMTest::class.java))
            {
                castedAllHAMTest = convertToRealmResults(allTests , HAMTest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , MDITest::class.java))
            {
                castedAllMDITest = convertToRealmResults(allTests , MDITest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , MDSTest::class.java))
            {
                castedAllMDSTest = convertToRealmResults(allTests , MDSTest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , STAITest::class.java))
            {
                castedAllSTAITest = convertToRealmResults(allTests , STAITest::class.java)!!
            }

            if (isInstanceOfRealmResults(allTests , ZUNGTest::class.java))
            {
                castedAllZUNGTest = convertToRealmResults(allTests , ZUNGTest::class.java)!!
            }


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