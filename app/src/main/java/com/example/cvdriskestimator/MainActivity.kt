package com.example.cvdriskestimator

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.CustomClasses.CustomTestListAdapter
import com.example.cvdriskestimator.Fragments.*
import com.example.cvdriskestimator.RealmDB.Doctor
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDB
import com.example.cvdriskestimator.RealmDB.Test
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import io.realm.Realm
import io.realm.RealmList
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var constraintLayout : ConstraintLayout
    private lateinit var introScreenConLayout : ConstraintLayout
    private lateinit var allCustomersTxtV : TextView
    private lateinit var newCustomerTxtV : TextView
    private lateinit var introSearchCustomersConLayout : ConstraintLayout
    private lateinit var customersListView : ListView
    private lateinit var customerSearchView : SearchView
    private lateinit var customerRadioGroup : RadioGroup
    private lateinit var nameArrayAdapter : ArrayAdapter<String>
    private lateinit var lastNameArrayAdapter : ArrayAdapter<String>
    private var selectCustomerType : Int = 0
    private lateinit var realmDB: RealmDB
    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var loginDoctorFragment: LoginDoctorFragment
    private lateinit var registerDoctorFragment: RegisterDoctorFragment
    private lateinit var loginDoctorButton : TextView
    private lateinit var registerDoctorButton : TextView
    private lateinit var checkFragment: CheckFragment
    private lateinit var checkDiabetesFragment : DiabetesCheckFragment
    private lateinit var mdiCheckFragment: MDICheckFragment
    private lateinit var baiCheckFragment: BAICheckFragment
    private lateinit var mdsCheckFragment: medDietTestFragment
    private lateinit var bpiCheckFragment: BPICheckFragment
    private lateinit var gdsCheckFragment: GDSCheckFragment
    private lateinit var pdqCheckFragment: PDQCheckFirstCategoryFragment
    private lateinit var pdqResultFragment : ResultExtraFragment
    private lateinit var bDIFragment: BeckDepressionInventoryFragment
    private lateinit var hamDFragment : HamiltonDepressionFragment
    private lateinit var staiCheckFragment: STAICheckFragment
    private lateinit var dassCheckFragment : DASSCheckFragment
    private lateinit var zungCheckFFragment : CheckZUNGFragment
    private lateinit var timelineFragment: ResultTimelineFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment
    private lateinit var popupMenu: PopupMenu
    private lateinit var allPatientResultsPopUp : ConstraintLayout
    private lateinit var allPatientTestNamesAdapter : ArrayAdapter<String>
    private lateinit var allPatientTestDatesAdapter : ArrayAdapter<String>
    private lateinit var allPatientResultsLinLayout : LinearLayout
    private lateinit var allPatientResultsConLayout : ConstraintLayout
    private lateinit var mainConLayout : ConstraintLayout
    private lateinit var mainScrollView : NestedScrollView
    private lateinit var scrollViewConstrainSet : ConstraintSet
    private lateinit var allPatientsTestNameListView : ListView
    private lateinit var allPatientTestDateLisView : ListView
    private lateinit var patietTestListFragment: PatietTestListFragment
    private lateinit var allPatientTestRecyclerView: RecyclerView
    private lateinit var allPatienttestNameTextView : TextView
    private lateinit var allPatienttestDateTextView : TextView
    private var CVDTestName : String = ""
    private var DiabetesTestName : String = ""
    private var MDITestName : String = ""
    private var BAITestName : String = ""
    private var BPITestName : String = ""
    private var MDTTestName : String = ""
    private var GDSTestName : String = ""
    private var BDITestName : String = ""
    private var STAITestName : String = ""
    private var DASSTestName : String = ""
    private var HammiltonTestName : String = ""
    private var ZungTestName : String = ""
    private var CVDTestDate : String = ""
    private var DiabetesTestDate : String = ""
    private var MDITestDate : String = ""
    private var BAITestDate : String = ""
    private var BPITestDate : String = ""
    private var MDTTestDate : String = ""
    private var GDSTestDate : String = ""
    private var BDITestDate : String = ""
    private var STAITestDate : String = ""
    private var DASSTestDate : String = ""
    private var HammiltonTestDate : String = ""
    private var ZungTestDate : String = ""
    private lateinit var patienTestListOkBtn : Button
    private lateinit var includeTestOptionsPopup : ConstraintLayout
    private var showPatientLasttest : Boolean = false
    private lateinit var addNewTxtV : TextView
    private lateinit var updateLastTxtV : TextView
    private lateinit var historyTxtV : TextView
    private var testName : String = ""
    private lateinit var MTETitleForm : RelativeLayout
    private  var mteTitleFormHeight : Int = 0
    private lateinit var userIconImg : ImageView
    private lateinit var companyLogo : ImageView
    private lateinit var appTitle : TextView
    private lateinit var signature : TextView
    private lateinit var termsConLayout : ConstraintLayout
    private lateinit var termsOFUseView : RelativeLayout
    private lateinit var termsCloseBtn : ImageView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var MTETitle : ConstraintLayout
    private lateinit var fragContainerConstraintSet: ConstraintSet
    private lateinit var cvdPanel : View
    private lateinit var dibetesPanel : View
    private lateinit var depressionPanel : View
    private lateinit var anxietyPanel : View
    private lateinit var painPanel : View
    private lateinit var dietPanel : View
    private lateinit var beckDeprPanel : View
    private lateinit var geriatricPanel : View
    private lateinit var staiPanel : View
    private lateinit var zungPanel : View
    private lateinit var hammiltonPanel : View
    private lateinit var dassPanel : View
    private lateinit var cvdVectorIcon : View
    private lateinit var diabetesVectorIcon : View
    private lateinit var depressionIcon : View
    private lateinit var anxietyIcon : View
    private lateinit var dietIcon : View
    private lateinit var painIcon : View
    private lateinit var gdsDepressionIcon : View
    private lateinit var pdqIcon : View
    private lateinit var bdiIcon : View
    private lateinit var hamDIcon : View
    private lateinit var staiAnxietyIcon : View
    private lateinit var dassIcon : View
    private lateinit var zungIcon : View
    private lateinit var cvdTestTitle : TextView
    private lateinit var diabetestestTitle : TextView
    private lateinit var depressionTestTitle : TextView
    private lateinit var anxietyTestTitle : TextView
    private lateinit var dietTestTitle : TextView
    private lateinit var painTestTitle : TextView
    private lateinit var beckDeprTxtV : TextView
    private lateinit var gerDepTxtV : TextView
    private lateinit var staiTxtV : TextView
    private lateinit var dassTxtV : TextView
    private lateinit var hamiltonTxtV : TextView
    private lateinit var zungTxtV : TextView
    private lateinit var animationZoomIn : Animation
    private lateinit var animationBounce : Animation
    private lateinit var animationSlideLeftToRight : Animation
    private lateinit var animationSlideRightToLeft : Animation
    private lateinit var animationSlideTopToBottom : Animation
    private lateinit var animationSlideBottomToTop : Animation
    private lateinit var animationShowTest : Animation
    private lateinit var animationHideTest : Animation
    private lateinit var bounceTests : Animation
    private lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentViewForFirstAppScreen()
    }

    fun setContentViewForFirstAppScreen()
    {
        setContentView(R.layout.app_first_screen)

        initRealmDB()

        MTETitle = findViewById(R.id.include_cvd_title_form)
        MTETitleForm = MTETitle.findViewById(R.id.cvdTitleForm)
        var menuIcon = MTETitle.findViewById<ImageView>(R.id.userIcon)
        menuIcon.visibility = View.INVISIBLE


        MTETitleForm.setOnClickListener {
            setContentViewForFirstAppScreen()
        }

        loginDoctorButton = findViewById(R.id.loginDoctorTxtV)
        loginDoctorButton.setOnClickListener {
            showPatientLasttest = false
            setContentView(R.layout.activity_main)
//            setContentViewForMainLayout(false)
            constraintLayout = findViewById(R.id.mainConLayout)
            fragmentContainer = findViewById(R.id.fragmentContainer)
            loginDoctorFragment = LoginDoctorFragment.newInstance()
//            hideLayoutElements()
            fragmentTransaction(loginDoctorFragment)
        }

        registerDoctorButton = findViewById(R.id.registerDoctorTxtV)
        registerDoctorButton.setOnClickListener {
            showPatientLasttest = false
            setContentViewForMainLayout(showPatientLasttest)
            registerDoctorFragment = RegisterDoctorFragment.newInstance()
            hideLayoutElements()
            fragmentTransaction(registerDoctorFragment)
        }
    }

    private fun setContentViewForIntroScreen()
    {

        setContentView(R.layout.initial_screen)


        MTETitle = findViewById(R.id.include_cvd_title_form)
        MTETitleForm = MTETitle.findViewById(R.id.cvdTitleForm)


        var menuIcon = MTETitle.findViewById<ImageView>(R.id.userIcon)
        menuIcon.visibility = View.INVISIBLE

        MTETitleForm.setOnClickListener {
            setContentViewForFirstAppScreen()
        }

        allCustomersTxtV = findViewById<TextView>(R.id.allCustomersTxtV)
        allCustomersTxtV.setOnClickListener {
            setContentViewForSearchCustomersScreen()
        }

        newCustomerTxtV = findViewById<TextView>(R.id.newCustomerTxtV)
        newCustomerTxtV.setOnClickListener {
            registerFragment = RegisterFragment()
            showPatientLasttest = true
            setContentViewForMainLayout(showPatientLasttest)
            fragmentTransaction(registerFragment)
        }


    }

    fun setContentViewForSearchCustomersScreen() {
        setContentView(R.layout.intro_screen_search_customers)
        customersListView = findViewById(R.id.customerListView)
        customerSearchView = findViewById(R.id.customersSearchView)

        MTETitle = findViewById(R.id.include_cvd_title_form)
        MTETitleForm = MTETitle.findViewById(R.id.cvdTitleForm)

        var menuIcon = MTETitle.findViewById<ImageView>(R.id.userIcon)
        menuIcon.visibility = View.INVISIBLE

        MTETitleForm.setOnClickListener {
            setContentViewForFirstAppScreen()
        }


        //get the doctor file from realm
        val doctorName = getPreferences(Context.MODE_PRIVATE).getString("doctorUserName" , "tempDoctor")
        var doctor = Doctor()
        var lastnames = ArrayList<String>()
        var realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            doctor= realm.where(Doctor::class.java).equalTo("doctorUserName" , doctorName).findFirst()!!
            //set a set of customer for the doctor
            if (doctor!!.doctorCustomers != null)
            {
                for (i in 0 until doctor!!.doctorCustomers!!.size)
                {
                    lastnames.add(doctor!!.doctorCustomers!!.get(i)!!)
                }
            }


//            lastnames.apply {
//                this.add("Papadopoulos")
//                this.add("Marantos")
//                this.add("Christodoulopoulou")
//                this.add("zikos")
//            }

            var patientList = RealmList<String>()
            for (i in 0..lastnames.size -1)
            {
                patientList.add(lastnames.get(i))
            }
            doctor!!.doctorCustomers = patientList

            it.insertOrUpdate(doctor)
        }

        var customerLastNames = ArrayList<String>()

        customerLastNames = getLastNamesFromRealm()

        var doctorPatients = ArrayList<String>()


        for (name in customerLastNames)
        {
            if (lastnames.contains(name))
            {
                doctorPatients.add(name)
            }
        }

        lastNameArrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorPatients)
        customersListView.adapter = lastNameArrayAdapter


        customerSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                lastNameArrayAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lastNameArrayAdapter.filter.filter(newText)
                return false
            }
        })
        customersListView.setOnItemClickListener { parent, view, position, id ->
            var customerLastname = lastNameArrayAdapter.getItem(position).toString()

            //query realm database
            var realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                val patient: Patient? = it.where(Patient::class.java).equalTo("patientLastName", customerLastname).findFirst()
                Log.d("LOGIN" , patient!!.userName)
                //save the username in shared preferences
                val sharedPref = getPreferences(Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString("userName", patient.userName)
                    putString("MSG", "Welcome " + patient.userName.toString())
                    apply()
                }
//                    mainActivity.setLoginItemTitle()
                hideSoftInputKeyboard()
            }
            showPatientLasttest = true
            setContentViewForMainLayout(true)
        }

    }


    private fun setContentViewForMainLayout(showList : Boolean)
    {
        setContentView(R.layout.activity_main)

        initRealmDB()
        //initialize the all patienttestlistview
        allPatientResultsPopUp = findViewById(R.id.include_all_patient_list_test)
        if (showList == true)
            allPatientResultsPopUp.visibility = View.VISIBLE
        allPatientResultsLinLayout = allPatientResultsPopUp.findViewById(R.id.patientTestsLinLayout)
        var patientname = allPatientResultsPopUp.findViewById<TextView>(R.id.patientNameTxtV)
        patientname.setText(getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser"))
        patienTestListOkBtn = allPatientResultsPopUp.findViewById(R.id.okBtn)
        allPatientsTestNameListView = findViewById(R.id.alltestsNameResultsistView)
        allPatientTestDateLisView = findViewById(R.id.alltestsDatesResultsistView)
        allPatientTestRecyclerView = findViewById(R.id.allPatientTestRecView)
        allPatientTestRecyclerView.layoutManager = LinearLayoutManager(this)
        allPatienttestNameTextView = findViewById(R.id.testNamesTxtV)
        allPatienttestDateTextView = findViewById(R.id.testDatesTxtV)
        allPatientTestDateLisView = findViewById(R.id.alltestsDatesResultsistView)
//        allPatientResultsConLayout = allPatientResultsPopUp.findViewById(R.id.alltestResultsConLayout)
        patienTestListOkBtn.setOnClickListener {
            allPatientResultsPopUp.visibility = View.INVISIBLE
            mainScrollView.visibility =View.VISIBLE
            setScrolViewConstraint(1)
            showLayoutElements()
            showMedicalTests()
        }


        var patientUserName = getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")
        if (patientUserName!= "tempUser")
        {
            if (showList == true)
            {
                allPatientsTestNameListView.invalidateViews()
                allPatientsTestNameListView.invalidate()
                allPatientTestNamesAdapter = ArrayAdapter(applicationContext , R.layout.textcenter , setTestDataListForPatient(1))
                allPatientTestNamesAdapter.clear()
                allPatientTestNamesAdapter.notifyDataSetChanged()
                allPatientTestNamesAdapter = ArrayAdapter(applicationContext , R.layout.textcenter , setTestDataListForPatient(1))
                allPatientTestNamesAdapter.notifyDataSetChanged()
                allPatientsTestNameListView.adapter = allPatientTestNamesAdapter
                allPatientTestDateLisView.invalidate()
                allPatientTestDateLisView.invalidateViews()
                allPatientTestDatesAdapter = ArrayAdapter(applicationContext , R.layout.textcenter , setTestDataListForPatient(2))
                allPatientTestDatesAdapter.clear()
                allPatientTestDatesAdapter.notifyDataSetChanged()
                allPatientTestDatesAdapter = ArrayAdapter(applicationContext , R.layout.textcenter , setTestDataListForPatient(2))
                allPatientTestDatesAdapter.notifyDataSetChanged()
                allPatientTestDateLisView.adapter = allPatientTestDatesAdapter
                var allPatientTestNames = ArrayList<String>()
                var allPatientTestDates = ArrayList<String>()
                if (patientUserName != "tempUser")
                {
                    allPatientTestNames = setTestDataListForPatient(1)
                    allPatientTestDates = setTestDataListForPatient(2)
                }


                //open the fragment with the data provided above
//            patietTestListFragment = PatietTestListFragment.newInstance(allPatientTestNames , allPatientTestDates , patientname.text as String)
//            fragmentTransaction(patietTestListFragment)

                val recyclerAdapter = CustomTestListAdapter(allPatientTestNames , allPatientTestDates)
                recyclerAdapter.notifyDataSetChanged()
                allPatientTestRecyclerView.removeAllViews()
                allPatientTestRecyclerView.adapter = recyclerAdapter
                showPatientLasttest = false
//            allPatientsTestNameListView.invalidate()
//            allPatientTestDateLisView.invalidate()
                allPatientResultsPopUp.visibility = View.VISIBLE
                allPatientResultsPopUp.invalidate()
                allPatientResultsLinLayout.invalidate()

            }
        }


        constraintLayout = findViewById(R.id.mainConLayout)
        mainConLayout = findViewById(R.id.mainConLayout)
        mainScrollView = mainConLayout.findViewById(R.id.mainScrollView)
        mainScrollView.visibility = View.INVISIBLE
        fragmentContainer = findViewById(R.id.fragmentContainer)
        bottomNavigationView = findViewById(R.id.appBottomnavigationView)
        includeTestOptionsPopup = findViewById(R.id.initial_test_screen_popup)
        includeTestOptionsPopup.visibility = View.INVISIBLE
        addNewTxtV = includeTestOptionsPopup.findViewById(R.id.addNewTxtV)
        updateLastTxtV = includeTestOptionsPopup.findViewById(R.id.updateLastTxtV)
        historyTxtV = includeTestOptionsPopup.findViewById(R.id.historyTxtV)
        addNewTxtV.setOnClickListener {
            addNewTest()
        }
        updateLastTxtV.setOnClickListener {
            updateLastTest()
        }
        historyTxtV.setOnClickListener {
            openHistory()
        }
        MTETitle = findViewById(R.id.include_cvd_title_form)
        MTETitle.post {

        }
        MTETitleForm = MTETitle.findViewById(R.id.cvdTitleForm)
        userIconImg = MTETitle.findViewById(R.id.userIcon)
        companyLogo = MTETitle.findViewById(R.id.covariance_logo)
        appTitle = MTETitle.findViewById(R.id.cvdTitleTxtV)
        signature = MTETitle.findViewById(R.id.signature)
        termsConLayout = findViewById(R.id.include_popup_menu)
        termsOFUseView = termsConLayout.findViewById(R.id.termsRelLayout)
        termsCloseBtn = termsConLayout.findViewById(R.id.closeBtn)
        cvdVectorIcon = findViewById(R.id.cvdPanel)
        diabetesVectorIcon = findViewById(R.id.diabetesPanel)
        depressionIcon = findViewById(R.id.depressionPanel)
        anxietyIcon = findViewById(R.id.anxietyPanel)
        dietIcon = findViewById(R.id.dietPanel)
        painIcon = findViewById(R.id.painPanel)
        gdsDepressionIcon = findViewById(R.id.gdPanel)
        bdiIcon = findViewById(R.id.bdiPanel)
        hamDIcon = findViewById(R.id.hammPanel)
        dassIcon = findViewById(R.id.dassPanel)
        staiAnxietyIcon = findViewById(R.id.staiPanel)
        zungIcon = findViewById(R.id.zungPanel)
        beckDeprTxtV = findViewById(R.id.bdiTxtV)
        gerDepTxtV = findViewById(R.id.gdsTxtV)
        staiTxtV = findViewById(R.id.staTxtV)
        dassTxtV = findViewById(R.id.dassTxtV)
        cvdTestTitle = findViewById(R.id.cvdTestTxtView)
        diabetestestTitle = findViewById(R.id.diabetesTestTxtView)
        depressionTestTitle = findViewById(R.id.depressionTestTxtView)
        anxietyTestTitle = findViewById(R.id.anxietyTestTxtView)
        dietTestTitle = findViewById(R.id.medDietScoreTxtView)
        painTestTitle = findViewById(R.id.painTxtView)
        zungTxtV = findViewById(R.id.zungTxtV)
        hamiltonTxtV = findViewById(R.id.hammTxtV)


        initPrefs()

        initUI()
    }

    private fun updateLastTest() {
        includeTestOptionsPopup.visibility = View.INVISIBLE
        when(testName)
        {
            "CardioVascularDisease" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                checkFragment = CheckFragment()
                checkFragment.arguments = bundle
                fragmentTransaction(checkFragment)
            }
            "DIABETES" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                checkDiabetesFragment = DiabetesCheckFragment()
                checkDiabetesFragment.arguments = bundle
                fragmentTransaction(checkDiabetesFragment)
            }
            "Major Depression Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                mdiCheckFragment = MDICheckFragment()
                mdiCheckFragment.arguments = bundle
                fragmentTransaction(mdiCheckFragment)
            }
            "Beck Anxiety Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                baiCheckFragment = BAICheckFragment()
                baiCheckFragment.arguments = bundle
                fragmentTransaction(baiCheckFragment)
            }
            "Brief Pain Inventory" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                bpiCheckFragment = BPICheckFragment()
                bpiCheckFragment.arguments = bundle
                fragmentTransaction(bpiCheckFragment)
            }
            "Mediterranean Diet Score" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                mdsCheckFragment = medDietTestFragment()
                mdsCheckFragment.arguments = bundle
                fragmentTransaction(mdsCheckFragment)
            }

            "Beck Depression Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                bDIFragment = BeckDepressionInventoryFragment()
                bDIFragment.arguments = bundle
                fragmentTransaction(bDIFragment)
            }
            "Geriatric Depression Scale" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                gdsCheckFragment = GDSCheckFragment()
                gdsCheckFragment.arguments = bundle
                fragmentTransaction(gdsCheckFragment)
            }
            "Hammilton Depression" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                hamDFragment = HamiltonDepressionFragment()
                hamDFragment.arguments = bundle
                fragmentTransaction(hamDFragment)
            }

            "STAI" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                staiCheckFragment = STAICheckFragment.newInstance()
                staiCheckFragment.arguments = bundle
                fragmentTransaction(staiCheckFragment)
            }

            "DASS" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                dassCheckFragment = DASSCheckFragment.newInstance()
                dassCheckFragment.arguments = bundle
                fragmentTransaction(dassCheckFragment)
            }

            "ZUNG" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "updateLast")
                zungCheckFFragment = CheckZUNGFragment.newInstance()
                zungCheckFFragment.arguments = bundle
                fragmentTransaction(zungCheckFFragment)
            }
        }
    }

    private fun addNewTest() {
        includeTestOptionsPopup.visibility = View.INVISIBLE
        when (testName)
        {
            "CardioVascularDisease" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                checkFragment = CheckFragment()
                checkFragment.arguments = bundle
                fragmentTransaction(checkFragment)
            }

            "DIABETES" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                checkDiabetesFragment = DiabetesCheckFragment()
                checkDiabetesFragment.arguments = bundle
                fragmentTransaction(checkDiabetesFragment)
            }
            "Major Depression Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                mdiCheckFragment = MDICheckFragment()
                mdiCheckFragment.arguments = bundle
                fragmentTransaction(mdiCheckFragment)
            }
            "Beck Anxiety Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                baiCheckFragment = BAICheckFragment()
                baiCheckFragment.arguments = bundle
                fragmentTransaction(baiCheckFragment)
            }
            "Brief Pain Inventory" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                bpiCheckFragment = BPICheckFragment()
                bpiCheckFragment.arguments = bundle
                fragmentTransaction(bpiCheckFragment)
            }
            "Mediterranean Diet Test" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                mdsCheckFragment = medDietTestFragment()
                mdsCheckFragment.arguments = bundle
                fragmentTransaction(mdsCheckFragment)
            }
            "Beck Depression Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                bDIFragment = BeckDepressionInventoryFragment()
                bDIFragment.arguments = bundle
                fragmentTransaction(bDIFragment)
            }
            "Geriatric Depression Scale" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                gdsCheckFragment = GDSCheckFragment()
                gdsCheckFragment.arguments = bundle
                fragmentTransaction(gdsCheckFragment)
            }
            "Hammilton Depression" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                hamDFragment = HamiltonDepressionFragment()
                hamDFragment.arguments = bundle
                fragmentTransaction(hamDFragment)
            }

            "STAI" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                staiCheckFragment = STAICheckFragment.newInstance()
                staiCheckFragment.arguments = bundle
                fragmentTransaction(staiCheckFragment)
            }

            "DASS" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                dassCheckFragment = DASSCheckFragment.newInstance()
                dassCheckFragment.arguments = bundle
                fragmentTransaction(dassCheckFragment)
            }
            "ZUNG" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "addNew")
                zungCheckFFragment = CheckZUNGFragment.newInstance()
                zungCheckFFragment.arguments = bundle
                fragmentTransaction(zungCheckFFragment)
            }
        }
    }

    private fun openHistory() {
        includeTestOptionsPopup.visibility = View.INVISIBLE
        when (testName)
        {
            "CardioVascularDisease" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                checkFragment = CheckFragment()
                checkFragment.arguments = bundle
                fragmentTransaction(checkFragment)
            }

            "DIABETES" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                checkDiabetesFragment = DiabetesCheckFragment()
                checkDiabetesFragment.arguments = bundle
                fragmentTransaction(checkDiabetesFragment)
            }
            "Major Depression Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                mdiCheckFragment = MDICheckFragment()
                mdiCheckFragment.arguments = bundle
                fragmentTransaction(mdiCheckFragment)
            }
            "Beck Anxiety Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                baiCheckFragment = BAICheckFragment()
                baiCheckFragment.arguments = bundle
                fragmentTransaction(baiCheckFragment)
            }
            "Brief Pain Inventory" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                bpiCheckFragment = BPICheckFragment()
                bpiCheckFragment.arguments = bundle
                fragmentTransaction(bpiCheckFragment)
            }
            "Mediterranean Diet Test" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                mdsCheckFragment = medDietTestFragment()
                mdsCheckFragment.arguments = bundle
                fragmentTransaction(mdsCheckFragment)
            }

            "Beck Depression Index" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                bDIFragment = BeckDepressionInventoryFragment()
                bDIFragment.arguments = bundle
                fragmentTransaction(bDIFragment)
            }
            "Geriatric Depression Scale" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                gdsCheckFragment = GDSCheckFragment()
                gdsCheckFragment.arguments = bundle
                fragmentTransaction(gdsCheckFragment)
            }
            "Hammilton Depression" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                hamDFragment = HamiltonDepressionFragment()
                hamDFragment.arguments = bundle
                fragmentTransaction(hamDFragment)
            }

            "STAI" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                staiCheckFragment = STAICheckFragment.newInstance()
                staiCheckFragment.arguments = bundle
                fragmentTransaction(staiCheckFragment)
            }

            "DASS" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                dassCheckFragment = DASSCheckFragment.newInstance()
                dassCheckFragment.arguments = bundle
                fragmentTransaction(dassCheckFragment)
            }
            "ZUNG" ->
            {
                var bundle = Bundle()
                bundle.putString("patientId" , "")
                bundle.putString("testDate" , "")
                bundle.putString("openType" , "history")
                zungCheckFFragment = CheckZUNGFragment.newInstance()
                zungCheckFFragment.arguments = bundle
                fragmentTransaction(zungCheckFFragment)
            }
        }
    }



    fun loadMedicalAppScreen()
    {
        setContentViewForIntroScreen()
    }

    fun loadLoginDoctorFragment()
    {
        loginDoctorFragment = LoginDoctorFragment.newInstance()
        fragmentTransaction(loginDoctorFragment)
    }

    private fun getNamesFromRealm() : ArrayList<String>
    {
        var patientNames = ArrayList<String>()
        var realm = Realm.getDefaultInstance()
        var allPatientNames = realm.where(Patient::class.java).findAll()
        for (patient in allPatientNames)
        {
            patientNames.add(patient.patientName)
        }
        return patientNames
    }

    private fun getLastNamesFromRealm() : ArrayList<String>
    {
        var patientLastNames = ArrayList<String>()
        var realm = Realm.getDefaultInstance()
        var allPatientLastNames = realm.where(Patient::class.java).findAll()
        for (patient in allPatientLastNames)
        {
            patientLastNames.add(patient.patientLastName)
        }
        return patientLastNames
    }

    private fun initUI() {


        showLayoutElements()

        MTETitleForm.post {
            mteTitleFormHeight = MTETitleForm.height
//            val layoutParams = mainScrollView
//                .getLayoutParams() as ConstraintLayout.LayoutParams
//
//            layoutParams.setMargins(0, 0, 0, mteTitleFormHeight)
//            mainScrollView.layoutParams = layoutParams
        }

        mainConLayout.setOnClickListener {
            if (termsOFUseView.visibility == View.VISIBLE)
            {
                termsConLayout.visibility = View.INVISIBLE
                termsOFUseView.visibility = View.INVISIBLE
            }
        }

        var mteConLayout = includeTestOptionsPopup.findViewById<ConstraintLayout>(R.id.include_cvd_title_form)
        mteConLayout.setOnClickListener {
            setContentViewForMainLayout(true)
        }

        includeTestOptionsPopup.findViewById<ImageView>(R.id.userIcon).setOnClickListener {
            showPopUp(userIconImg)
        }


        MTETitle.setOnClickListener {
            hideSoftInputKeyboard()
            hideFragmentVisibility()
            showLayoutElements()
            onBackPressed()
            playSelectTestAudio(1)
            showMedicalTests()
        }

        //set the view references for the panels
        cvdPanel = findViewById(R.id.cvdPanel)
        dibetesPanel = findViewById(R.id.diabetesPanel)
        depressionPanel = findViewById(R.id.depressionPanel)
        anxietyPanel = findViewById(R.id.anxietyPanel)
        painPanel = findViewById(R.id.painPanel)
        dietPanel = findViewById(R.id.dietPanel)
        beckDeprPanel = findViewById(R.id.bdiPanel)
        geriatricPanel = findViewById(R.id.gdPanel)
        staiPanel = findViewById(R.id.staiPanel)
        zungPanel = findViewById(R.id.zungPanel)
        hammiltonPanel = findViewById(R.id.hammPanel)
        dassPanel = findViewById(R.id.dassPanel)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        cvdPanel.layoutParams.height = height / 5
        cvdPanel.layoutParams.width = (width / 2.3).toInt()

        dibetesPanel.layoutParams.height = height / 5
        dibetesPanel.layoutParams.width = (width / 2.3).toInt()

        depressionPanel.layoutParams.height = height / 5
        depressionPanel.layoutParams.width = (width / 2.3).toInt()

        anxietyPanel.layoutParams.height = height / 5
        anxietyPanel.layoutParams.width = (width / 2.3).toInt()

        painPanel.layoutParams.height = height / 5
        painPanel.layoutParams.width = (width / 2.3).toInt()

        dietPanel.layoutParams.height = height / 5
        dietPanel.layoutParams.width = (width / 2.3).toInt()

        beckDeprPanel.layoutParams.height = height / 5
        beckDeprPanel.layoutParams.width = (width / 2.3).toInt()

        geriatricPanel.layoutParams.height = height / 5
        geriatricPanel.layoutParams.width = (width / 2.3).toInt()

        staiPanel.layoutParams.height = height / 5
        staiPanel.layoutParams.width = (width / 2.3).toInt()

        zungPanel.layoutParams.height = height / 5
        zungPanel.layoutParams.width = (width / 2.3).toInt()

        hammiltonPanel.layoutParams.height = height / 5
        hammiltonPanel.layoutParams.width = (width / 2.3).toInt()

        dassPanel.layoutParams.height = height / 5
        dassPanel.layoutParams.width = (width / 2.3).toInt()

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        checkFragment = CheckFragment()
        checkDiabetesFragment = DiabetesCheckFragment()
        mdiCheckFragment = MDICheckFragment()
        baiCheckFragment = BAICheckFragment()
        mdsCheckFragment = medDietTestFragment()
        bpiCheckFragment = BPICheckFragment()
        gdsCheckFragment = GDSCheckFragment()
        bDIFragment = BeckDepressionInventoryFragment()
        hamDFragment = HamiltonDepressionFragment()
        staiCheckFragment = STAICheckFragment()
        dassCheckFragment = DASSCheckFragment()
        zungCheckFFragment = CheckZUNGFragment()
        var resultsArray = IntArray(8)
        resultsArray[0] = 40
        resultsArray[1] = 55
        resultsArray[2] = 70
        resultsArray[3] = 25
        resultsArray[4] = 75
        resultsArray[5] = 90
        resultsArray[6] = 15
        resultsArray[7] = 20
//        pdqResultFragment = ResultExtraFragment.newInstance(resultsArray)
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        setFragmentContainerConstraint(2)

//        showTermsOfUseLayout()

        userIconImg.animate().alpha(1F).duration = 1500

        userIconImg.animate()
            .scaleX(0.5f).scaleY(0.5f) //scale to quarter(half x,half y)
            .translationY((userIconImg.height / 4).toFloat())
            .rotation(360f) // one round turns
            .setDuration(500) // all take 1 seconds
            .withEndAction {
                //animation ended
                userIconImg.animate()
                    .scaleX(1f).scaleY(1f)
                    // move to bottom / right
                    .rotation(360f).duration = 500 // all take 1 seconds
            }

        termsOFUseView.setOnClickListener {
            hideTermsOfUseLayout()
        }


        termsCloseBtn.setOnClickListener {
            hideTermsOfUseLayout()
        }

        userIconImg.setOnClickListener {
            showPopUp(userIconImg)
        }

//        setAllViewsDimens()



        hideLayoutElements()
        //set the animation for the check button
        animationZoomIn = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        animationBounce  = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)

        //set the animations for the tests rellayout
        animationShowTest = AnimationUtils.loadAnimation(applicationContext , R.anim.showtest)
        animationHideTest = AnimationUtils.loadAnimation(applicationContext, R.anim.hidetest)

        bounceTests = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce_tests)


        //bounce the userIcon

        //set the animation for the testRelLayout slide
        animationSlideLeftToRight = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_left_to_right)
        animationSlideRightToLeft = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_right_to_left)
        animationSlideTopToBottom = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_top_to_bottom)

        animationSlideBottomToTop = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_bottom_to_top)

        initTestsRelLayouts()


    }

    private fun initPrefs()
    {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("LOG" , "Customer Change")
            putString("MSG" , "Medical Test Estimator")
//            putString("userName" , "tempUser")
            apply()
        }
    }


    private fun showTermsOfUseLayout() {
        termsConLayout.visibility = View.VISIBLE
        termsOFUseView.visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        termsConLayout.visibility = View.INVISIBLE
        termsOFUseView.visibility = View.INVISIBLE
    }

    private fun initTestsRelLayouts()
    {
        cvdVectorIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(2)
            openTestPopUp("CardioVascularDisease")

        }
        diabetesVectorIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(3)
            openTestPopUp("DIABETES")

        }

        depressionIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(4)
            openTestPopUp("Major Depression Index")

        }

        anxietyIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(5)
            openTestPopUp("Beck Anxiety Index")

        }

        dietIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(6)
            openTestPopUp("Mediterranean Diet Test")

        }

        painIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(7)
            openTestPopUp("Brief Pain Inventory")

        }

        gdsDepressionIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(8)
            openTestPopUp("Geriatric Depression Scale")

        }

