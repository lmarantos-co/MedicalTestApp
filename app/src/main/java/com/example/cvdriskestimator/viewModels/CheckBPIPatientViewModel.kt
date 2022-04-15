package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BPICheckFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BPITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CheckBPIPatientViewModel : ViewModel() {

    private lateinit var mainActivity: MainActivity
    private lateinit var bpiFragment: BPICheckFragment
    private lateinit var realm : Realm
    private var realmDAO = RealmDAO()
    private lateinit var bpiTestEstimator : BPITestEstimator
    private lateinit var resultFragment: ResultFragment

    var patientData = MutableLiveData<Patient>()

    //pass the main activity instance
    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    //pass the fragment instance
    fun passFragmentInstance(bpiCheckFragment: BPICheckFragment)
    {
        bpiFragment = bpiCheckFragment
    }

    // init realm DB
    fun initRealm(context : Context)
    {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    fun initPatientData() : Job =
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("THREAD" , Thread.currentThread().name)
            setPatientDataOnForm()
        }

    fun setPatientDataOnForm()
    {
        realm.executeTransaction {
            var patient: Patient? =
                realm.where(Patient::class.java).isNotNull("id").equalTo("userName", "tempUser")
                    .findFirst()
            if (patient == null) {
                patient = Patient()
                patient?.userName = "tempUser"
                patient?.password = "dummy#1"
            }
            realm.insertOrUpdate(patient)
        }
    }

    fun checkBPITestPAtient(allPatientValues : ArrayList<Int?> , circleCoordinates : ArrayList<Float?>)
    {
        if ((checkQuestionForInputError(allPatientValues[0] , 2)) && (checkQuestionForInputError(allPatientValues[1]  , 3))
            && (checkQuestionForInputError(allPatientValues[2]  , 4)) && (checkQuestionForInputError(allPatientValues[3]  , 5))
            && (checkQuestionForInputError(allPatientValues[4]  , 6))
            && (checkQuestionForInputError(allPatientValues[6]  , 8)) && (checkQuestionForInputError(allPatientValues[7]  , 9))
            && (checkQuestionForInputError(allPatientValues[8]  , 10)) && (checkQuestionForInputError(allPatientValues[9]  , 11))
            && (checkQuestionForInputError(allPatientValues[10]  , 12)) && (checkQuestionForInputError(allPatientValues[11]  , 13))
            && (checkCircleCoordinates(circleCoordinates))){
            //all data input is correct
            GlobalScope.launch(Dispatchers.Main) {
                storePatientDataOnRealmDB(allPatientValues , circleCoordinates)
                //calculate the pain scores
                bpiTestEstimator = BPITestEstimator(allPatientValues)
                var scoreResults = bpiTestEstimator.calculatePainScores()

                resultFragment = ResultFragment.newInstance(
                    scoreResults[0].toDouble(),
                    scoreResults[1].toDouble(),
                    6
                )
                mainActivity.fragmentTransaction(resultFragment)
            }
        }

    }

    fun checkCircleCoordinates(circleXY : ArrayList<Float?>) :  Boolean
    {
        var result = false
        if (circleXY.get(0)!!.equals(null) || circleXY.get(1)!!.equals(null))
        {
            bpiFragment.setErrorOnForm("PLease click on the image to place the position of your pain!", 0)
            result = false
        }
        else
            result = true
        return result
    }

    fun storePatientDataOnRealmDB(allPatientData : ArrayList<Int?> , circleCoordinates: ArrayList<Float?>)
    {
        realm.executeTransaction {
            var patient = Patient()
            patient.patientBPIQ1 = allPatientData[0]
            patient.patientBPIQ2 = allPatientData[1]
            patient.patientBPIQ3 = allPatientData[2]
            patient.patientBPIQ4 = allPatientData[3]
            patient.patientBPIQ5 = allPatientData[4]
            patient.patientBPIQ6 = allPatientData[5]
            patient.patientBPIQ7 = allPatientData[6]
            patient.patientBPIQ8 = allPatientData[7]
            patient.patientBPIQ9 = allPatientData[8]
            patient.patientBPIQ10 = allPatientData[9]
            patient.patientBPIQ11 = allPatientData[10]
            patient.patientBPIQ12 = allPatientData[11]
            patient.patientBPIcircleX = circleCoordinates[0]
            patient.patientBPIcircleY = circleCoordinates[1]
            realm.copyToRealmOrUpdate(patient)
        }
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            bpiFragment.setErrorOnForm("Please select an answer for question No : $questionNO", questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    fun setPatientDataOnForm(username : String)
    {
        getPatientData(username)
    }

    private fun getPatientData(username : String)
    {
        patientData = realmDAO.fetchPatientData(username)
        patientData.postValue(patientData!!.value)
    }

}