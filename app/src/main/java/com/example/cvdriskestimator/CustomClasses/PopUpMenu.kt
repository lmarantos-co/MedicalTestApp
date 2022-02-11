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
    //variables to set the menu item titles
    var LOG : String = "Login"
    var USER : String =  "tempUser Data"
    private lateinit var prPopupmenu: PopupMenu
    private lateinit var realm : Realm

    constructor(termsOfUse : RelativeLayout, mainActivity: MainActivity, fragment: Fragment, loginFragment: LoginFragment?, registerFragment: RegisterFragment?)
    {
        prTermsOfUse = termsOfUse
        prMainActivity = mainActivity
        prFragment = fragment
        prLoginFragment = loginFragment
        prRegisterFragment = registerFragment
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
                R.id.login_item -> {
                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is RegisterFragment) || (prFragment is MDICheckFragment) || (prFragment is BAICheckFragment) ||(prFragment is ResultFragment))
                    {
                        val prefs = prMainActivity.getPreferences(Context.MODE_PRIVATE)
                        val message = prefs.getString("LOG", "Test1")
                        if (message == "Login") {
                            prMainActivity.backToActivity()
                            prMainActivity.fragmentTransaction(prLoginFragment!!)
                        } else
                            prMainActivity.logOutUser()
                    }

                }
                R.id.register_item -> {
                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is LoginFragment) || (prFragment is MDICheckFragment) || (prFragment is BAICheckFragment) || (prFragment is ResultFragment))
                    {
                        prMainActivity.backToActivity()
                        prMainActivity.fragmentTransaction(prRegisterFragment!!)
                    }
                }
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

                            }) // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()

                }
                R.id.terms_item -> {
                    showTermsOfUseLayout()
                }
            }
            false
        }
        val inflater : MenuInflater = prPopupmenu.menuInflater
        inflater.inflate(R.menu.main_optionss_menu , prPopupmenu.menu)
        prPopupmenu.menu.getItem(1).setTitle(prMainActivity.getPreferences(Context.MODE_PRIVATE).getString("LOG" , "Login"))
        prPopupmenu.menu.getItem(3).setTitle(prMainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser") + " data")
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