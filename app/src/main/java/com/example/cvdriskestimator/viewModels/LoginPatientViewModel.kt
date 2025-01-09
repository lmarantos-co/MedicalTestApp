package com.example.cvdriskestimator.viewModels

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cvdriskestimator.Fragments.LoginDoctorFragment
import com.example.cvdriskestimator.Fragments.LoginPatientFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Doctor
import com.example.cvdriskestimator.RealmDB.Patient
import io.realm.Realm
import java.util.regex.Pattern

class LoginPatientViewModel () : ViewModel() , Observable {

    private lateinit var loginPatientFragment: LoginPatientFragment
    private lateinit var mainActivity : MainActivity
    private var patientName : String = ""
    private var patientPassword : String = ""

    // defining our own password pattern
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{5,}" +  // at least 4 characters
                "$"
    )

    @Bindable
    val inputUserName = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    fun setFragment(logPatientFragment: LoginPatientFragment)
    {
        loginPatientFragment = logPatientFragment
    }

    fun setActivity(activity: MainActivity)
    {
        mainActivity = activity
    }


    fun loginUser()
    {
        retrieveUser(inputUserName.value!!)
    }

    private fun retrieveUser(userName : String)
    {
        val realm = Realm.getDefaultInstance()
        var correctUserData = true
        //check for null pointer
        patientName = inputUserName.value!!
        patientPassword = inputPassword.value!!
        correctUserData = correctUserData && validateUserName(inputUserName.value!!)
        correctUserData = correctUserData && validatePassword(inputPassword.value!!)
        if (correctUserData)
        {
            val patient: Patient? = realm.where(Patient::class.java).equalTo("password", patientPassword).findFirst()
            if (patient != null) {
                if (patient.password != patientPassword)
                {
                    loginPatientFragment.passwordError(mainActivity.resources.getString(R.string.patient_not_in_db))
                }
                else
                {
//                    mainActivity.setLoginItemTitle()
                    //set the doctor username in the shared prefs
                    val prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putString("userName" , patientName)
//                        putString("doctorPasword" , doctorPassword)
//                        putString("userName", "tempUser")
                        apply()
                    }
                    hideSoftInputKeyboard()
                    mainActivity.setContentViewForMainLayout(true)
                }
            }
            else
            {
                loginPatientFragment.passwordError(mainActivity.resources.getString(R.string.doctor_not_in_db))
            }
        }
    }

    fun hideSoftInputKeyboard()
    {
        val imm = mainActivity.applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    private fun validateUserName(name : String) : Boolean {
        val correctName : Boolean
        if (name.isNotEmpty())
        {
            correctName = true
        }
        else
        {
            correctName = false
            loginPatientFragment.userNameError("Username cannot be empty.")
        }
        return correctName
    }

    private fun validatePassword(password : String): Boolean {
        // if password field is empty
        // it will display error message "Field can not be empty"
        return if (password.isEmpty()) {
            loginPatientFragment.passwordError("Field can not be empty")
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            loginPatientFragment.passwordError("Password does not match.")
            false
        } else {
            true
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}