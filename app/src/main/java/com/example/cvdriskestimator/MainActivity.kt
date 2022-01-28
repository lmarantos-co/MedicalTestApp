package com.example.cvdriskestimator

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.cvdriskestimator.RealmDB.RealmDB
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.lang.reflect.Method


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var constraintLayout : ConstraintLayout
    private lateinit var realmDB: RealmDB
    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var checkFragment: CheckFragment
    private lateinit var checkDiabetesFragment : DiabetesCheckFragment
    private lateinit var mdiCheckFragment: MDICheckFragment
    private lateinit var popupMenu: PopupMenu
    private lateinit var MTETitleForm : RelativeLayout
    private  var mteTitleFormHeight : Int = 0
    private lateinit var userIconImg : ImageView
    private lateinit var companyLogo : ImageView
    private lateinit var appTitle : TextView
    private lateinit var signature : TextView
    private lateinit var termsConLayout : ConstraintLayout
    private lateinit var termsOFUseView : RelativeLayout
    private lateinit var termsCloseBtn : Button
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var MTETitle : ConstraintLayout
    private lateinit var fragContainerConstraintSet: ConstraintSet
    private lateinit var cvdVectorIcon : ImageView
    private lateinit var diabetesVectorIcon : ImageView
    private lateinit var depressionIcon : ImageView
    private lateinit var noseIcon : ImageView
    private lateinit var cvdTestTitle : TextView
    private lateinit var diabetestestTitle : TextView
    private lateinit var depressionTestTitle : TextView
    private lateinit var noseTestTitle : TextView
    private lateinit var animationZoomIn : Animation
    private lateinit var animationBounce : Animation
    private lateinit var animationSlideLeftToRight : Animation
    private lateinit var animationSlideRightToLeft : Animation
    private lateinit var animationSlideTopToBottom : Animation
    private lateinit var animationSlideBottomToTop : Animation
    private lateinit var animationShowTest : Animation
    private lateinit var animationHideTest : Animation
    private lateinit var bounceTests : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        noseIcon = findViewById(R.id.lungsImgV)
        cvdTestTitle = findViewById(R.id.cvdTestTxtView)
        diabetestestTitle = findViewById(R.id.diabetesTestTxtView)
        depressionTestTitle = findViewById(R.id.depressionTestTxtView)
        noseTestTitle = findViewById(R.id.lungsTestTxtView)


        initPrefs()

        initUI()

        initRealmDB()
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

        setFragmentContainerConstraint(2)

        showTermsOfUseLayout()

        hideTermsOfUseLayout()

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
            fragmentTransaction(checkFragment)
        }
        diabetesVectorIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(3)
            fragmentTransaction(checkDiabetesFragment)
        }

        depressionIcon.setOnClickListener {
            hideLayoutElements()
            playSelectTestAudio(4)
            fragmentTransaction(mdiCheckFragment)
        }

        showMedicalTests()

    }


    override fun onStart() {
        super.onStart()

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
        cvdVectorIcon.animate().alpha(1f).duration = 1200


        diabetesVectorIcon.animate().alpha(1f).duration = 1200
        depressionIcon.animate().alpha(1f).duration = 1200
        depressionIcon.startAnimation(bounceTests)
        cvdVectorIcon.startAnimation(bounceTests)
        diabetesVectorIcon.startAnimation(bounceTests)

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
        depressionIcon.visibility = View.INVISIBLE
        noseIcon.visibility = View.INVISIBLE
        cvdTestTitle.visibility = View.INVISIBLE
        diabetestestTitle.visibility = View.INVISIBLE
        depressionTestTitle.visibility = View.INVISIBLE
        noseTestTitle.visibility = View.INVISIBLE
        hideCVDTitleForm()
    }

    private fun showLayoutElements() {
        constraintLayout.visibility = View.VISIBLE
        MTETitle.visibility = View.VISIBLE
        cvdVectorIcon.visibility = View.VISIBLE
        diabetesVectorIcon.visibility = View.VISIBLE
        cvdTestTitle.visibility = View.VISIBLE
        diabetestestTitle.visibility = View.VISIBLE
        depressionTestTitle.visibility = View.VISIBLE
        noseTestTitle.visibility = View.VISIBLE
        noseIcon.visibility = View.VISIBLE
        depressionIcon.visibility = View.VISIBLE
        noseIcon.visibility = View.VISIBLE
        showCVDTitleForm()
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

    private fun showCVDTitleForm()
    {
        MTETitle.visibility = View.VISIBLE
    }

    private fun hideCVDTitleForm()
    {
        MTETitle.visibility = View.INVISIBLE

    }

    private fun initRealmDB() {
        realmDB = RealmDB(applicationContext)
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


    private fun hideFragmentVisibility()
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.hide(loginFragment)
        fragmentTransaction.hide(registerFragment)
        fragmentTransaction.hide(checkFragment)
        fragmentTransaction.hide(checkDiabetesFragment)
        fragmentTransaction.hide(mdiCheckFragment).commit()
    }


    fun fragmentTransaction(fragment : Fragment) {
        hideLayoutElements()
        setFragmentContainerConstraint(1)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment is CheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is DiabetesCheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is MDICheckFragment)
            fragmentTransaction.show(fragment)
        if (fragment is LoginFragment)
            fragmentTransaction.show(loginFragment)
        if (fragment is RegisterFragment)
            fragmentTransaction.show(registerFragment)


        fragmentTransaction.replace(R.id.fragmentContainer , fragment).addToBackStack("FRAGMENT").commit()
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
            R.id.terms_item -> {
                showTermsOfUseLayout()
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