//        pdqIcon.setOnClickListener {
//            hideLayoutElements()
//            playSelectTestAudio(9)
//            fragmentTransaction(pdqCheckFragment)
//        }

        bdiIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(10)
            openTestPopUp("Beck Depression Index")
        }

        hamDIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(13)
            openTestPopUp("Hammilton Depression")
        }

        staiAnxietyIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(11)
            openTestPopUp("STAI")
        }

        dassIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(12)
            openTestPopUp("DASS")
        }

        zungIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(14)
            openTestPopUp("ZUNG")
        }


    }

    private fun openTestPopUp(test: String) {
        setFragmentContainerConstraint(1)
        setScrolViewConstraint(2)
        includeTestOptionsPopup.visibility = View.VISIBLE
        testName = test
    }


    override fun onStart() {
        super.onStart()


    }


    fun playSelectTestAudio(id : Int) {
        when (id) {
            1 -> {
                val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.sound_clip1)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
            2 -> {
                val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.test_for_cvd)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
            3 -> {
                val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.test_for_diabetes)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
            4 -> {
                val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.mdi_test_index)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
            5 -> {
                val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.test_for_bai)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
            6 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.med_diet_test)
                mediaPlayer.start()
            }
            7 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.bpi_test)
                mediaPlayer.start()
            }
            8 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.gds_speech)
                mediaPlayer.start()
            }
            9 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.pdq_speech)
                mediaPlayer.start()
            }
            10 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.beck_derpession)
                mediaPlayer.start()
            }
            11 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.stai_speech)
                mediaPlayer.start()
            }
            12 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.dass_speech)
                mediaPlayer.start()
            }
            13 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.hammilton_speech)
                mediaPlayer.start()
            }
            14 -> {
                val mediaPlayer : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.zung_speech)
                mediaPlayer.start()
            }
        }

    }

    fun setLogOutUI(){

        onBackPressed()
        showLayoutElements()
        val shared = getPreferences(Context.MODE_PRIVATE) ?: return
        with(shared.edit())
        {
            putString("LOG","Logout")
            apply()
        }
        val message = shared.getString("MSG" , "Test 1")
        val login = shared.getString("LOG" , "Test 2")
        val popUpmenu = popupMenu.menu
        popUpmenu.getItem(1).setTitle(login)
        //replace register with check user
        var user : String
        with(shared.edit())
        {
            user = shared.getString("userName" , "tempUser")!!
        }
        user = "$user data"
        popUpmenu.getItem(3).setTitle(user)
    }

    @SuppressLint("RestrictedApi")
    fun logOutUser() {
        val shared = getPreferences(Context.MODE_PRIVATE) ?: return
        with(shared.edit())
        {
            putString("MSG" , "Medical Test Estimator")
            putString("LOG" , "Customer Change")
            putString("userName" , "tempUser")
            apply()
        }
        val message = shared.getString("MSG" , "Test 1")
        val login = shared.getString("LOG" , "Test 2")
        val popUpmenu = popupMenu.menu
        popUpmenu.getItem(1).setTitle("Αλλαγή ασθενούς")
        //set register button message
        popUpmenu.getItem(3).setTitle("tempUser Data")

//        enableRegisterButton()
//        disableCheckButton()
    }

    fun logOutDoctor()
    {
        val shared = getPreferences(Context.MODE_PRIVATE) ?: return
        with (shared.edit())
        {
            putString("doctorUserName" , "tempDoctor")
        }
        setContentViewForFirstAppScreen()
    }

    fun backToActivity() {
        onBackPressed()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        fragmentContainer.setBackgroundColor(getColor(R.color.MidnightBlue))
        fragmentContainer.visibility = View.VISIBLE
        hideFragmentVisibility()
        includeTestOptionsPopup.visibility = View.INVISIBLE
        setFragmentContainerConstraint(1)
        supportFragmentManager.popBackStack()
//        showLayoutElements()
//        showMedicalTests()
        setContentViewForMainLayout(true)
    }

    private fun setScrolViewConstraint(index : Int)
    {
        when(index)
        {
            1 ->
            {
                scrollViewConstrainSet = ConstraintSet()
                scrollViewConstrainSet.clone(mainConLayout)
                scrollViewConstrainSet.connect(mainScrollView.id , ConstraintSet.TOP , R.id.include_cvd_title_form , ConstraintSet.BOTTOM)
                scrollViewConstrainSet.applyTo(mainConLayout)
            }
            2 ->
            {
                scrollViewConstrainSet = ConstraintSet()
                scrollViewConstrainSet.clone(mainConLayout)
                scrollViewConstrainSet.connect(mainScrollView.id , ConstraintSet.TOP , ConstraintSet.PARENT_ID , ConstraintSet.BOTTOM)
                scrollViewConstrainSet.applyTo(mainConLayout)
            }
        }

    }

    private fun setFragmentContainerConstraint(action : Int)
    {
        if (action == 1)
        {
            fragContainerConstraintSet = ConstraintSet()
            fragContainerConstraintSet.clone(constraintLayout)
            fragContainerConstraintSet.connect(fragmentContainer.id , ConstraintSet.TOP , ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            fragContainerConstraintSet.applyTo(constraintLayout)
            fragmentContainer.elevation = 10f
        }
        if ((action == 2) && (includeTestOptionsPopup.visibility == View.VISIBLE))
        {
            fragContainerConstraintSet = ConstraintSet()
            fragContainerConstraintSet.clone(constraintLayout)
            fragContainerConstraintSet.connect(fragmentContainer.id , ConstraintSet.TOP , R.id.horGL1, ConstraintSet.BOTTOM)
            fragContainerConstraintSet.applyTo(constraintLayout)
            fragmentContainer.elevation = 10f
        }
    }

    private fun showMedicalTests() {

        //gdsDepressionIcon.visibility = View.VISIBLE
        MTETitle.visibility = View.VISIBLE
        cvdVectorIcon.animate().alpha(1f).duration = 1200
        cvdVectorIcon.startAnimation(bounceTests)
        cvdVectorIcon.visibility = View.VISIBLE
        diabetesVectorIcon.animate().alpha(1f).duration = 1200
        diabetesVectorIcon.startAnimation(bounceTests)
        diabetesVectorIcon.visibility = View.VISIBLE
        depressionIcon.animate().alpha(1f).duration = 1200
        depressionIcon.startAnimation(bounceTests)
        dietIcon.animate().alpha(1f).duration = 1200
        dietIcon.startAnimation(bounceTests)
        dietIcon.visibility = View.VISIBLE
        anxietyIcon.animate().alphaBy(1f).duration = 1200
        anxietyIcon.startAnimation(bounceTests)
        anxietyIcon.visibility = View.VISIBLE
        staiAnxietyIcon.animate().alphaBy(1f).duration = 1200
        staiAnxietyIcon.startAnimation(bounceTests)
        staiAnxietyIcon.visibility = View.VISIBLE
        painIcon.animate().alphaBy(1f).duration = 1200
        painIcon.startAnimation(bounceTests)
        painIcon.visibility = View.VISIBLE
        bdiIcon.animate().alphaBy(1f).duration = 1200
        bdiIcon.startAnimation(bounceTests)
        bdiIcon.visibility = View.VISIBLE
        gdsDepressionIcon.animate().alphaBy(1f).duration = 1200
        gdsDepressionIcon.startAnimation(bounceTests)
        gdsDepressionIcon.visibility = View.VISIBLE
        hamDIcon.animate().alphaBy(1f).duration = 1200
        hamDIcon.startAnimation(bounceTests)
        hamDIcon.visibility = View.VISIBLE
        dassIcon.animate().alphaBy(1f).duration = 1200
        dassIcon.startAnimation(bounceTests)
        dassIcon.visibility = View.VISIBLE
        zungIcon.animate().alphaBy(1f).duration = 1200
        zungIcon.startAnimation(bounceTests)
        zungIcon.visibility = View.VISIBLE

    }



    private fun hideSoftInputKeyboard()
    {
        val view: View = window.decorView.rootView!!
        val imm = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken , 0)
    }


    fun hideLayoutElements() {
        MTETitle.visibility = View.INVISIBLE
        cvdVectorIcon.clearAnimation()
        diabetesVectorIcon.clearAnimation()
        cvdVectorIcon.visibility = View.INVISIBLE
        diabetesVectorIcon.visibility = View.INVISIBLE
        depressionIcon.clearAnimation()
        depressionIcon.visibility = View.INVISIBLE
        anxietyIcon.clearAnimation()
        anxietyIcon.visibility = View.INVISIBLE
        staiAnxietyIcon.clearAnimation()
        staiAnxietyIcon.visibility = View.INVISIBLE
        dassIcon.clearAnimation()
        dassIcon.visibility = View.INVISIBLE
        dietIcon.clearAnimation()
        dietIcon.visibility = View.INVISIBLE
        painIcon.clearAnimation()
        painIcon.visibility = View.INVISIBLE
        gdsDepressionIcon.clearAnimation()
        gdsDepressionIcon.visibility = View.INVISIBLE
        bdiIcon.clearAnimation()
        bdiIcon.visibility = View.INVISIBLE
        hamDIcon.clearAnimation()
        hamDIcon.visibility = View.INVISIBLE
        zungIcon.clearAnimation()
        zungIcon.visibility = View.INVISIBLE
        cvdTestTitle.visibility = View.INVISIBLE
        diabetestestTitle.visibility = View.INVISIBLE
        depressionTestTitle.visibility = View.INVISIBLE
        anxietyTestTitle.visibility = View.INVISIBLE
        dietTestTitle.visibility = View.INVISIBLE
        painTestTitle.visibility = View.INVISIBLE
        beckDeprTxtV.visibility = View.INVISIBLE
        gerDepTxtV.visibility = View.INVISIBLE
        staiTxtV.visibility = View.INVISIBLE
        dassTxtV.visibility = View.INVISIBLE
        zungTxtV.visibility = View.INVISIBLE
        hamiltonTxtV.visibility = View.INVISIBLE

    }

    private fun showLayoutElements() {
        MTETitle.visibility = View.VISIBLE
        mainScrollView.visibility = View.VISIBLE
        cvdVectorIcon.clearAnimation()
        cvdVectorIcon.visibility = View.VISIBLE
        diabetesVectorIcon.clearAnimation()
        diabetesVectorIcon.visibility = View.VISIBLE
        depressionIcon.clearAnimation()
        depressionIcon.visibility = View.VISIBLE
        anxietyIcon.clearAnimation()
        anxietyIcon.visibility = View.VISIBLE
        dietIcon.clearAnimation()
        dietIcon.visibility = View.VISIBLE
        painIcon.clearAnimation()
        painIcon.visibility = View.VISIBLE
        gdsDepressionIcon.clearAnimation()
        gdsDepressionIcon.visibility = View.VISIBLE
        bdiIcon.clearAnimation()
        bdiIcon.visibility = View.VISIBLE
        hamDIcon.clearAnimation()
        hamDIcon.visibility = View.VISIBLE
        staiAnxietyIcon.clearAnimation()
        staiAnxietyIcon.visibility = View.VISIBLE
        dassIcon.clearAnimation()
        dassIcon.visibility = View.VISIBLE
        zungIcon.clearAnimation()
        zungIcon.visibility = View.VISIBLE
        cvdVectorIcon.visibility = View.VISIBLE
        cvdTestTitle.visibility = View.VISIBLE
        diabetesVectorIcon.visibility = View.VISIBLE
        diabetestestTitle.visibility = View.VISIBLE
        depressionTestTitle.visibility = View.VISIBLE
        anxietyTestTitle.visibility = View.VISIBLE
        dietTestTitle.visibility = View.VISIBLE
        painTestTitle.visibility = View.VISIBLE
        beckDeprTxtV.visibility = View.VISIBLE
        gerDepTxtV.visibility = View.VISIBLE
        staiTxtV.visibility = View.VISIBLE
        dassTxtV.visibility = View.VISIBLE
        zungTxtV.visibility = View.VISIBLE
        hamiltonTxtV.visibility = View.VISIBLE
    }

    private fun setAllViewsDimens()
    {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        companyLogo.layoutParams.width = width / 9
        companyLogo.layoutParams.height = width / 12

        userIconImg.layoutParams.width = width / 10
        userIconImg.layoutParams.height = width / 10

        (height * 0.14) / 8
    }


    private fun initRealmDB() {
        realmDB = RealmDB(applicationContext)
        Realm.init(applicationContext)
        var realm = Realm.getDefaultInstance()
        var patientCount = realm.where(Patient::class.java).count()
//        if (patientCount < 10)
//            buildRealmDatabase()
        }

    // this event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                showLayoutElements()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buildRealmDatabase()
    {
        var patient1 = Patient()
        patient1.patientId = 1.toString()
        patient1.userName = "lampros_1"
        patient1.password = "lampros#1"
        patient1.patientName = "Lampros"
        patient1.patientLastName = "Marantos"
        patient1.dateOfBirth = "1983 04 01"
        patient1.occupation = "Programmer"
        patient1.yearsOfApprentice = 2

        var patient2 = Patient()
        patient2.patientId = 2.toString()
        patient2.userName = "vaslis_1"
        patient2.password = "vasilis#1"
        patient2.patientName = "Vasilis"
        patient2.patientLastName = "Marantos"
        patient2.dateOfBirth = "1981 05 11"
        patient2.occupation = "Logistics"
        patient2.yearsOfApprentice = 4

        var patient3 = Patient()
        patient3.patientId = 3.toString()
        patient3.userName = "george_1"
        patient3.password = "george#1"
        patient3.patientName = "George"
        patient3.patientLastName = "Papadopoulos"
        patient3.dateOfBirth = "1975 02 17"
        patient3.occupation = "Student"
        patient3.yearsOfApprentice = 5

        var patient4 = Patient()
        patient4.patientId = 4.toString()
        patient4.userName = "nick_1"
        patient4.password = "nick#1"
        patient4.patientName = "Nick"
        patient4.patientLastName = "Melanitis"
        patient4.dateOfBirth = "1970 01 10"
        patient4.occupation = "Freelancer"
        patient4.yearsOfApprentice = 3

        var patient5 = Patient()
        patient5.patientId = 5.toString()
        patient5.userName = "dimitris_2"
        patient5.password = "dimitris#2"
        patient5.patientName = "Dimitris"
        patient5.patientLastName = "Zikos"
        patient5.dateOfBirth = "1964 12 10"
        patient5.occupation = "Freelancer"
        patient5.yearsOfApprentice = 3

        var patient6 = Patient()
        patient6.patientId = 6.toString()
        patient6.userName = "elena_1"
        patient6.password = "elena#1"
        patient6.patientName = "Elena"
        patient6.patientLastName = "Christodoulopoulou"
        patient6.dateOfBirth = "1982 08 10"
        patient6.occupation = "Accountant"
        patient6.yearsOfApprentice = 7

        var patient7 = Patient()
        patient7.patientId = 7.toString()
        patient7.userName = "dimitris_1"
        patient7.password = "dimitris#1"
        patient7.patientName = "Dimitris"
        patient7.patientLastName = "Marantos"
        patient7.dateOfBirth = "1984 11 15"
        patient7.occupation = "Driver"
        patient7.yearsOfApprentice = 6

        var patient8 = Patient()
        patient8.patientId = 8.toString()
        patient8.userName = "kostas_1"
        patient8.password = "kostas#1"
        patient8.patientName = "Kostas"
        patient8.patientLastName = "Nikolaou"
        patient8.dateOfBirth = "1972 03 09"
        patient8.occupation = "singer"
        patient8.yearsOfApprentice = 10

        var patient9 = Patient()
        patient9.patientId = 9.toString()
        patient9.userName = "petros_1"
        patient9.password = "petros#1"
        patient9.patientName = "Petros"
        patient9.patientLastName = "zikos"
        patient9.dateOfBirth = "1968 05 17"
        patient9.occupation = "Financial Manager"
        patient9.yearsOfApprentice = 4

        var patient10 = Patient()
        patient10.patientId = 10.toString()
        patient10.userName = "maria_1"
        patient10.password = "maria#1"
        patient10.patientName = "Maria"
        patient10.patientLastName = "Vasilogiannakopoulou"
        patient10.dateOfBirth = "1982 04 17"
        patient10.occupation = "Shop Owner"
        patient10.yearsOfApprentice = 4

        var realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insert(patient1)
            it.insert(patient2)
            it.insert(patient3)
            it.insert(patient4)
            it.insert(patient5)
            it.insert(patient6)
            it.insert(patient7)
            it.insert(patient8)
            it.insert(patient9)
            it.insert(patient10)
        }
    }

    private fun hideFragmentVisibility()
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.hide(loginFragment)
        fragmentTransaction.hide(registerFragment)
        fragmentTransaction.hide(checkFragment)
        fragmentTransaction.hide(checkDiabetesFragment)
        fragmentTransaction.hide(mdiCheckFragment)
        fragmentTransaction.hide(baiCheckFragment)
        fragmentTransaction.hide(mdsCheckFragment)
        fragmentTransaction.hide(bpiCheckFragment)
        fragmentTransaction.hide(gdsCheckFragment)
        fragmentTransaction.hide(staiCheckFragment)
        fragmentTransaction.hide(bDIFragment)
        fragmentTransaction.hide(dassCheckFragment)
        fragmentTransaction.hide(hamDFragment)
        fragmentTransaction.hide(zungCheckFFragment)
