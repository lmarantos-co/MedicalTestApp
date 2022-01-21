package com.example.cvdriskestimator.viewModels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cvdriskestimator.Fragments.LoginFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import io.realm.Realm
import java.util.regex.Pattern

class LoginPatientViewModel () : ViewModel() , Observable {

    private lateinit var loginFragment: LoginFragment
    private lateinit var mainActivity : MainActivity
    private var patientName : String = ""
    private var patientPassword : String = ""
    private var realm: Realm = Realm.getDefaultInstance()

    // defining our own password pattern
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"
    )

    @Bindable
    val inputUserName = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    fun setFragment(logFragment: LoginFragment)
    {
        loginFragment = logFragment
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
        var correctUserData = true
        //check for null pointer
        patientName = inputUserName.value!!
        patientPassword = inputPassword.value!!
        correctUserData = correctUserData && validateUserName(inputUserName.value!!)
        correctUserData = correctUserData && validatePassword(inputPassword.value!!)
        if (correctUserData)
        {
            val patient: Patient? = realm.where(Patient::class.java).equalTo("userName", userName).findFirst()
            if (patient != null) {
                if (patient.password != patientPassword)
                {
                    loginFragment.passwordError(mainActivity.resources.getString(R.string.patient_not_in_db))
                }
                else
                {
                    Log.d("LOGIN" , patient.userName)
                    //save the username in shared preferences
                    val sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE) ?: return
                    with (sharedPref.edit()) {
                        putString("userName", patient.userName)
                        putString("MSG", "Welcome " + patient.userName.toString())
                        apply()
                    }
//                    mainActivity.setLoginItemTitle()
                    hideSoftInputKeyboard()
                    loginFragment.backToActivity()
                }
            }
            else
            {
                loginFragment.passwordError(mainActivity.resources.getString(R.string.patient_not_in_db))
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
            loginFragment.userNameError("Username cannot be empty.")
        }
        return correctName
    }

    private fun validatePassword(password : String): Boolean {
        // if password field is empty
        // it will display error message "Field can not be empty"
        return if (password.isEmpty()) {
            loginFragment.passwordError("Field can not be empty")
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            loginFragment.passwordError("Password does not match.")
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