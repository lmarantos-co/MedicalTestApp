package com.example.cvdriskestimator.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.Fragments.medDietTestFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CheckMedDietTestViewModel : ViewModel(){

    private lateinit var realm : Realm
    private lateinit var mainActivity: MainActivity
    private lateinit var medDietTestFragment: medDietTestFragment
    private lateinit var resultFragment: ResultFragment
    private var realmDAO = RealmDAO()
    var patientData = MutableLiveData<Patient>()


    fun setActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun setFragment(DietTestFragment: medDietTestFragment)
    {
        medDietTestFragment = DietTestFragment
    }

    fun initialiseRealm()
    {
        Realm.init(mainActivity.applicationContext)
        realm = Realm.getDefaultInstance()
    }

    fun initialiseUserDummy() : Job =
        viewModelScope.launch(Dispatchers.IO) {
            setUserDummyData()
        }


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

    fun checkMDSTestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10) )
            && (checkQuestionForInputError(allPatientSelections[10]  , 11))
           )
        {
            storePatientData(allPatientSelections)
            var score : Int = 0
            for (patientValue in allPatientSelections)
            {
                score += patientValue!!
            }
            openResultFragment(score)
        }
    }

    fun openResultFragment(score : Int)
    {
        resultFragment = ResultFragment.newInstance(score.toDouble() , 0.0 ,  5)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            medDietTestFragment.showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientData(medDietScoreValues : ArrayList<Int?>)
    {
        realm.executeTransaction {

            var userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")
            var patient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName", userName).findFirst()
            patient!!.patientMDSQ1 = medDietScoreValues.get(0)
            patient!!.patientMDSQ2 = medDietScoreValues.get(1)
            patient!!.patientMDSQ3 = medDietScoreValues.get(2)
            patient!!.patientMDSQ4 = medDietScoreValues.get(3)
            patient!!.patientMDSQ5 = medDietScoreValues.get(4)
            patient!!.patientMDSQ6 = medDietScoreValues.get(5)
            patient!!.patientMDSQ7 = medDietScoreValues.get(6)
            patient!!.patientMDSQ8 = medDietScoreValues.get(7)
            patient!!.patientMDSQ9 = medDietScoreValues.get(8)
            patient!!.patientMDSQ10 = medDietScoreValues.get(9)
            patient!!.patientMDSQ11 = medDietScoreValues.get(10)

            realm.copyToRealmOrUpdate(patient)


        }
    }


}