//        fragmentTransaction.hide(pdqCheckFragment)
        fragmentTransaction.hide(leaderBoardFragment)
            .commit()
    }


    fun fragmentTransaction(fragment : Fragment) {
            setFragmentContainerConstraint(1)
        if (!(fragment is LoginDoctorFragment) && !(fragment is RegisterDoctorFragment) && !(fragment is PatietTestListFragment))
            hideLayoutElements()
//        if (!(fragment is LoginDoctorFragment) && !(fragment is PatietTestListFragment))
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment is CheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is DiabetesCheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is MDICheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is BAICheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is medDietTestFragment)
            fragmentTransaction.show(fragment)
        if (fragment is LoginFragment)
            fragmentTransaction.show(loginFragment)
        if (fragment is RegisterFragment)
            fragmentTransaction.show(registerFragment)
        if (fragment is LoginDoctorFragment)
            fragmentTransaction.show(loginDoctorFragment)
        if (fragment is RegisterDoctorFragment)
            fragmentTransaction.show(registerDoctorFragment)
        if (fragment is PatietTestListFragment)
            fragmentTransaction.show(patietTestListFragment)
        if (fragment is BPICheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is GDSCheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is ResultExtraFragment)
            fragmentTransaction.show(pdqResultFragment)
        if (fragment is PDQCheckFirstCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckSecondCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckThridCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckFourthCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckFifthCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckSixthCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckSeventhCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is PDQCheckEightCategoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is BeckDepressionInventoryFragment)
            fragmentTransaction.show(fragment)
        if (fragment is HamiltonDepressionFragment)
            fragmentTransaction.show(fragment)
        if (fragment is DASSCheckFragment)
            fragmentTransaction.show(dassCheckFragment)
        if (fragment is CheckZUNGFragment)
            fragmentTransaction.show(fragment)
        if (fragment is LeaderBoardFragment)
            fragmentTransaction.show(fragment)
        if (fragment is HistoryFragment)
        {
            hideFragmentVisibility()
            val fm: FragmentManager = this.getSupportFragmentManager()

            for (i in 0 until fm.backStackEntryCount)
            {
                fm.popBackStack()
            }

            fragmentTransaction.show(fragment)
        }
        if (fragment is ResultTimelineFragment)
        {
            hideFragmentVisibility()
            val fm: FragmentManager = this.getSupportFragmentManager()

            for (i in 0 until fm.backStackEntryCount)
            {
                fm.popBackStack()
            }

            fragmentTransaction.show(fragment)
        }
        if (fragment.isAdded)
        {
            fragmentTransaction.remove(fragment)
        }
        fragmentTransaction.add(R.id.fragmentContainer , fragment).addToBackStack("FRAGMENT").commit()
    }

    //behavioral code related with Popup menu
    fun showPopUp(v : View)
    {
        popupMenu = PopupMenu(ContextThemeWrapper(applicationContext, R.style.PopupMenu), v)

        //set On MenuItemClickListener for the Popup menu
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.home_item -> {
                    playSelectTestAudio(1)
                    showMedicalTests()
                }
                R.id.questions ->
                {
                    backToActivity()
                }
                R.id.doctor_item ->
                {
                    logOutDoctor()
                }
                R.id.customer_change_item -> {
                    val prefs = getPreferences(Context.MODE_PRIVATE)
                    val message = prefs.getString("LOG", "Test1")
                    if (message == "Αλλαγή ασθενούς") {
                        setContentViewForSearchCustomersScreen()
                } else
                    logOutUser()
            }
                R.id.customer_add_item -> {
                    hideLayoutElements()
                    fragmentTransaction(registerFragment)
            }
//            R.id.doctor_logout_item -> {
//                logOutDoctor()
//            }
            R.id.user_item -> {
            }
            R.id.clear_data -> {

                AlertDialog.Builder(this)
                    .setTitle("Clear All Data")
                    .setMessage("Are you sure you want to delete the user data?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            // Continue with delete operation

                            Realm.init(applicationContext)
                            realm = Realm.getDefaultInstance()
                            realm.executeTransaction {
                                val username = getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
                                val patient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , username).findFirst()
                                //get the last recorded

                                //initialize all patient data fields
//                                patient!!.patientAge = ""
//                                patient!!.smoker = ""
//                                patient!!.patientSiblings = ""
//                                patient!!.patientBMI = ""
//                                patient!!.patientSex = ""
//                                patient!!.patientSteroids = ""
//                                patient!!.patientPAM = ""
//                                patient!!.SSB = ""
//                                patient!!.HDL = ""
//                                patient!!.TCH = ""
//                                patient!!.treatment = ""
//                                patient!!.patientRace = ""
//
//                                patient!!.patientMDIQ1 = null
//                                patient!!.patientMDIQ2 = null
//                                patient!!.patientMDIQ3 = null
//                                patient!!.patientMDIQ5 = null
//                                patient!!.patientMDIQ6 = null
//                                patient!!.patientMDIQ7 = null
//                                patient!!.patientMDIQ8 = null
//                                patient!!.patientMDIQ5 = null
//                                patient!!.patientMDIQ10 = null
//                                patient!!.patientMDIQ11 = null
//                                patient!!.patientMDIQ12 = null
//                                patient!!.patientMDIQ13 = null
//
//                                patient!!.patientBAIQ1 = null
//                                patient!!.patientBAIQ2 = null
//                                patient!!.patientBAIQ3 = null
//                                patient!!.patientBAIQ4 = null
//                                patient!!.patientBAIQ5 = null
//                                patient!!.patientBAIQ6 = null
//                                patient!!.patientBAIQ7 = null
//                                patient!!.patientBAIQ8 = null
//                                patient!!.patientBAIQ9 = null
//                                patient!!.patientBAIQ10 = null
//                                patient!!.patientBAIQ11 = null
//                                patient!!.patientBAIQ12 = null
//                                patient!!.patientBAIQ13 = null
//                                patient!!.patientBAIQ14 = null
//                                patient!!.patientBAIQ15 = null
//                                patient!!.patientBAIQ16 = null
//                                patient!!.patientBAIQ17 = null
//                                patient!!.patientBAIQ18 = null
//                                patient!!.patientBAIQ19 = null
//                                patient!!.patientBAIQ20 = null
//                                patient!!.patientBAIQ21 = null
//
//                                patient!!.patientGDSQ1 = null
//                                patient!!.patientGDSQ2 = null
//                                patient!!.patientGDSQ3 = null
//                                patient!!.patientGDSQ4 = null
//                                patient!!.patientGDSQ5 = null
//                                patient!!.patientGDSQ6 = null
//                                patient!!.patientGDSQ7 = null
//                                patient!!.patientGDSQ8 = null
//                                patient!!.patientGDSQ9 = null
//                                patient!!.patientGDSQ10 = null
//                                patient!!.patientGDSQ11 = null
//                                patient!!.patientGDSQ12 = null
//                                patient!!.patientGDSQ13 = null
//                                patient!!.patientGDSQ14 = null
//                                patient!!.patientGDSQ15 = null
//
//                                patient!!.patientPDQQ1 = null
//                                patient!!.patientPDQQ2 = null
//                                patient!!.patientPDQQ3 = null
//                                patient!!.patientPDQQ4 = null
//                                patient!!.patientPDQQ5 = null
//                                patient!!.patientPDQQ6 = null
//                                patient!!.patientPDQQ7 = null
//                                patient!!.patientPDQQ8 = null
//                                patient!!.patientPDQQ9 = null
//                                patient!!.patientPDQQ10 = null
//                                patient!!.patientPDQQ11 = null
//                                patient!!.patientPDQQ12 = null
//                                patient!!.patientPDQQ13 = null
//                                patient!!.patientPDQQ14 = null
//                                patient!!.patientPDQQ15 = null
//                                patient!!.patientPDQQ16 = null
//                                patient!!.patientPDQQ17 = null
//                                patient!!.patientPDQQ18 = null
//                                patient!!.patientPDQQ19 = null
//                                patient!!.patientPDQQ20 = null
//                                patient!!.patientPDQQ21 = null
//                                patient!!.patientPDQQ22 = null
//                                patient!!.patientPDQQ23 = null
//                                patient!!.patientPDQQ24 = null
//                                patient!!.patientPDQQ25 = null
//                                patient!!.patientPDQQ26 = null
//                                patient!!.patientPDQQ27 = null
//                                patient!!.patientPDQQ28 = null
//                                patient!!.patientPDQQ30 = null
//                                patient!!.patientPDQQ31 = null
//                                patient!!.patientPDQQ32 = null
//                                patient!!.patientPDQQ33 = null
//                                patient!!.patientPDQQ34 = null
//                                patient!!.patientPDQQ35 = null
//                                patient!!.patientPDQQ36 = null
//                                patient!!.patientPDQQ37 = null
//                                patient!!.patientPDQQ38 = null
//                                patient!!.patientPDQQ39 = null
                            }

                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()

            }
            R.id.terms_item -> {
                showTermsOfUseLayout()
            }
            R.id.leaderboard_item ->
            {
                fragmentTransaction(leaderBoardFragment)
            }
            }
            false
        }
        val inflater : MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.main_optionss_menu , popupMenu.menu)
        popupMenu.menu.getItem(3).title =
            this.getPreferences(Context.MODE_PRIVATE).getString("LOG" , "Αλλαγή ασθενούς")
        popupMenu.menu.getItem(5).title =
            this.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser") + " δεδομένα"
        //aplly font to all menu item
        applyFontToMenuItem(popupMenu.menu.getItem(0) , "Αρχική" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(1) , "Ερωτηματολόγια" , applicationContext)
        applyFontToMenuItem(popupMenu.menu.getItem(2) , "Έξοδος ιατρού" , applicationContext)
        applyFontToMenuItem(popupMenu.menu.getItem(3) , this.getPreferences(Context.MODE_PRIVATE).getString("LOG" , "Αλλαγή ασθνεούς")!! , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(4) , "Προσθήκη Ασθενούς" , applicationContext )
