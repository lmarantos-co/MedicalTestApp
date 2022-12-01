package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BPICheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BPITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CheckBPIPatientViewModel : ViewModel() {

    private lateinit var mainActivity: MainActivity
    private lateinit var bpiFragment: BPICheckFragment
    private lateinit var realm : Realm
    private var realmDAO = RealmDAO()
    private lateinit var bpiTestEstimator : BPITestEstimator
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment

    var patientData = MutableLiveData<Patient>()
    var testData = MutableLiveData<Test>()


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
                realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName", "tempUser")
                    .findFirst()
            if (patient == null) {
                patient = Patient()
                patient?.userName = "tempUser"
                patient?.password = "dummy#1"
            }
            realm.insertOrUpdate(patient)
        }
    }

    fun history()
    {
        var patientId : String = ""
        var testName : String = ""
        realm.executeTransaction {
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            var patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()
            patientId = patient!!.patientId
            testName = "BPI"
        }
        val bundle = Bundle()
        bundle.putString("patientId" , patientId)
        bundle.putString("testName" , testName)
        historyFragment = HistoryFragment()
        historyFragment.arguments = bundle
        mainActivity.fragmentTransaction(historyFragment)
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
                //calculate the pain scores
                bpiTestEstimator = BPITestEstimator(allPatientValues)
                var scoreResults = bpiTestEstimator.calculatePainScores()

                storePatientDataOnRealmDB(allPatientValues , circleCoordinates  , scoreResults)
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

    fun storePatientDataOnRealmDB(allPatientData : ArrayList<Int?> , circleCoordinates: ArrayList<Float?> , scoreResults : ArrayList<Float>)
    {
        realm.executeTransaction {
            var username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")
            var patient = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , username).findFirst()

            var currentTest = Test()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            //check if the current date is already in the test database
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , currentDate).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , currentDate).findFirst()!!
            }

            currentTest!!.patientBPIQ1 = allPatientData[0]
            currentTest!!.patientBPIQ2 = allPatientData[1]
            currentTest!!.patientBPIQ3 = allPatientData[2]
            currentTest!!.patientBPIQ4 = allPatientData[3]
            currentTest!!.patientBPIQ5 = allPatientData[4]
            currentTest!!.patientBPIQ6 = allPatientData[5]
            currentTest!!.patientBPIQ7 = allPatientData[6]
            currentTest!!.patientBPIQ8 = allPatientData[7]
            currentTest!!.patientBPIQ9 = allPatientData[8]
            currentTest!!.patientBPIQ10 = allPatientData[9]
            currentTest!!.patientBPIQ11 = allPatientData[10]
            currentTest!!.patientBPIQ12 = allPatientData[11]
            currentTest!!.patientBPIcircleX = circleCoordinates[0]
            currentTest!!.patientBPIcircleY = circleCoordinates[1]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.patientBPITestInterferenceResult = scoreResults.get(0)
            currentTest!!.patientBPITestSeverityResult = scoreResults.get(1)
            currentTest.testDate = currentDate
            currentTest.testName = "Brief Pain Inventory"

            var testId : Int = 0
            if (dateCount.toInt() == 0)
            {
                var testList = realm.where(Test::class.java).findAll()
                if (testList.size > 0)
                {
                    testId = testList.get(testList.size -1)!!.testId.toInt()
                    testId += 1
                    currentTest.testId = testId.toString()
                }
                else
                {
                    testId = 1
                    currentTest.testId = testId.toString()
                }
            }

            var listOftest = ArrayList<Test>()

            //add the test to the patient table
            if (patient!!.listOfTests != null)
            {
                for (i in 0 until patient!!.listOfTests!!.size -1)
                listOftest[i] = patient!!.listOfTests!!.get(i)!!

                patient!!.listOfTests = null
                for (i in 0 until listOftest.size -1)
                {
                    patient!!.listOfTests!![i] = listOftest.get(i)
                }
            }
            listOftest.add(currentTest)
            realm.insertOrUpdate(currentTest)

            realm.insertOrUpdate(patient)
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
        testData = realmDAO.fetchTestData(patientData.value!!.patientId , "BPI")
        patientData.postValue(patientData!!.value)
    }

}