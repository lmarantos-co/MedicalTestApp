package com.example.cvdriskestimator.viewModels


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BAICheckFragment
import com.example.cvdriskestimator.Fragments.HamiltonDepressionFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BAITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CheckHAMDPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var hamdCheckFragment: HamiltonDepressionFragment
    private lateinit var realm : Realm
    private lateinit var baiTestEstimator : BAITestEstimator
    private var realmDAO = RealmDAO()
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment

    var patientData = MutableLiveData<Patient>()
    var testDATA = MutableLiveData<Test>()


    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
        baiTestEstimator = BAITestEstimator(activity)
    }

    fun passFragment(hamdFrag: HamiltonDepressionFragment)
    {
        hamdCheckFragment = hamdFrag
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
            var dummyPatient = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , "tempUser").findFirst()
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "BAI")
        patientData.postValue(patientData.value)
        testDATA.postValue(testDATA.value)
    }

    fun checkHAMDTestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10))
            && (checkQuestionForInputError(allPatientSelections[10]  , 11)) && (checkQuestionForInputError(allPatientSelections[11]  , 12))
            && (checkQuestionForInputError(allPatientSelections[12]  , 13)) && (checkQuestionForInputError(allPatientSelections[13]  , 14))
            && (checkQuestionForInputError(allPatientSelections[14]  , 15)) && (checkQuestionForInputError(allPatientSelections[15]  , 16)))
        {
            val result = baiTestEstimator.calculateBAIndex(allPatientSelections[0]!!, allPatientSelections[1]!!, allPatientSelections[2]!!, allPatientSelections[3]!!, allPatientSelections[4]!! ,
                allPatientSelections[5]!! , allPatientSelections[6]!!, allPatientSelections[7]!! , allPatientSelections[8]!! , allPatientSelections[9]!! ,
                allPatientSelections[10]!! , allPatientSelections[11]!! , allPatientSelections[12]!! , allPatientSelections[13]!! , allPatientSelections[14]!!,
                allPatientSelections[15]!! , allPatientSelections[16]!! , allPatientSelections[17]!! , allPatientSelections[18]!! , allPatientSelections[19]!!
                , allPatientSelections[20]!!)
            storePatientOnRealm(allPatientSelections , result)
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
            hamdCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO , questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?> , score : Int) : Job =
        viewModelScope.launch{
            storePatientOnDB(allPatientSelections , score)
        }

    fun history()
    {
        var patientId : String = ""
        var testName : String = ""
        realm.executeTransaction {
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            var patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()
            patientId = patient!!.patientId
            testName = "Hammilton Depression"
        }
        val bundle = Bundle()
        bundle.putString("patientId" , patientId)
        bundle.putString("testName" , testName)
        historyFragment = HistoryFragment()
        historyFragment.arguments = bundle
        mainActivity.fragmentTransaction(historyFragment)
    }

    fun fetchHistoryTest(patientId : String, testDate : Date) : Test
    {
        var test = Test()
        realm.executeTransaction {

            test = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testDate" , testDate).equalTo("testName" , "Beck Anxiety Index").findFirst()!!
        }

        return test
    }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?> , score : Int)
    {
        //execute transaction on realm
        realm.executeTransaction {

            //store the patient data on the database
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            val patient = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , username).findFirst()

            var currentTest = Test()
            val date = Date()
            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR , date.year)
            calendar.set(Calendar.MONTH , date.month)
            calendar.set(Calendar.DAY_OF_MONTH , date.day)
//            calendar.set(Calendar.HOUR_OF_DAY, date.hours)
//            calendar.set(Calendar.MINUTE, date.minutes)
//            calendar.set(Calendar.SECOND, date.seconds)
            //check if the current date is already in the test database
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , currentDate).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , currentDate).findFirst()!!
            }

            currentTest!!.patientHAMDQ1 = allPatientSelections[0]
            currentTest!!.patientHAMDQ2 = allPatientSelections[1]
            currentTest!!.patientHAMDQ3 = allPatientSelections[2]
            currentTest!!.patientHAMDQ4 = allPatientSelections[3]
            currentTest!!.patientHAMDQ5 = allPatientSelections[4]
            currentTest!!.patientHAMDQ6 = allPatientSelections[5]
            currentTest!!.patientHAMDQ7 = allPatientSelections[6]
            currentTest!!.patientHAMDQ8 = allPatientSelections[7]
            currentTest!!.patientHAMDQ9 = allPatientSelections[8]
            currentTest!!.patientHAMDQ10 = allPatientSelections[9]
            currentTest!!.patientHAMDQ11 = allPatientSelections[10]
            currentTest!!.patientHAMDQ12 = allPatientSelections[11]
            currentTest!!.patientHAMDQ13 = allPatientSelections[12]
            currentTest!!.patientHAMDQ14 = allPatientSelections[13]
            currentTest!!.patientHAMDQ15 = allPatientSelections[14]
            currentTest!!.patientHAMDQ16 = allPatientSelections[15]
            currentTest!!.patientHAMDQ17 = allPatientSelections[16]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.testDate = calendar.time
            currentTest!!.testName = "Hammilton Depression"
            currentTest!!.patientHAMDTestResult = score

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

            //add the test to the patient test list
            var testList = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    testList.add(patient.listOfTests!!.get(i)!!)
                }
                testList.add(currentTest)
                patient.listOfTests = null
                for (i in 0 until testList.size -1)
                {
                    patient.listOfTests!![i] = testList.get(i)
                }
            }
            realm.insertOrUpdate(currentTest)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

        }
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date)
    }

}