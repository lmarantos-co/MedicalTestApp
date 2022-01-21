package com.example.cvdriskestimator.viewModels

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cvdriskestimator.Fragments.RegisterFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MySQLDatabase.InsertDBAsyncTask
import com.example.cvdriskestimator.MySQLDatabase.SQLPatient
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.SQLDatabase.SQLRepository
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class RegisterPatientViewModel : ViewModel() , Observable{

    private lateinit var registerFragment : RegisterFragment
    private lateinit var mainActivity: MainActivity
    private var userName : String = ""
    private var userPassword : String = ""
    private var userPassword2 : String = ""
    private  var userDataCorrect : Boolean = true
    private var realm: Realm = Realm.getDefaultInstance()

    //database parameters
    private val URL = "jdbc:mysql://185.138.42.15:3306/estimator_db"
    private val registerURL = "http://304000380.linuxzone188.grserver.gr/register.php"
    val database : String = "estimator_db"
    val userNameDb : String = "estimator_user"
    val userPasswordDb : String = "^Avsq288"

    val sqlRepository = SQLRepository()

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

    @Bindable
    val inputPassValidate = MutableLiveData<String>()

    fun setFragment(registerFrag: RegisterFragment)
    {
        registerFragment = registerFrag
    }


    fun insertOrUpdatePatient() : Job =
        viewModelScope.launch {
            updatePatient()
        }


    //realm related methods


    fun registerPatientMySQL()
    {
        userDataCorrect = true
        var userExists = false
        if (inputUserName.value != null)
        {
            userName = inputUserName.value!!
        }
        if (inputPassword.value != null)
        {
            userPassword = inputPassword.value!!
        }
        if (inputPassValidate.value != null)
        {
            userPassword2 = inputPassValidate.value!!
        }
        userDataCorrect = userDataCorrect && validateUserName(userName)
        userDataCorrect = userDataCorrect && validatePassword(userPassword)
        userDataCorrect = userDataCorrect && comparePasswords(userPassword, userPassword2)
        if (userDataCorrect)
        {
            var patient = SQLPatient(userName , userPassword, null, null, null, null ,null, null , null, null)
            patient.userName = userName
            patient.password = userPassword
            userExists = InsertDBAsyncTask(patient).execute().get()
        }
        if (userExists)
            Toast.makeText(mainActivity.applicationContext, mainActivity.resources.getString(R.string.register_success) , Toast.LENGTH_LONG).show()
        else
            registerFragment.userNameError(mainActivity.resources.getString(R.string.patient_already_in_db))
    }

    fun registerPatientSQL()
    {
        userDataCorrect = true
        var userExists = false
        if (inputUserName.value != null) {
            userName = inputUserName.value!!
        }
        if (inputPassword.value != null) {
            userPassword = inputPassword.value!!
        }
        if (inputPassValidate.value != null)
        {
            userPassword2 = inputPassValidate.value!!
        }

        userDataCorrect = userDataCorrect && validateUserName(userName)
        userDataCorrect = userDataCorrect && validatePassword(userPassword)
        userDataCorrect = userDataCorrect && comparePasswords(userPassword, userPassword2)
        if (userDataCorrect)
        {
            var patient = SQLPatient(userName , userPassword , null , null , null ,null , null , null , null , null)
            patient.userName = userName
            patient.password = userPassword
//            userExists = sqlRepository.insertPatient(patient)
            if (userExists)
                Toast.makeText(mainActivity.applicationContext, mainActivity.resources.getString(R.string.register_success) , Toast.LENGTH_LONG).show()
            else
                registerFragment.userNameError(mainActivity.resources.getString(R.string.patient_already_in_db))
        }
    }

    private suspend fun updatePatient() {

        userDataCorrect = true
        var userExists = false

        if (inputUserName.value != null) {
            userName = inputUserName.value!!
        }
        if (inputPassword.value != null) {
            userPassword = inputPassword.value!!
        }
        if (inputPassValidate.value != null)
        {
            userPassword2 = inputPassValidate.value!!
        }

        userDataCorrect = userDataCorrect && validateUserName(userName)
        userDataCorrect = userDataCorrect && validatePassword(userPassword)
        userDataCorrect = userDataCorrect && comparePasswords(userPassword, userPassword2)

        if (userDataCorrect) {
            // 1.
//            val realm = Realm.getInstance(mainActivity.passRealmConfig())
            val patients = realm.where(Patient::class.java).findAll()
            val patientNum = patients.size
            //check if username is already in db
            if (realm.isEmpty)
            {
                // 2.
                realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
                    // 3.
                    val patient = Patient()
                    patient.id = "0"
                    patient.userName = "userTest"
                    patient.password = "userPass"
                    // 4.
                    realmTransaction.insert(patient)
                }
            }
            else
            {
                realm.refresh()
                for(patient in patients)
                {
                    if (userName == patient.userName)
                        userExists = true
                }
                if (!userExists)
                {
                    // 2.
                    realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
                        // 3.
                        val patient = Patient()
                        patient.id = (patientNum).toString()
                        patient.userName = userName
                        patient.password = userPassword
                        // 4.
                        realmTransaction.copyToRealmOrUpdate(patient)
                    }
                    Toast.makeText(mainActivity.applicationContext, mainActivity.resources.getString(R.string.register_success) , Toast.LENGTH_LONG).show()
                    hideSoftInputKeyboard()
                    mainActivity.backToActivity()
                }
                else
                {
                    registerFragment.userNameError(mainActivity.resources.getString(R.string.patient_already_in_db))
                }
            }
        }
    }

    fun deletePatient(id: String) {
        val patients = realm.where(Patient::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            patients!!.deleteFromRealm()
        }
    }

    fun deleteAllPatient() {
        realm.executeTransaction { r: Realm ->
            r.delete(Patient::class.java)
        }
    }

    private fun hideSoftInputKeyboard()
    {
        val imm = mainActivity.applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun setActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    private fun validateUserName(name : String) : Boolean {
        var correctName : Boolean
        if (name.isNotEmpty())
        {
            correctName = true
        }
        else
        {
            correctName = false
            registerFragment.userNameError("Username cannot be empty.")
        }
        return correctName
    }

    private fun validatePassword(password : String): Boolean {
        // if password field is empty
        // it will display error message "Field can not be empty"
        return if (password.isEmpty()) {
            registerFragment.passwordError("Field can not be empty")
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            registerFragment.passwordError("Password does not match.")
            false
        } else {
            true
        }
    }

    private fun comparePasswords(pass1 : String, pass2 : String) : Boolean {
        var passMatch: Boolean = true
        passMatch = passMatch && (pass1 == pass2)
        if (!passMatch)
            registerFragment.passwordMatchError("Passwords do not match!")
        return passMatch
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}