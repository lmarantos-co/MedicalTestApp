package com.example.cvdriskestimator.CustomClasses

import android.content.Context
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
                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is RegisterFragment) || (prFragment is MDICheckFragment) || (prFragment is ResultFragment))
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
                    if ((prFragment is CheckFragment) || (prFragment is DiabetesCheckFragment) || (prFragment is LoginFragment) || (prFragment is MDICheckFragment) || (prFragment is ResultFragment))
                    {
                        prMainActivity.backToActivity()
                        prMainActivity.fragmentTransaction(prRegisterFragment!!)
                    }
                }
                R.id.user_item -> {

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
        prPopupmenu.menu.getItem(3).setTitle(prMainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser"))
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