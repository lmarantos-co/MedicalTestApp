package com.example.cvdriskestimator

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.cvdriskestimator.Fragments.*
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDB
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import io.realm.Realm
import java.lang.reflect.Method
import android.content.DialogInterface
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.core.view.get
import com.example.cvdriskestimator.RealmDB.Doctor
import io.realm.RealmList


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var constraintLayout : ConstraintLayout
    private lateinit var introScreenConLayout : ConstraintLayout
    private lateinit var allCustomersTxtV : TextView
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
    private lateinit var leaderBoardFragment: LeaderBoardFragment
    private lateinit var popupMenu: PopupMenu
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
    private lateinit var cvdVectorIcon : ImageView
    private lateinit var diabetesVectorIcon : ImageView
    private lateinit var depressionIcon : ImageView
    private lateinit var anxietyIcon : ImageView
    private lateinit var dietIcon : ImageView
    private lateinit var painIcon : ImageView
    private lateinit var gdsDepressionIcon : ImageView
    private lateinit var pdqIcon : ImageView
    private lateinit var cvdTestTitle : TextView
    private lateinit var diabetestestTitle : TextView
    private lateinit var depressionTestTitle : TextView
    private lateinit var anxietyTestTitle : TextView
    private lateinit var dietTestTitle : TextView
    private lateinit var painTestTitle : TextView
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

    private fun setContentViewForFirstAppScreen()
    {
        setContentView(R.layout.app_first_screen)

        initRealmDB()

        loginDoctorButton = findViewById(R.id.loginDoctorTxtV)
        loginDoctorButton.setOnClickListener {
            setContentViewForMainLayout()
            loginDoctorFragment = LoginDoctorFragment.newInstance()
            hideLayoutElements()
            fragmentTransaction(loginDoctorFragment)
        }

        registerDoctorButton = findViewById(R.id.registerDoctorTxtV)
        registerDoctorButton.setOnClickListener {
            setContentViewForMainLayout()
            registerDoctorFragment = RegisterDoctorFragment.newInstance()
            hideLayoutElements()
            fragmentTransaction(registerDoctorFragment)
        }
    }

    private fun setContentViewForIntroScreen()
    {

        setContentView(R.layout.initial_screen)

        allCustomersTxtV = findViewById<TextView>(R.id.allCustomersTxtV)
        allCustomersTxtV.setOnClickListener {
            setContentViewForSearchCustomersScreen()
        }

    }

    private fun setContentViewForSearchCustomersScreen() {
        setContentView(R.layout.intro_screen_search_customers)
        customersListView = findViewById(R.id.customerListView)
        customerSearchView = findViewById(R.id.customersSearchView)


        //get the doctor file from realm
        val doctorName = getPreferences(Context.MODE_PRIVATE).getString("doctorUserName" , "tempDoctor")
        var doctor = Doctor()
        var lastnames = ArrayList<String>()
        var realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            doctor= realm.where(Doctor::class.java).equalTo("doctorUserName" , doctorName).findFirst()!!
            //set a set of customer for the doctor
            for (i in 0..doctor!!.doctorCustomers!!.size -1)
            {
                lastnames.add(doctor!!.doctorCustomers!!.get(i)!!)
            }

            lastnames.apply {
                this.add("Papadopoulos")
                this.add("Marantos")
                this.add("Christodoulopoulou")
                this.add("zikos")
            }

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
                setContentViewForMainLayout()
            }
        }

    }


    private fun setContentViewForMainLayout()
    {
        setContentView(R.layout.activity_main)

        constraintLayout = findViewById(R.id.mainActiConLayout)
        fragmentContainer = findViewById(R.id.fragmentContainer)
        bottomNavigationView = findViewById(R.id.appBottomnavigationView)
        MTETitle = findViewById(R.id.include_cvd_title_form)
        MTETitleForm = MTETitle.findViewById(R.id.cvdTitleForm)
        userIconImg = MTETitle.findViewById(R.id.userIcon)
        companyLogo = MTETitle.findViewById(R.id.covariance_logo)
        appTitle = MTETitle.findViewById(R.id.cvdTitleTxtV)
        signature = MTETitle.findViewById(R.id.signature)
        termsConLayout = findViewById(R.id.include_popup_menu)
        termsOFUseView = termsConLayout.findViewById(R.id.termsRelLayout)
        termsCloseBtn = termsConLayout.findViewById(R.id.closeBtn)
        cvdVectorIcon = findViewById(R.id.cvdTestImgV)
        diabetesVectorIcon = findViewById(R.id.diabetesTestImgV)
        depressionIcon = findViewById(R.id.depressionImgV)
        anxietyIcon = findViewById(R.id.AnxietyImgV)
        dietIcon = findViewById(R.id.dietImgV)
        painIcon = findViewById(R.id.PainImgV)
        gdsDepressionIcon = findViewById(R.id.gdsIcon)
        pdqIcon = findViewById(R.id.pdqIcon)
        cvdTestTitle = findViewById(R.id.cvdTestTxtView)
        diabetestestTitle = findViewById(R.id.diabetesTestTxtView)
        depressionTestTitle = findViewById(R.id.depressionTestTxtView)
        anxietyTestTitle = findViewById(R.id.anxietyTestTxtView)
        dietTestTitle = findViewById(R.id.medDietScoreTxtView)
        painTestTitle = findViewById(R.id.painTxtView)

        initPrefs()

        initUI()
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

        MTETitleForm.post {
            mteTitleFormHeight = MTETitleForm.height
        }

        constraintLayout.setOnClickListener {
            if (termsOFUseView.visibility == View.VISIBLE)
            {
                termsOFUseView.visibility = View.INVISIBLE
            }
        }


        MTETitle.setOnClickListener {
            hideSoftInputKeyboard()
            hideFragmentVisibility()
            showLayoutElements()
            onBackPressed()
            playSelectTestAudio(1)
            showMedicalTests()
        }

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        checkFragment = CheckFragment.newInstance()
        checkDiabetesFragment = DiabetesCheckFragment.newInstance()
        mdiCheckFragment = MDICheckFragment.newInstance()
        baiCheckFragment = BAICheckFragment.newInstance()
        mdsCheckFragment = medDietTestFragment.newInstance()
        bpiCheckFragment = BPICheckFragment.newInstance()
        gdsCheckFragment = GDSCheckFragment.newInstance()
//        pdqCheckFragment = PDQCheckFirstCategoryFragment()
        var resultsArray = IntArray(8)
        resultsArray[0] = 40
        resultsArray[1] = 55
        resultsArray[2] = 70
        resultsArray[3] = 25
        resultsArray[4] = 75
        resultsArray[5] = 90
        resultsArray[6] = 15
        resultsArray[7] = 20
        pdqResultFragment = ResultExtraFragment.newInstance(resultsArray)
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        setFragmentContainerConstraint(2)

        showTermsOfUseLayout()

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


        cvdVectorIcon.visibility = View.INVISIBLE
        diabetesVectorIcon.visibility = View.INVISIBLE

        //set the animation for the check button
        animationZoomIn = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        animationBounce  = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)

        //set the animations for the tests rellayout
        animationShowTest = AnimationUtils.loadAnimation(applicationContext , R.anim.showtest)
        animationHideTest = AnimationUtils.loadAnimation(applicationContext, R.anim.hidetest)

        bounceTests = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce_tests)
        initTestsRelLayouts()


        //bounce the userIcon

        //set the animation for the testRelLayout slide
        animationSlideLeftToRight = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_left_to_right)
        animationSlideRightToLeft = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_right_to_left)
        animationSlideTopToBottom = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_top_to_bottom)

        animationSlideBottomToTop = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_bottom_to_top)

    }

    private fun initPrefs()
    {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("LOG" , "Login")
            putString("MSG" , "Medical Test Estimator")
            if (sharedPref.getString("userName", "tempUser") == "tempUser")
                putString("userName" , "tempUser")
            apply()
        }
    }


    private fun showTermsOfUseLayout() {

        termsOFUseView.visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        termsOFUseView.visibility = View.INVISIBLE
    }

    private fun initTestsRelLayouts()
    {
        cvdVectorIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(2)
            checkFragment = CheckFragment.newInstance()
            fragmentTransaction(checkFragment)
        }
        diabetesVectorIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(3)
            checkDiabetesFragment = DiabetesCheckFragment.newInstance()
            fragmentTransaction(checkDiabetesFragment)
        }

        depressionIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(4)
            mdiCheckFragment = MDICheckFragment.newInstance()
            fragmentTransaction(mdiCheckFragment)
        }

        anxietyIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(5)
            baiCheckFragment = BAICheckFragment.newInstance()
            fragmentTransaction(baiCheckFragment)
        }

        dietIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(6)
            fragmentTransaction(mdsCheckFragment)
        }

        painIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(7)
            fragmentTransaction(bpiCheckFragment)
        }

        gdsDepressionIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(8)
            fragmentTransaction(gdsCheckFragment)
        }

        pdqIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(9)
            fragmentTransaction(pdqResultFragment)
        }

        showMedicalTests()

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
            putString("LOG" , "Login")
            putString("userName" , "tempUser")
            apply()
        }
        val message = shared.getString("MSG" , "Test 1")
        val login = shared.getString("LOG" , "Test 2")
        val popUpmenu = popupMenu.menu
        popUpmenu.getItem(1).setTitle("login")
        //set register button message
        popUpmenu.getItem(3).setTitle("tempUser Data")

