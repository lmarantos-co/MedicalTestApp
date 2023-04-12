package com.example.cvdriskestimator.CustomClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.ContextThemeWrapper
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.cvdriskestimator.Fragments.*
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import io.realm.Realm
import java.lang.reflect.Method

class PopUpMenu {

    private var prTermsOfUse : RelativeLayout
    private var prMainActivity : MainActivity
    private var prFragment : Fragment
    private var prLoginFragment : LoginFragment?
    private var prRegisterFragment : RegisterFragment?
    private var prRegisterDoctorFragment : RegisterDoctorFragment? = null
    private var prleaderBoardFragment : LeaderBoardFragment?
    //variables to set the menu item titles
    var LOG : String = "Login"
    var USER : String =  "Δεδομένα Χρήστη"
    private lateinit var prPopupmenu: PopupMenu
    private lateinit var realm : Realm

    constructor(termsOfUse : RelativeLayout, mainActivity: MainActivity, fragment: Fragment, loginFragment: LoginFragment?, registerFragment: RegisterFragment?, registerDoctorFragment : RegisterDoctorFragment? ,leaderBoardFragment: LeaderBoardFragment?)
    {
        prTermsOfUse = termsOfUse
        prMainActivity = mainActivity
        prFragment = fragment
        prLoginFragment = loginFragment
        prRegisterFragment = registerFragment
        prRegisterDoctorFragment = registerDoctorFragment
        prleaderBoardFragment = leaderBoardFragment
    }

    //behavioral code related with Popup menu
    fun showPopUp(v : View)
    {
        prPopupmenu = PopupMenu(ContextThemeWrapper(prMainActivity.applicationContext, R.style.PopupMenu), v)

        //set On MenuItemClickListener for the Popup menu
        prPopupmenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.home_item -> {
                    prMainActivity.playSelectTestAudio(1)
                    prMainActivity.backToActivity()
                }
                R.id.questions ->
                {
                    prMainActivity.backToActivity()
                }
                R.id.doctor_item ->
                {
                    prMainActivity.logOutDoctor()
                }
                R.id.customer_change_item -> {
                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is RegisterFragment) || (prFragment is MDICheckFragment) || (prFragment is BAICheckFragment) || (prFragment is medDietTestFragment) || (prFragment is BPICheckFragment) || (prFragment is BeckDepressionInventoryFragment) || (prFragment is HamiltonDepressionFragment) || (prFragment is GDSCheckFragment)|| (prFragment is STAICheckFragment) || (prFragment is CheckZUNGFragment)  ||(prFragment is ResultFragment) || (prleaderBoardFragment is LeaderBoardFragment))
                    {
                        val prefs = prMainActivity.getPreferences(Context.MODE_PRIVATE)
                        val message = prefs.getString("LOG", "Test1")
                        if (message == "Αλλαγή ασθενούς") {
                            prMainActivity.setContentViewForSearchCustomersScreen()
                        } else
                            prMainActivity.logOutUser()
                    }

                }
                R.id.customer_add_item -> {
                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is LoginFragment) || (prFragment is MDICheckFragment) || (prFragment is BAICheckFragment) || (prFragment is medDietTestFragment) || (prFragment is BPICheckFragment) || (prFragment is LeaderBoardFragment) || (prFragment is ResultFragment))
                    {
                        prMainActivity.backToActivity()
                        prMainActivity.fragmentTransaction(prRegisterFragment!!)
                    }
                }
