package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BAICheckFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BAITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import io.realm.Realm
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CheckBAIPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var baiCheckFragment: BAICheckFragment
    private lateinit var realm : Realm
    private lateinit var baiTestEstimator : BAITestEstimator
    private var realmDAO = RealmDAO()
    private lateinit var resultFragment: ResultFragment

    var patientData = MutableLiveData<Patient>()

    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
        baiTestEstimator = BAITestEstimator(activity)
    }

    fun passFragment(BAICheckFragment: BAICheckFragment)
    {
        baiCheckFragment = BAICheckFragment
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
            var dummyPatient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , "tempUser").findFirst()
            if (dummyPatient == null)
            {
                val patient = Patient()
                //set the initial values of the patient object
                patient.userName = "tempUser"
                patient.password = "dummy#1"
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

    fun checkBAITestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10))
            && (checkQuestionForInputError(allPatientSelections[10]  , 11)) && (checkQuestionForInputError(allPatientSelections[11]  , 12))
            && (checkQuestionForInputError(allPatientSelections[12]  , 13)) && (checkQuestionForInputError(allPatientSelections[13]  , 14))
            && (checkQuestionForInputError(allPatientSelections[14]  , 15)) && (checkQuestionForInputError(allPatientSelections[15]  , 16))
            && (checkQuestionForInputError(allPatientSelections[16]  , 17)) && (checkQuestionForInputError(allPatientSelections[17]  , 18))
            && (checkQuestionForInputError(allPatientSelections[18]  , 19)) && (checkQuestionForInputError(allPatientSelections[19]  , 20))
            && (checkQuestionForInputError(allPatientSelections[20]  , 21)))
        {
            storePatientOnRealm(allPatientSelections)
            val result = baiTestEstimator.calculateBAIndex(allPatientSelections[0]!!, allPatientSelections[1]!!, allPatientSelections[2]!!, allPatientSelections[3]!!, allPatientSelections[4]!! ,
                allPatientSelections[5]!! , allPatientSelections[6]!!, allPatientSelections[7]!! , allPatientSelections[8]!! , allPatientSelections[9]!! ,
                allPatientSelections[10]!! , allPatientSelections[11]!! , allPatientSelections[12]!! , allPatientSelections[13]!! , allPatientSelections[14]!!,
                allPatientSelections[15]!! , allPatientSelections[16]!! , allPatientSelections[17]!! , allPatientSelections[18]!! , allPatientSelections[19]!!
                , allPatientSelections[20]!!)
            openResultFragment(result)
        }
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 ,  4)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            baiCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?>) : Job =
        viewModelScope.launch{
            storePatientOnDB(allPatientSelections)
        }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?>)
    {
        //execute transaction on realm
        realm.executeTransaction {

            //store the patient data on the database
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            val patient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , username).findFirst()

            patient!!.patientBAIQ1 = allPatientSelections[0]
            patient!!.patientBAIQ2 = allPatientSelections[1]
            patient!!.patientBAIQ3 = allPatientSelections[2]
            patient!!.patientBAIQ4 = allPatientSelections[3]
            patient!!.patientBAIQ5 = allPatientSelections[4]
            patient!!.patientBAIQ6 = allPatientSelections[5]
            patient!!.patientBAIQ7 = allPatientSelections[6]
            patient!!.patientBAIQ8 = allPatientSelections[7]
            patient!!.patientBAIQ9 = allPatientSelections[8]
            patient!!.patientBAIQ10 = allPatientSelections[9]
            patient!!.patientBAIQ11 = allPatientSelections[10]
            patient!!.patientBAIQ12 = allPatientSelections[11]
            patient!!.patientBAIQ13 = allPatientSelections[12]
            patient!!.patientBAIQ14 = allPatientSelections[13]
            patient!!.patientBAIQ15 = allPatientSelections[14]
            patient!!.patientBAIQ16 = allPatientSelections[15]
            patient!!.patientBAIQ17 = allPatientSelections[16]
            patient!!.patientBAIQ18 = allPatientSelections[17]
            patient!!.patientBAIQ19 = allPatientSelections[18]
            patient!!.patientBAIQ20 = allPatientSelections[19]
            patient!!.patientBAIQ21 = allPatientSelections[20]

            realm.insertOrUpdate(patient)

        }
    }

}