//        enableRegisterButton()
//        disableCheckButton()
    }

    fun backToActivity() {
        onBackPressed()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        fragmentContainer.setBackgroundColor(getColor(R.color.MidnightBlue))
        fragmentContainer.visibility = View.VISIBLE
        hideFragmentVisibility()
        setFragmentContainerConstraint(2)
        supportFragmentManager.popBackStack()
        showLayoutElements()
    }

    private fun setFragmentContainerConstraint(action : Int)
    {
        if (action == 1)
        {
            fragContainerConstraintSet = ConstraintSet()
            fragContainerConstraintSet.clone(constraintLayout)
            fragContainerConstraintSet.connect(fragmentContainer.id , ConstraintSet.TOP , ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            fragContainerConstraintSet.applyTo(constraintLayout)
        }
        if (action == 2)
        {
            fragContainerConstraintSet = ConstraintSet()
            fragContainerConstraintSet.clone(constraintLayout)
            fragContainerConstraintSet.connect(fragmentContainer.id , ConstraintSet.TOP , R.id.horGL1, ConstraintSet.BOTTOM)
            fragContainerConstraintSet.applyTo(constraintLayout)
        }
    }

    private fun showMedicalTests() {
        cvdVectorIcon.visibility = View.VISIBLE
        diabetesVectorIcon.visibility = View.VISIBLE
        depressionIcon.visibility = View.VISIBLE
        anxietyIcon.visibility = View.VISIBLE
        dietIcon.visibility = View.VISIBLE
        painIcon.visibility = View.VISIBLE
        gdsDepressionIcon.visibility = View.VISIBLE
        cvdVectorIcon.animate().alpha(1f).duration = 1200
        diabetesVectorIcon.animate().alpha(1f).duration = 1200
        depressionIcon.animate().alpha(1f).duration = 1200
        dietIcon.animate().alpha(1f).duration = 1200
        depressionIcon.startAnimation(bounceTests)
        anxietyIcon.animate().alphaBy(1f).duration = 1200
        anxietyIcon.startAnimation(bounceTests)
        cvdVectorIcon.startAnimation(bounceTests)
        diabetesVectorIcon.startAnimation(bounceTests)
        dietIcon.startAnimation(bounceTests)
        painTestTitle.animate().alphaBy(1f).duration = 1200
        painIcon.startAnimation(bounceTests)
        gdsDepressionIcon.animate().alphaBy(1f).duration = 1200
        gdsDepressionIcon.startAnimation(bounceTests)
        pdqIcon.animate().alphaBy(1f).duration = 1200
        pdqIcon.startAnimation(bounceTests)

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
        dietIcon.clearAnimation()
        dietIcon.visibility = View.INVISIBLE
        painIcon.clearAnimation()
        painIcon.visibility = View.INVISIBLE
        gdsDepressionIcon.visibility = View.INVISIBLE
        pdqIcon.visibility = View.INVISIBLE
        cvdTestTitle.visibility = View.INVISIBLE
        diabetestestTitle.visibility = View.INVISIBLE
        depressionTestTitle.visibility = View.INVISIBLE
        anxietyTestTitle.visibility = View.INVISIBLE
        dietTestTitle.visibility = View.INVISIBLE
        painTestTitle.visibility = View.INVISIBLE

    }

    private fun showLayoutElements() {
        MTETitle.visibility = View.VISIBLE
        cvdVectorIcon.visibility = View.VISIBLE
        diabetesVectorIcon.visibility = View.VISIBLE
        cvdTestTitle.visibility = View.VISIBLE
        diabetestestTitle.visibility = View.VISIBLE
        depressionTestTitle.visibility = View.VISIBLE
        anxietyTestTitle.visibility = View.VISIBLE
        dietTestTitle.visibility = View.VISIBLE
        painTestTitle.visibility = View.VISIBLE
        anxietyIcon.visibility = View.VISIBLE
        depressionIcon.clearAnimation()
        depressionIcon.visibility = View.VISIBLE
        anxietyIcon.visibility = View.VISIBLE
        dietIcon.visibility = View.VISIBLE
        painIcon.visibility = View.VISIBLE
        gdsDepressionIcon.visibility = View.VISIBLE
        pdqIcon.visibility = View.VISIBLE
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
        if (patientCount < 10)
            buildRealmDatabase()

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
        patient1.id = 1.toString()
        patient1.userName = "lampros_1"
        patient1.password = "lampros#1"
        patient1.patientName = "Lampros"
        patient1.patientLastName = "Marantos"
        patient1.dateOfBirth = "1983 04 01"
        patient1.occupation = "Programmer"
        patient1.yearsOfApprentice = 2

        var patient2 = Patient()
        patient2.id = 2.toString()
        patient2.userName = "vaslis_1"
        patient2.password = "vasilis#1"
        patient2.patientName = "Vasilis"
        patient2.patientLastName = "Marantos"
        patient2.dateOfBirth = "1981 05 11"
        patient2.occupation = "Logistics"
        patient2.yearsOfApprentice = 4

        var patient3 = Patient()
        patient3.id = 3.toString()
        patient3.userName = "george_1"
        patient3.password = "george#1"
        patient3.patientName = "George"
        patient3.patientLastName = "Papadopoulos"
        patient3.dateOfBirth = "1975 02 17"
        patient3.occupation = "Student"
        patient3.yearsOfApprentice = 5

        var patient4 = Patient()
        patient4.id = 4.toString()
        patient4.userName = "nick_1"
        patient4.password = "nick#1"
        patient4.patientName = "Nick"
        patient4.patientLastName = "Melanitis"
        patient4.dateOfBirth = "1970 01 10"
        patient4.occupation = "Freelancer"
        patient4.yearsOfApprentice = 3

        var patient5 = Patient()
        patient5.id = 5.toString()
        patient5.userName = "dimitris_2"
        patient5.password = "dimitris#2"
        patient5.patientName = "Dimitris"
        patient5.patientLastName = "Zikos"
        patient5.dateOfBirth = "1964 12 10"
        patient5.occupation = "Freelancer"
        patient5.yearsOfApprentice = 3

        var patient6 = Patient()
        patient6.id = 6.toString()
        patient6.userName = "elena_1"
        patient6.password = "elena#1"
        patient6.patientName = "Elena"
        patient6.patientLastName = "Christodoulopoulou"
        patient6.dateOfBirth = "1982 08 10"
        patient6.occupation = "Accountant"
        patient6.yearsOfApprentice = 7

        var patient7 = Patient()
        patient7.id = 7.toString()
        patient7.userName = "dimitris_1"
        patient7.password = "dimitris#1"
        patient7.patientName = "Dimitris"
        patient7.patientLastName = "Marantos"
        patient7.dateOfBirth = "1984 11 15"
        patient7.occupation = "Driver"
        patient7.yearsOfApprentice = 6

        var patient8 = Patient()
        patient8.id = 8.toString()
        patient8.userName = "kostas_1"
        patient8.password = "kostas#1"
        patient8.patientName = "Kostas"
        patient8.patientLastName = "Nikolaou"
        patient8.dateOfBirth = "1972 03 09"
        patient8.occupation = "singer"
        patient8.yearsOfApprentice = 10

        var patient9 = Patient()
        patient9.id = 9.toString()
        patient9.userName = "petros_1"
        patient9.password = "petros#1"
        patient9.patientName = "Petros"
        patient9.patientLastName = "zikos"
        patient9.dateOfBirth = "1968 05 17"
        patient9.occupation = "Financial Manager"
        patient9.yearsOfApprentice = 4

        var patient10 = Patient()
        patient10.id = 10.toString()
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
//        fragmentTransaction.hide(pdqCheckFragment)
        fragmentTransaction.hide(leaderBoardFragment)
            .commit()
    }


    fun fragmentTransaction(fragment : Fragment) {
        if (!(fragment is LoginDoctorFragment) && !(fragment is RegisterDoctorFragment))
            hideLayoutElements()
        setFragmentContainerConstraint(1)
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
        if (fragment is LeaderBoardFragment)
            fragmentTransaction.show(fragment)

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
                }R.id.login_item -> {
                    val prefs = getPreferences(Context.MODE_PRIVATE)
                    val message = prefs.getString("LOG", "Test1")
                    if (message == "Login") {
                        hideLayoutElements()
                        fragmentTransaction(loginFragment)
                } else
                    logOutUser()
            }
                R.id.register_item -> {
                    hideLayoutElements()
                    fragmentTransaction(registerFragment)
            }
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
                                //initaalize all patient data fields
                                patient!!.patientAge = ""
                                patient!!.smoker = ""
                                patient!!.patientSiblings = ""
                                patient!!.patientBMI = ""
                                patient!!.patientSex = ""
                                patient!!.patientSteroids = ""
                                patient!!.patientPAM = ""
                                patient!!.SSB = ""
                                patient!!.HDL = ""
                                patient!!.TCH = ""
                                patient!!.treatment = ""
                                patient!!.patientRace = ""

                                patient!!.patientMDIQ1 = null
                                patient!!.patientMDIQ2 = null
                                patient!!.patientMDIQ3 = null
                                patient!!.patientMDIQ5 = null
                                patient!!.patientMDIQ6 = null
                                patient!!.patientMDIQ7 = null
                                patient!!.patientMDIQ8 = null
                                patient!!.patientMDIQ5 = null
                                patient!!.patientMDIQ10 = null
                                patient!!.patientMDIQ11 = null
                                patient!!.patientMDIQ12 = null
                                patient!!.patientMDIQ13 = null

                                patient!!.patientBAIQ1 = null
                                patient!!.patientBAIQ2 = null
                                patient!!.patientBAIQ3 = null
                                patient!!.patientBAIQ4 = null
                                patient!!.patientBAIQ5 = null
                                patient!!.patientBAIQ6 = null
                                patient!!.patientBAIQ7 = null
                                patient!!.patientBAIQ8 = null
                                patient!!.patientBAIQ9 = null
                                patient!!.patientBAIQ10 = null
                                patient!!.patientBAIQ11 = null
                                patient!!.patientBAIQ12 = null
                                patient!!.patientBAIQ13 = null
                                patient!!.patientBAIQ14 = null
                                patient!!.patientBAIQ15 = null
                                patient!!.patientBAIQ16 = null
                                patient!!.patientBAIQ17 = null
                                patient!!.patientBAIQ18 = null
                                patient!!.patientBAIQ19 = null
                                patient!!.patientBAIQ20 = null
                                patient!!.patientBAIQ21 = null

                                patient!!.patientGDSQ1 = null
                                patient!!.patientGDSQ2 = null
                                patient!!.patientGDSQ3 = null
                                patient!!.patientGDSQ4 = null
                                patient!!.patientGDSQ5 = null
                                patient!!.patientGDSQ6 = null
                                patient!!.patientGDSQ7 = null
                                patient!!.patientGDSQ8 = null
                                patient!!.patientGDSQ9 = null
                                patient!!.patientGDSQ10 = null
                                patient!!.patientGDSQ11 = null
                                patient!!.patientGDSQ12 = null
                                patient!!.patientGDSQ13 = null
                                patient!!.patientGDSQ14 = null
                                patient!!.patientGDSQ15 = null

                                patient!!.patientPDQQ1 = null
                                patient!!.patientPDQQ2 = null
                                patient!!.patientPDQQ3 = null
                                patient!!.patientPDQQ4 = null
                                patient!!.patientPDQQ5 = null
                                patient!!.patientPDQQ6 = null
                                patient!!.patientPDQQ7 = null
                                patient!!.patientPDQQ8 = null
                                patient!!.patientPDQQ9 = null
                                patient!!.patientPDQQ10 = null
                                patient!!.patientPDQQ11 = null
                                patient!!.patientPDQQ12 = null
                                patient!!.patientPDQQ13 = null
                                patient!!.patientPDQQ14 = null
                                patient!!.patientPDQQ15 = null
                                patient!!.patientPDQQ16 = null
                                patient!!.patientPDQQ17 = null
                                patient!!.patientPDQQ18 = null
                                patient!!.patientPDQQ19 = null
                                patient!!.patientPDQQ20 = null
                                patient!!.patientPDQQ21 = null
                                patient!!.patientPDQQ22 = null
                                patient!!.patientPDQQ23 = null
                                patient!!.patientPDQQ24 = null
                                patient!!.patientPDQQ25 = null
                                patient!!.patientPDQQ26 = null
                                patient!!.patientPDQQ27 = null
                                patient!!.patientPDQQ28 = null
                                patient!!.patientPDQQ30 = null
                                patient!!.patientPDQQ31 = null
                                patient!!.patientPDQQ32 = null
                                patient!!.patientPDQQ33 = null
                                patient!!.patientPDQQ34 = null
                                patient!!.patientPDQQ35 = null
                                patient!!.patientPDQQ36 = null
                                patient!!.patientPDQQ37 = null
                                patient!!.patientPDQQ38 = null
                                patient!!.patientPDQQ39 = null
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
        popupMenu.menu.getItem(1).title =
            this.getPreferences(Context.MODE_PRIVATE).getString("LOG" , "Login")
        popupMenu.menu.getItem(3).title =
            this.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser") + " data"
        //aplly font to all menu item
        applyFontToMenuItem(popupMenu.menu.getItem(0) , "Home" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(1) , this.getPreferences(Context.MODE_PRIVATE).getString("LOG" , "Login")!! , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(2) , "Register" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(3) , this.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser") + " data" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(4) , "Clear All Data" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(5) , "Terms Of Use" , applicationContext )
        applyFontToMenuItem(popupMenu.menu.getItem(6) ,"LeaderBoard" , applicationContext)


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

    //function to apply font family to menu items
    private fun applyFontToMenuItem(menuItem: MenuItem, menuTitle : String, mContext : Context)
    {
        val typeface = Typeface.create("sans-serif",Typeface.NORMAL) //  THIS
        var string  = SpannableString(menuTitle)
        string.setSpan(typeface, 0, menuTitle.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuItem.title = menuTitle
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