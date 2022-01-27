package com.example.cvdriskestimator.viewModels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.MDICheckFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.MDITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CheckMDIPatientViewModel : ViewModel() {

    private lateinit var mainActivity : MainActivity
    private lateinit var mdiCheckFragment: MDICheckFragment
    private lateinit  var realm : Realm
    private  var realmDAO =  RealmDAO()
    private var mdiTestEstimator = MDITestEstimator()
    private lateinit var resultFragment : ResultFragment

    //mutable data that hold the patient data
    var patientData = MutableLiveData<Patient>()

    fun passActivity(activity : MainActivity)
    {
        mainActivity = activity
    }

    fun passFragment(fragment : MDICheckFragment)
    {
        mdiCheckFragment = fragment
    }

    fun initialiseRealm()
    {
        Realm.init(mainActivity.applicationContext)
        realm = Realm.getDefaultInstance()
    }

    fun initialiseUserDummy() : Job =
        viewModelScope.launch { setUserDummyData() }


    fun setUserDummyData()
    {
        realm.executeTransaction {
            var dummyPatient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , "userDummy").findFirst()
            if (dummyPatient == null)
            {
                val patient = Patient()
                //set the initial values of the patient object
                patient.userName = "userDummy"
                patient.password = "password#1"
                realm.insertOrUpdate(patient)
            }
        }
    }

    fun setPatientDataOnForm(username : String)
    {
        fetchPatientData(username)
    }

    private fun fetchPatientData(username : String) {
        patientData = realmDAO.fetchPatientData(username)
        patientData.postValue(patientData.value)
    }

    fun checkMDITestPatient(mdiq1 : Int? , mdiq2 : Int? , mdiq3 : Int? , mdiq4 : Int? , mdiq5 : Int? , mdiq6 : Int? , mdiq7 : Int?
                                    , mdiq8 : Int? , mdiq9 : Int? , mdiq10 : Int? , mdiq11 : Int? , mdiq12 : Int? , mdiq13 : Int?)
    {
        if ((checkQuestionForInputError(mdiq1 , 1)) && (checkQuestionForInputError(mdiq2 , 2))
            && (checkQuestionForInputError(mdiq3 , 3)) && (checkQuestionForInputError(mdiq4 , 4))
            && (checkQuestionForInputError(mdiq5 , 5)) && (checkQuestionForInputError(mdiq6 , 6))
            && (checkQuestionForInputError(mdiq7 , 7)) && (checkQuestionForInputError(mdiq8 , 8))
            && (checkQuestionForInputError(mdiq9 , 9)) && (checkQuestionForInputError(mdiq10 , 10))
            && (checkQuestionForInputError(mdiq11 , 11)) && (checkQuestionForInputError(mdiq12 , 12))
            && (checkQuestionForInputError(mdiq13 , 13)))
            {
                //store the patient data on the database
                val newPatient = Patient()
                newPatient.patientMDIQ1 = mdiq1
                newPatient.patientMDIQ2 = mdiq2
                newPatient.patientMDIQ3 = mdiq3
                newPatient.patientMDIQ4 = mdiq4
                newPatient.patientMDIQ5 = mdiq5
                newPatient.patientMDIQ6 = mdiq6
                newPatient.patientMDIQ7 = mdiq7
                newPatient.patientMDIQ8 = mdiq8
                newPatient.patientMDIQ9 = mdiq9
                newPatient.patientMDIQ10 = mdiq10
                newPatient.patientMDIQ11 = mdiq11
                newPatient.patientMDIQ12 = mdiq12
                newPatient.patientMDIQ13 = mdiq13

                storePatientOnRealm(newPatient)
                val result = mdiTestEstimator.calculateMDI(mdiq1!!, mdiq2!!, mdiq3!!, mdiq4!!, mdiq5!! , mdiq6!! , mdiq7!!
                    , mdiq8!! , mdiq9!! , mdiq10!! , mdiq11!! , mdiq12!! , mdiq13!!)
                Toast.makeText(mainActivity.applicationContext , result.toString() , Toast.LENGTH_LONG).show()
                openResultFragment(result)
            }
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 3)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            mdiCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(patient : Patient) : Job =
        viewModelScope.launch{
            storePatientOnDB(patient)
        }

    private fun storePatientOnDB(patient : Patient)
    {
        //execute transaction on realm
        realm.executeTransaction {
            realm.insertOrUpdate(patient)

        }
    }


}