//                R.id.doctor_logout_item ->
//                {
//                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is LoginFragment) || (prFragment is MDICheckFragment) || (prFragment is BAICheckFragment) || (prFragment is medDietTestFragment) || (prFragment is BPICheckFragment) || (prFragment is LeaderBoardFragment) || (prFragment is ResultFragment))
//                    {
//                        prMainActivity.backToActivity()
//                        prMainActivity.logOutDoctor()
//                    }
//                }
                R.id.user_item -> {

                }
                R.id.clear_data ->
                {

                    AlertDialog.Builder(prFragment.activity)
                        .setTitle("Clear All Data")
                        .setMessage("Are you sure you want to delete the user data?") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes,
                            DialogInterface.OnClickListener { dialog, which ->
                                // Continue with delete operation

                                Realm.init(prMainActivity.applicationContext)
                                realm = Realm.getDefaultInstance()
                                realm.executeTransaction {
                                    val username = prMainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
                                    val patient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , username).findFirst()
                                    //initaalize all patient data fields
//                                    patient!!.patientAge = ""
//                                    patient!!.smoker = ""
//                                    patient!!.patientSiblings = ""
//                                    patient!!.patientBMI = ""
//                                    patient!!.patientSex = ""
//                                    patient!!.patientSteroids = ""
//                                    patient!!.patientPAM = ""
//                                    patient!!.SSB = ""
//                                    patient!!.HDL = ""
//                                    patient!!.TCH = ""
//                                    patient!!.treatment = ""
//                                    patient!!.patientRace = ""
//
//                                    patient!!.patientMDIQ1 = null
//                                    patient!!.patientMDIQ2 = null
//                                    patient!!.patientMDIQ3 = null
//                                    patient!!.patientMDIQ5 = null
//                                    patient!!.patientMDIQ6 = null
//                                    patient!!.patientMDIQ7 = null
//                                    patient!!.patientMDIQ8 = null
//                                    patient!!.patientMDIQ5 = null
//                                    patient!!.patientMDIQ10 = null
//                                    patient!!.patientMDIQ11 = null
//                                    patient!!.patientMDIQ12 = null
//                                    patient!!.patientMDIQ13 = null
//
//                                    patient!!.patientBAIQ1 = null
//                                    patient!!.patientBAIQ2 = null
//                                    patient!!.patientBAIQ3 = null
//                                    patient!!.patientBAIQ4 = null
//                                    patient!!.patientBAIQ5 = null
//                                    patient!!.patientBAIQ6 = null
//                                    patient!!.patientBAIQ7 = null
//                                    patient!!.patientBAIQ8 = null
//                                    patient!!.patientBAIQ9 = null
//                                    patient!!.patientBAIQ10 = null
//                                    patient!!.patientBAIQ11 = null
//                                    patient!!.patientBAIQ12 = null
//                                    patient!!.patientBAIQ13 = null
//                                    patient!!.patientBAIQ14 = null
//                                    patient!!.patientBAIQ15 = null
//                                    patient!!.patientBAIQ16 = null
//                                    patient!!.patientBAIQ17 = null
//                                    patient!!.patientBAIQ18 = null
//                                    patient!!.patientBAIQ19 = null
//                                    patient!!.patientBAIQ20 = null
//                                    patient!!.patientBAIQ21 = null
//
//                                    patient!!.patientMDSQ1 = null
//                                    patient!!.patientMDSQ2 = null
//                                    patient!!.patientMDSQ3 = null
//                                    patient!!.patientMDSQ4 = null
//                                    patient!!.patientMDSQ5 = null
//                                    patient!!.patientMDSQ6 = null
//                                    patient!!.patientMDSQ7 = null
//                                    patient!!.patientMDSQ8 = null
//                                    patient!!.patientMDSQ9 = null
//                                    patient!!.patientMDSQ10 = null
//                                    patient!!.patientMDSQ11 = null
//
//                                    patient!!.patientBPIQ1 = null
//                                    patient!!.patientBPIQ2= null
//                                    patient!!.patientBPIQ3 = null
//                                    patient!!.patientBPIQ4 = null
//                                    patient!!.patientBPIQ5 = null
//                                    patient!!.patientBPIQ6 = null
//                                    patient!!.patientBPIQ7 = null
//                                    patient!!.patientBPIQ8 = null
//                                    patient!!.patientBPIQ9 = null
//                                    patient!!.patientBPIQ10 = null
//                                    patient!!.patientBPIQ11 = null
//                                    patient!!.patientBPIQ12 = null
//
//                                    patient!!.patientGDSQ1 = null
//                                    patient!!.patientGDSQ2 = null
//                                    patient!!.patientGDSQ3 = null
//                                    patient!!.patientGDSQ4 = null
//                                    patient!!.patientGDSQ5 = null
//                                    patient!!.patientGDSQ6 = null
//                                    patient!!.patientGDSQ2 = null
//                                    patient!!.patientGDSQ8 = null
//                                    patient!!.patientGDSQ9 = null
//                                    patient!!.patientGDSQ10 = null
//                                    patient!!.patientGDSQ11 = null
//                                    patient!!.patientGDSQ12 = null
//                                    patient!!.patientGDSQ13 = null
//                                    patient!!.patientGDSQ14 = null
//                                    patient!!.patientGDSQ15 = null
//
//                                    patient!!.patientPDQQ1 = null
//                                    patient!!.patientPDQQ2 = null
//                                    patient!!.patientPDQQ3 = null
//                                    patient!!.patientPDQQ4 = null
//                                    patient!!.patientPDQQ5 = null
//                                    patient!!.patientPDQQ6 = null
//                                    patient!!.patientPDQQ7 = null
//                                    patient!!.patientPDQQ8 = null
//                                    patient!!.patientPDQQ9 = null
//                                    patient!!.patientPDQQ10 = null
//                                    patient!!.patientPDQQ11 = null
//                                    patient!!.patientPDQQ12 = null
//                                    patient!!.patientPDQQ13 = null
//                                    patient!!.patientPDQQ14 = null
//                                    patient!!.patientPDQQ15 = null
//                                    patient!!.patientPDQQ16 = null
//                                    patient!!.patientPDQQ17 = null
//                                    patient!!.patientPDQQ18 = null
//                                    patient!!.patientPDQQ19 = null
//                                    patient!!.patientPDQQ20 = null
//                                    patient!!.patientPDQQ21 = null
//                                    patient!!.patientPDQQ22 = null
//                                    patient!!.patientPDQQ23 = null
//                                    patient!!.patientPDQQ24 = null
//                                    patient!!.patientPDQQ25 = null
//                                    patient!!.patientPDQQ26 = null
//                                    patient!!.patientPDQQ27 = null
//                                    patient!!.patientPDQQ28 = null
//                                    patient!!.patientPDQQ29 = null
//                                    patient!!.patientPDQQ30 = null
//                                    patient!!.patientPDQQ31 = null
//                                    patient!!.patientPDQQ32 = null
//                                    patient!!.patientPDQQ33 = null
//                                    patient!!.patientPDQQ34 = null
//                                    patient!!.patientPDQQ35 = null
//                                    patient!!.patientPDQQ36 = null
//                                    patient!!.patientPDQQ37 = null
//                                    patient!!.patientPDQQ38 = null
//                                    patient!!.patientPDQQ39 = null
                                }

                                if (prFragment is CheckFragment)
                                {
                                    (prFragment as CheckFragment).initPatientData()
                                }

                                if (prFragment is DiabetesCheckFragment)
                                {
                                    (prFragment as DiabetesCheckFragment).initPatientData()
                                }

                                if (prFragment is MDICheckFragment)
                                {
                                    (prFragment as MDICheckFragment).initialisePatientData()
                                }

                                if (prFragment is BAICheckFragment)
                                {
                                    (prFragment as BAICheckFragment).initialisePatientData()
                                }

                                if (prFragment is medDietTestFragment)
                                {
                                    (prFragment as medDietTestFragment).initialisePatientData()
                                }


                                if (prFragment is GDSCheckFragment)
                                {
                                    (prFragment as GDSCheckFragment).initialisePatientData()
                                }

                                if (prFragment is HamiltonDepressionFragment)
                                {
                                    (prFragment as HamiltonDepressionFragment).initialisePatientData()
                                }

                                if (prFragment is HamiltonDepressionFragment)
                                {
                                    (prFragment as HamiltonDepressionFragment).initialisePatientData()
                                }

                                if (prFragment is STAICheckFragment)
                                {
                                    (prFragment as STAICheckFragment).initialisePatientData()
                                }

                                if (prFragment is CheckZUNGFragment)
                                {
                                    (prFragment as CheckZUNGFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckFirstCategoryFragment)
                                {
                                    (prFragment as PDQCheckFirstCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckSecondCategoryFragment)
                                {
                                    (prFragment as PDQCheckSecondCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckThridCategoryFragment)
                                {
                                    (prFragment as PDQCheckThridCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckFourthCategoryFragment)
                                {
                                    (prFragment as PDQCheckFourthCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckFifthCategoryFragment)
                                {
                                    (prFragment as PDQCheckFifthCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckSixthCategoryFragment)
                                {
                                    (prFragment as PDQCheckSixthCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckSeventhCategoryFragment)
                                {
                                    (prFragment as PDQCheckSeventhCategoryFragment).initialisePatientData()
                                }

                                if (prFragment is PDQCheckEightCategoryFragment)
                                {
                                    (prFragment as PDQCheckEightCategoryFragment).initialisePatientData()
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
                    prMainActivity.backToActivity()
                    prMainActivity.fragmentTransaction(prleaderBoardFragment!!)
                }
            }
            false
        }
        val inflater : MenuInflater = prPopupmenu.menuInflater
        inflater.inflate(R.menu.main_optionss_menu , prPopupmenu.menu)
        prPopupmenu.menu.getItem(3).setTitle(prMainActivity.getPreferences(Context.MODE_PRIVATE).getString("LOG" , "Αλλαγή ασθενούς"))
        prPopupmenu.menu.getItem(4).setTitle(prMainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser") + " δεδομένα")
        // show icons on popup menu
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            prPopupmenu.setForceShowIcon(true)
        }else{
            try {
                val fields = prPopupmenu.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field[prPopupmenu]
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
        prPopupmenu.show()
    }

    private fun showTermsOfUseLayout() {

        prTermsOfUse.visibility = View.VISIBLE
    }

}