//        applyFontToMenuItem(popupMenu.menu.getItem(3) , "Doctor Log Out" , applicationContext)
        applyFontToMenuItem(popupMenu.menu.getItem(5) , this.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser") + " δεδομένα" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(6) , "Καθαρισμός δεδομένων" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(7) , "Όρρι Χρήσης" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(8) ,"Πίνακας αποτελεσμάτων" , applicationContext)


        // show icons on popup menu
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }else{
            try {
                val fields = popupMenu.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field[popupMenu]
                        val classPopupHelper =
                            Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons: Method = classPopupHelper.getMethod(
                            "setForceShowIcon",
                            Boolean::class.javaPrimitiveType
                        )
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //show the PopUp menu
        popupMenu.show()
    }

    fun hideResultFragment(resultFragment: ResultFragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.hide(resultFragment)
                .commit()
    }

    //function to apply font family to menu items
    private fun applyFontToMenuItem(menuItem: MenuItem, menuTitle : String, mContext : Context)
    {
        val typeface = Typeface.create("sans-serif",Typeface.NORMAL) //  THIS
        var string  = SpannableString(menuTitle)
        string.setSpan(typeface, 0, menuTitle.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuItem.title = menuTitle
    }

    private fun setTestDataListForPatient(testReturnType : Int) : ArrayList<String>
    {
        //get all tests related with the patient

        var allPatientTestNames : String = ""
        var allPatientTestDates : String = ""

        var allPatientTestNameData = ArrayList<String>()
        var allPatientTestDateData = ArrayList<String>()
        allPatientTestNameData.clear()
        allPatientTestDateData.clear()
        CVDTestName = ""
        DiabetesTestName = ""
        MDITestName = ""
        BAITestName = ""
        BPITestName = ""
        BDITestName = ""
        MDTTestName = ""
        GDSTestName = ""
        STAITestName = ""
        HammiltonTestName = ""
        DASSTestName = ""
        ZungTestName = ""
//        runOnUiThread {
//
//            allPatientTestNames = ""
//            allPatientTestDates = ""
//            allPatienttestNameTextView.setText(" ")
//            allPatienttestDateTextView.setText(" ")
//            allPatienttestNameTextView.postInvalidate()
//            allPatienttestDateTextView.postInvalidate()
//        }


        runOnUiThread {
            Realm.init(applicationContext)
            var realm = Realm.getDefaultInstance()
            var patientUserName = getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")
            //fetch patientId related with patient username
            var patient = realm.copyFromRealm(realm.where(Patient::class.java).equalTo("userName" , patientUserName).findFirst())
            var currentDate = Date()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR , currentDate.year)
            calendar.set(Calendar.MONTH , currentDate.month)
            calendar.set(Calendar.DAY_OF_MONTH , LocalDateTime.now().dayOfMonth)
            var allCVDTest = realm.where(Test::class.java).notEqualTo("SSB" , "").equalTo("patientId" , patient!!.patientId).findAll()
            var CVDTestResult = ""
            if (allCVDTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allCVDTestSize = allCVDTest.get(allCVDTest.size -1)
                CVDTestName = "CardioVascularDis"
                CVDTestDate = dateFormat.format(allCVDTestSize!!.testDate)
                CVDTestResult = "CardioVascularDis - ${dateFormat.format(allCVDTestSize!!.testDate)}"
            }

            var allDiabetesTest = realm.where(Test::class.java).notEqualTo("patientPAM" , "").equalTo("patientId" , patient!!.patientId).findAll()
            var DiabetesTestResult = ""
            if (allDiabetesTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allDiabetesSize = allDiabetesTest.get(allDiabetesTest.size -1)
                DiabetesTestName = "DIABETES"
                DiabetesTestDate = dateFormat.format(allDiabetesSize!!.testDate)
                DiabetesTestResult = "DIABETES - ${dateFormat.format(allDiabetesSize!!.testDate)}"
            }


            var allMDITest = realm.where(Test::class.java).isNotNull("patientMDIQ1").equalTo("patientId" , patient!!.patientId).findAll()
            var MDITestResult = ""
            if (allMDITest.size > 0)
            {

                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allMDISize = allMDITest.get(allMDITest.size -1)
                MDITestName = "Major Depression"
                MDITestDate = dateFormat.format(allMDISize!!.testDate)
                MDITestResult = "Major Depression - ${dateFormat.format(allMDISize!!.testDate)}"
            }


            var allBAITest = realm.where(Test::class.java).isNotNull("patientBAIQ1").equalTo("patientId" , patient!!.patientId).findAll()
            var BAITestResult = ""
            if (allBAITest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allBAISize = allBAITest.get(allBAITest.size -1)
                BAITestName = "Beck Anxiety"
                BAITestDate = dateFormat.format(allBAISize!!.testDate)
                BAITestResult = "Beck Anxiety - ${dateFormat.format(allBAISize!!.testDate)}"
            }

            var allMDSTest = realm.where(Test::class.java).isNotNull("patientMDSQ1").equalTo("patientId" , patient!!.patientId).findAll()
            var MDSTestResult = ""
            if (allMDSTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allMDSSize = allMDSTest.get(allMDSTest.size -1)
                MDTTestName = "Mediterranean Diet"
                MDTTestDate = dateFormat.format(allMDSSize!!.testDate)
                MDSTestResult = "Mediterranean Diet - ${dateFormat.format(allMDSSize!!.testDate)}"
            }

            var allBPITest = realm.where(Test::class.java).isNotNull("patientBPIQ1").equalTo("patientId" , patient!!.patientId).findAll()
            if (allBPITest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allBPiSize = allMDSTest.get(allBPITest.size -1)
                BPITestName = "Brief Pain Inv"
                BPITestDate = dateFormat.format(allBPiSize!!.testDate)
                var BPITestResult = "Brief Pain Inv - ${dateFormat.format(allBPiSize!!.testDate)}"
            }

            var allBDITest = realm.where(Test::class.java).isNotNull("patientBDIQ1").equalTo("patientId" , patient!!.patientId).findAll()

            if (allBDITest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allBDISize = allBDITest.get(allBDITest.size -1)
                BDITestName = "Beck Depression"
                BDITestDate = dateFormat.format(allBDISize!!.testDate)
                var BDITestResult = "Beck Depression - ${dateFormat.format(allBDISize!!.testDate)}"
            }

            var allGDSTest = realm.where(Test::class.java).isNotNull("patientGDSQ1").equalTo("patientId" , patient!!.patientId).findAll()

            if (allGDSTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allGDSSize = allGDSTest.get(allGDSTest.size -1)
                GDSTestName = "Geriatric Depression"
                GDSTestDate = dateFormat.format(allGDSSize!!.testDate)
                var GDSTestResult = "Geriatric Depression - ${dateFormat.format(allGDSSize!!.testDate)}"
            }

            var allSTAIOnTest = realm.where(Test::class.java).isNotNull("patientSTAISQ1").equalTo("patientId" , patient!!.patientId).findAll()

            if (allSTAIOnTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allSTAISize = allSTAIOnTest.get(allSTAIOnTest.size -1)
                STAITestName = "STAI"
                STAITestDate = dateFormat.format(allSTAISize!!.testDate)
                var STAITestResult = "STAI - ${dateFormat.format(allSTAISize!!.testDate)}"
            }

            var allHAMMILTOnTest = realm.where(Test::class.java).isNotNull("patientHAMDQ1").equalTo("patientId" , patient!!.patientId).findAll()

            if (allHAMMILTOnTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allHammiltonSize = allHAMMILTOnTest.get(allHAMMILTOnTest.size -1)
                HammiltonTestName = "Hammilton"
                HammiltonTestDate = dateFormat.format(allHammiltonSize!!.testDate)
                var HammiltonTestResult = "Hammilton - ${dateFormat.format(allHammiltonSize!!.testDate)}"
            }

            var allDASSTest = realm.where(Test::class.java).isNotNull("patientDASSQ1").equalTo("patientId" , patient!!.patientId).findAll()

            if (allDASSTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allDASSSize = allDASSTest.get(allDASSTest.size -1)
                DASSTestName = "Dass"
                DASSTestDate = dateFormat.format(allDASSSize!!.testDate)
                var DASSTestResult = "Dass - ${dateFormat.format(allDASSSize!!.testDate)}"
            }

            var allZUNGTest = realm.where(Test::class.java).isNotNull("patientZUNGQ1").equalTo("patientId" , patient!!.patientId).findAll()

            if (allZUNGTest.size > 0)
            {
                var dateFormat = SimpleDateFormat("MM/dd/yyyy")
                var allZUNGSize = allZUNGTest.get(allZUNGTest.size -1)
                ZungTestName = "ZUNG"
                ZungTestDate = dateFormat.format(allZUNGSize!!.testDate)
                var ZUNGTestResult = "ZUNG - ${dateFormat.format(allZUNGSize!!.testDate)}"
            }

//            allPatientsTestNameListView = findViewById(R.id.alltestsNameResultsistView)
//            allPatientTestDateLisView = findViewById(R.id.alltestsDatesResultsistView)

            if (CVDTestName != "")
            {
                allPatientTestNameData.add(CVDTestName)
                allPatientTestDateData.add(CVDTestDate)
                allPatientTestNames += CVDTestName +"\n"
                allPatientTestDates += CVDTestDate + "\n"
            }
            if (DiabetesTestName != "")
            {
                allPatientTestNameData.add(DiabetesTestName)
                allPatientTestDateData.add(DiabetesTestDate)
                allPatientTestNames += DiabetesTestName +"\n"
                allPatientTestDates += DiabetesTestDate + "\n"
            }
            if (MDITestName != "")
            {
                allPatientTestNameData.add(MDITestName)
                allPatientTestDateData.add(MDITestDate)
                allPatientTestNames += MDITestName +"\n"
                allPatientTestDates += MDITestDate + "\n"
            }
            if (BAITestName != "")
            {
                allPatientTestNameData.add(BAITestName)
                allPatientTestDateData.add(BAITestDate)
                allPatientTestNames += BAITestName +"\n"
                allPatientTestDates += BAITestDate + "\n"
            }
            if (BPITestName != "")
            {
                allPatientTestNameData.add(BPITestName)
                allPatientTestDateData.add(BPITestDate)
                allPatientTestNames += BPITestName +"\n"
                allPatientTestDates += BPITestDate + "\n"
            }
            if (MDTTestName != "")
            {
                allPatientTestNameData.add(MDTTestName)
                allPatientTestDateData.add(MDTTestDate)
                allPatientTestNames += MDTTestName +"\n"
                allPatientTestDates += MDTTestDate + "\n"
            }
            if (GDSTestName != "")
            {
                allPatientTestNameData.add(GDSTestName)
                allPatientTestDateData.add(GDSTestDate)
                allPatientTestNames += GDSTestName +"\n"
                allPatientTestDates += GDSTestDate + "\n"
            }
            if (BDITestName != "")
            {
                allPatientTestNameData.add(BDITestName)
                allPatientTestDateData.add(BDITestDate)
                allPatientTestNames += BDITestName +"\n"
                allPatientTestDates += BDITestDate + "\n"
            }
            if (STAITestName != "")
            {
                allPatientTestNameData.add(STAITestName)
                allPatientTestDateData.add(STAITestDate)
                allPatientTestNames += STAITestName +"\n"
                allPatientTestDates += STAITestDate + "\n"
            }
            if (HammiltonTestName != "")
            {
                allPatientTestNameData.add(HammiltonTestName)
                allPatientTestDateData.add(HammiltonTestDate)
                allPatientTestNames += HammiltonTestName +"\n"
                allPatientTestDates += HammiltonTestDate + "\n"
            }
            if (DASSTestName != "")
            {
                allPatientTestNameData.add(DASSTestName)
                allPatientTestDateData.add(DASSTestDate)
                allPatientTestNames += DASSTestName +"\n"
                allPatientTestDates += DASSTestDate + "\n"
            }
            if (ZungTestName != "")
            {
                allPatientTestNameData.add(ZungTestName)
                allPatientTestDateData.add(ZungTestDate)
                allPatientTestNames += ZungTestName +"\n"
                allPatientTestDates += ZungTestDate + "\n"
            }
        }


        runOnUiThread {
//            allPatienttestNameTextView.setText(allPatientTestNames)
//            allPatienttestDateTextView.setText(allPatientTestDates)
//          allPatienttestNameTextView.postInvalidate()
//            allPatienttestDateTextView.postInvalidate()
//            allPatienttestNameTextView.requestLayout()
//            allPatienttestDateTextView.requestLayout()
            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).invalidate()
            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).requestLayout()
            allPatientResultsPopUp.postInvalidate()
            allPatientResultsPopUp.requestLayout()
//            var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT)
//            layoutParams.weight = 1f
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).layoutParams = layoutParams
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).removeView(allPatienttestNameTextView)
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).removeView(allPatienttestDateTextView)
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).addView(allPatienttestNameTextView)
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).addView(allPatienttestDateTextView)
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).invalidate()
//            allPatientResultsPopUp.findViewById<LinearLayout>(R.id.patientTestsLinLayout).requestLayout()
        }
