package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.DiabetesCheckFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.Diabetes2Estimator
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import io.realm.Realm
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CheckDiabetesPatientViewModel : ViewModel() , Observable {

    private lateinit var mainActivity: MainActivity
    private lateinit var diabetesCheckFragment: DiabetesCheckFragment
    private  var diabetes2Estimator = Diabetes2Estimator()
    private lateinit var resultFragment: ResultFragment
    private lateinit var realm: Realm
    private lateinit var realmDAO : RealmDAO


    // Mutable Live Data for Patient
    var patientDATA = MutableLiveData<Patient>()


    fun setMainActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun setDiabetesCheckFragment(checkFragment: DiabetesCheckFragment)
    {
        diabetesCheckFragment = checkFragment
    }

    fun initRealm()
    {
        Realm.init(mainActivity.applicationContext)
        realm = Realm.getDefaultInstance()
        realmDAO = RealmDAO()
    }

    fun setPatientDataOnForm()
    {
        fetchPatientRecord()
    }

    fun setUserDummyData() : Job =
        viewModelScope.launch {
            initialiseUserDummy()
        }

    private fun initialiseUserDummy() {
        realm.executeTransaction {
            var dummyPatient : Patient? =  realm.where(Patient::class.java).isNotNull("id") .equalTo("userName" , "tempUser").findFirst()
            if (dummyPatient == null)
            {
                val patient = Patient()
                patient.userName = "tempUser"
                patient.password = "dummy#1"
                realm.insertOrUpdate(patient)
            }

        }
    }

    private fun checkAgeAndBMI(age : String,  BMI : String) : Boolean {
        var ageCorrect : Boolean = true
        var bmiCorrect : Boolean = true
        //check age for correct input
        if (age.equals(""))
        {
            ageCorrect = ageCorrect && false
            diabetesCheckFragment.displayAgeError("You must input a value for the age.")
        }
        else
        {
            if ((age.toInt() > 0) && (age.toInt() < 120))
            {
                ageCorrect = ageCorrect && true
            }
            else
            {
                diabetesCheckFragment.displayAgeError("Age must be between 1 and 120 years old.")
            }
        }

        if (BMI.equals(""))
        {
            bmiCorrect = bmiCorrect && false
            diabetesCheckFragment.displayBMIError("You must input a value for the BMI.")
        }
        else
        {
            if ((BMI.toDouble() > 10) && (BMI.toDouble() < 50))
            {
                bmiCorrect = bmiCorrect && true
            }
            else
            {
                diabetesCheckFragment.displayBMIError("BMI must be between 10 and 50.")
            }
        }
        return (ageCorrect && bmiCorrect)
    }


//    private fun checkPatientSex(sex : String) : Boolean
//    {
//        var correctSex : Boolean
//        correctSex = (sex == "MALE") && (sex = "FEMALE")
//        if (correctSex == false)
//            diabetesCheckFragment.displaySexError("Please input your Sex.")
//    }

    fun checkPatientForDiabetesData(sex : String , pam : String, Steroids : String , age : String , BMI : String , familyStatus : String , SmokingStatus : String)
    {
        var result : Double = 0.0
        if (checkAgeAndBMI(age , BMI)) {
            result = diabetes2Estimator.calculateDiabetes2Risk(
                sex,
                pam,
                Steroids,
                age,
                BMI,
                familyStatus,
                SmokingStatus
            )
            registerOrUpdatePatient(sex, pam, Steroids, age, BMI, familyStatus, SmokingStatus)
            Log.d(
                "DIABETES",
                result.toString()
            )
            resultFragment = ResultFragment.newInstance(result , 2)
            mainActivity.fragmentTransaction(resultFragment)
        }
    }

    private  fun fetchPatientRecord() {
        val prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
        val username = prefs.getString("userName" , "tempUser")
        patientDATA = realmDAO.fetchPatientData(username!!)
        patientDATA.postValue(patientDATA.value)    }

    fun registerOrUpdatePatient(sex : String , pam : String, Steroids : String , age : String , BMI : String , familyStatus : String , SmokingStatus : String) : Job =
        viewModelScope.launch {
            registerPatient(sex , pam , Steroids , age , BMI  , familyStatus  , SmokingStatus)
    }

    private fun registerPatient(sex : String, pam : String, Steroids : String, age : String, BMI : String, familyStatus : String, SmokingStatus : String)
    {
        var prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
        var userName = prefs.getString("userName", "tempUser")
        realm.executeTransaction {
            var patient : Patient = realm.where(Patient::class.java).equalTo("userName" , userName).findFirst()!!
                patient.patientAge = age
                patient.patientPAM = pam
                patient.patientSteroids = Steroids
                patient.patientSex = sex
                patient.patientBMI = BMI
                patient.patientSiblings = familyStatus
                patient.smoker = SmokingStatus
            realm.insertOrUpdate(patient)
         }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}