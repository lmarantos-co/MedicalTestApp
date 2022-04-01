package com.example.cvdriskestimator.viewModels

import android.content.Context
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

    fun checkMDITestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10))
            && (checkQuestionForInputError(allPatientSelections[10]  , 11)) && (checkQuestionForInputError(allPatientSelections[11]  , 12))
            && (checkQuestionForInputError(allPatientSelections[12]  , 13)))
            {
                storePatientOnRealm(allPatientSelections)
                val result = mdiTestEstimator.calculateMDI(allPatientSelections[0]!!, allPatientSelections[1]!!, allPatientSelections[2]!!, allPatientSelections[3]!!, allPatientSelections[4]!! ,
                    allPatientSelections[5]!! , allPatientSelections[6]!!, allPatientSelections[7]!! , allPatientSelections[8]!! , allPatientSelections[9]!! ,
                    allPatientSelections[10]!! , allPatientSelections[11]!! , allPatientSelections[12]!!)
                Toast.makeText(mainActivity.applicationContext , result.toString() , Toast.LENGTH_LONG).show()
                openResultFragment(result)
            }
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 , 3)
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

            patient!!.patientMDIQ1 = allPatientSelections[0]
            patient!!.patientMDIQ2 = allPatientSelections[1]
            patient!!.patientMDIQ3 = allPatientSelections[2]
            patient!!.patientMDIQ4 = allPatientSelections[3]
            patient!!.patientMDIQ5 = allPatientSelections[4]
            patient!!.patientMDIQ6 = allPatientSelections[5]
            patient!!.patientMDIQ7 = allPatientSelections[6]
            patient!!.patientMDIQ8 = allPatientSelections[7]
            patient!!.patientMDIQ9 = allPatientSelections[8]
            patient!!.patientMDIQ10 = allPatientSelections[9]
            patient!!.patientMDIQ11 = allPatientSelections[10]
            patient!!.patientMDIQ12 = allPatientSelections[11]
            patient!!.patientMDIQ13 = allPatientSelections[12]


            realm.insertOrUpdate(patient)

        }
    }


}