//        populateTextForPatientTestList(allPatientTestNameData , allPatientTestDateData)
//        allPatientTestData.add(BPITestResult)
//        allPatientTestData.add(GDSTestResult)
        when (testReturnType)
        {
            1 ->
                return allPatientTestNameData
            2 ->
                return allPatientTestDateData
        }
        return allPatientTestNameData
    }

    private fun populateTextForPatientTestList(allPatientTestNameData : ArrayList<String>, allPatientTestDateData : ArrayList<String>)
    {
        allPatientResultsLinLayout.removeAllViews()
        allPatientResultsLinLayout.invalidate()
        allPatientResultsLinLayout.requestLayout()
        var defaultFieldsLinLayout = LinearLayout(applicationContext)
        defaultFieldsLinLayout.orientation = LinearLayout.HORIZONTAL
        var testNamesTxtV = TextView(applicationContext)
        testNamesTxtV.gravity = Gravity.LEFT
        testNamesTxtV.textSize = 20f
        testNamesTxtV.setTextColor(Color.BLACK)
        testNamesTxtV.setText("Test Name")
        var testDatesTxtV = TextView(applicationContext)
        testDatesTxtV.gravity = Gravity.RIGHT
        testDatesTxtV.textSize = 20f
        testDatesTxtV.setTextColor(Color.BLACK)
        testDatesTxtV.setText("Test Date")
        defaultFieldsLinLayout.addView(testNamesTxtV)
        defaultFieldsLinLayout.addView(testDatesTxtV)
        allPatientResultsLinLayout.addView(defaultFieldsLinLayout)
        //populate the linear layout with views
        for (i in 0 until allPatientTestDateData.size)
        {
            var newLinearLayout = LinearLayout(applicationContext)
            newLinearLayout.orientation = LinearLayout.HORIZONTAL
            var dummyNameTxtView = TextView(applicationContext)
            dummyNameTxtView.gravity = Gravity.LEFT
            dummyNameTxtView.textSize = 16f
            dummyNameTxtView.setTextColor(Color.BLACK)
            dummyNameTxtView.setText(allPatientTestNameData.get(i))
            var dummyDateTxtView = TextView(applicationContext)
            dummyDateTxtView.gravity = Gravity.RIGHT
            dummyDateTxtView.textSize = 16f
            dummyDateTxtView.setTextColor(Color.BLACK)
            dummyDateTxtView.setText(allPatientTestDateData.get(i))
            newLinearLayout.addView(dummyNameTxtView)
            newLinearLayout.addView(dummyDateTxtView)
            allPatientResultsLinLayout.addView(newLinearLayout)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cvd -> {
                hideLayoutElements()
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentTransaction(checkFragment)
                return true
            }
            R.id.nav_diabetes ->
            {
                hideLayoutElements()
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentTransaction(checkDiabetesFragment)
                return true
            }
        }
        return true
    }




}