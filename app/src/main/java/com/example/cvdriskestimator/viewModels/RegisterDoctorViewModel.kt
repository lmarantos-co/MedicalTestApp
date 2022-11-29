package com.example.cvdriskestimator.viewModels

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.RegisterDoctorFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Doctor
import com.example.cvdriskestimator.RealmDB.RealmDB
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterDoctorViewModel : ViewModel() , Observable {

    private lateinit var mainActivity : MainActivity
    private lateinit var regDoctorFragment : RegisterDoctorFragment
    private var doctorUserName : String = ""
    private var doctorPassword : String = ""
    private var doctorDataCorrect : Boolean = false
    private lateinit var realmDB : RealmDB
    // defining our own password pattern
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{5,}" +  // at least 4 characters
                "$"
    )

    //live data
    @Bindable
    val inputDoctorName = MutableLiveData<String>()

    @Bindable
    val inputDoctorPassword = MutableLiveData<String>()

    @Bindable
    val inputPasswordValidate = MutableLiveData<String>()

    fun initRealm()
    {
        realmDB = RealmDB(mainActivity.applicationContext)

    }

    //pass activity
    fun passActivity(mActivity : MainActivity)
    {
        mainActivity = mActivity
    }

    //pass fragment
    fun passFragment(registerDoctorFragment: RegisterDoctorFragment)
    {
        regDoctorFragment = registerDoctorFragment
    }

    fun insertOrUpdatePatient() : Job =
        viewModelScope.launch {
            updateDoctor()
        }

    private suspend fun updateDoctor()
    {
        var realm = Realm.getDefaultInstance()
        if (realm.isEmpty)
        {
            //insert a dummy doctor
            var doctor = Doctor()
            doctor.id = 1.toString()
            doctor.doctorUserName = "tempDoctor"
            doctor.doctorPassword = "dummy#1"

            realm.executeTransaction {
                realm.insertOrUpdate(doctor)
            }
        }
        else
        {
            doctorUserName = inputDoctorName.value!!
            doctorPassword = inputDoctorPassword.value!!
            //get all doctors in database
            var doctorUserNameExists : Boolean = false
            if (validateDoctorUserName(doctorUserName))
            {
                val doctors = realm.where(Doctor::class.java).findAll()
                for (doctor in doctors)
                {
                    if (doctor.doctorUserName == doctorUserName)
                    {
                        doctorUserNameExists = true
                    }
                }
                if (doctorUserNameExists)
                    regDoctorFragment.userNameError(mainActivity.resources.getString(R.string.patient_already_in_db))
            }
            if (validateDoctorUserName(doctorUserName)
            && !doctorUserNameExists && (validateDoctorPassword(doctorPassword)))
            {
                val doctorCount = realm.where(Doctor::class.java).count()
                realm.executeTransaction {

                        val doctorId = doctorCount + 1
                        var doctor = Doctor()
                        doctor.id = doctorId.toString()
                        doctor.doctorUserName = doctorUserName
                        doctor.doctorPassword = doctorPassword
                        realm.insert(doctor)

                }
                //return to mainActivity and load login fragment
                mainActivity.loadLoginDoctorFragment()
            }
        }
    }

    private fun validateDoctorUserName(userName : String) : Boolean
    {
        var correctUserName : Boolean = true
        if (userName.isEmpty())
        {
            correctUserName = false
            regDoctorFragment.userNameError("User Name cannot be empty")
        }
        return correctUserName
    }

    private fun validateDoctorPassword(password : String) : Boolean
    {
        var correctPassword : Boolean = true
        if (password.isEmpty())
        {
            correctPassword = false
            regDoctorFragment.passwordError("Password cannot be empty")
        }
        if (!PASSWORD_PATTERN.matcher(password).matches())
        {
            correctPassword = false
            regDoctorFragment.passwordError("Password is not formulated correctly!")
        }
        if (password != inputPasswordValidate.value)
        {
            correctPassword = false
            regDoctorFragment.passwordError("Passwords do not match!")
        }
        return correctPassword
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}
