package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.GDSCheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.MDICheckFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.MDITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CheckGDSViewModel : ViewModel() {

    private lateinit var mainActivity : MainActivity
    private lateinit var gdsCheckFragment: GDSCheckFragment
    private lateinit  var realm : Realm
    private  var realmDAO =  RealmDAO()
    private var mdiTestEstimator = MDITestEstimator()
    private lateinit var resultFragment : ResultFragment
    private lateinit var historyFragment: HistoryFragment

    //mutable data that hold the patient data
    var patientData = MutableLiveData<Patient>()
    var testData = MutableLiveData<Test>()

    fun passActivity(activity : MainActivity)
    {
        mainActivity = activity
    }

    fun passFragment(fragment : GDSCheckFragment)
    {
        gdsCheckFragment = fragment
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
        testData = realmDAO.fetchTestData(patientData.value!!.patientId ,  "Geriatric Depression Scale")
        patientData.postValue(patientData.value)
        testData.postValue(testData.value)
    }

    fun checkGDSTestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10))
            && (checkQuestionForInputError(allPatientSelections[10]  , 11)) && (checkQuestionForInputError(allPatientSelections[11]  , 12))
            && (checkQuestionForInputError(allPatientSelections[12]  , 13)))
        {
            val result = mdiTestEstimator.calculateMDI(allPatientSelections[0]!!, allPatientSelections[1]!!, allPatientSelections[2]!!, allPatientSelections[3]!!, allPatientSelections[4]!! ,
                allPatientSelections[5]!! , allPatientSelections[6]!!, allPatientSelections[7]!! , allPatientSelections[8]!! , allPatientSelections[9]!! ,
                allPatientSelections[10]!! , allPatientSelections[11]!! , allPatientSelections[12]!!)
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
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
            testName = "Geriatric Depression Scale"
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
        var tests : RealmResults<Test>? = null
        realm.executeTransaction {

            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Geriatric Depression Scale").findAll()
            var dummyTest = dummyTestList.get(dummyTestList.size -1)
            var dummyTestDate = Calendar.getInstance()
            if (testDate.day > 1)
            {
                dummyTestDate.set(Calendar.YEAR , testDate.year + 1900)
                dummyTestDate.set(Calendar.MONTH , testDate.month)
                dummyTestDate.set(Calendar.DAY_OF_MONTH , testDate.day - 1)
            }
            else
            {
                dummyTestDate.set(Calendar.MONTH , testDate.month -1)
                dummyTestDate.set(Calendar.DAY_OF_MONTH , testDate.day - 1)
                if (testDate.month == 1)
                {
                    dummyTestDate.set(Calendar.YEAR , testDate.year -1 + 1900)
                    dummyTestDate.set(Calendar.MONTH , 12)
                    dummyTestDate.set(Calendar.DAY_OF_MONTH , 31)
                }
            }
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).lessThanOrEqualTo("testDate" , testDate).equalTo("testName" , "Geriatric Depression Scale").findAll()
        }

        return tests!!.get(tests!!.size -1)!!
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 , 7 , null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            gdsCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?> , result : Int) : Job =
        viewModelScope.launch{
            storePatientOnDB(allPatientSelections , result)
        }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?> , result : Int)
    {
        //execute transaction on realm
        realm.executeTransaction {

            //store the patient data on the database
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            val patient = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , username).findFirst()
            var currentTest = Test()
            val date = Date()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //check if the current date is already in the test database

            val calendar: Calendar = Calendar.getInstance()
//            calendar.set(Calendar.YEAR , date.year)
//            calendar.set(Calendar.MONTH , date.month)
//            calendar.set(Calendar.DAY_OF_MONTH , date.day)
//            calendar.set(Calendar.HOUR_OF_DAY, date.hours)
//            calendar.set(Calendar.MINUTE, date.minutes)
//            calendar.set(Calendar.SECOND, date.seconds)
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            currentTest!!.patientGDSQ1 = allPatientSelections[0]
            currentTest!!.patientGDSQ2 = allPatientSelections[1]
            currentTest!!.patientGDSQ3 = allPatientSelections[2]
            currentTest!!.patientGDSQ4 = allPatientSelections[3]
            currentTest!!.patientGDSQ5 = allPatientSelections[4]
            currentTest!!.patientGDSQ6 = allPatientSelections[5]
            currentTest!!.patientGDSQ7 = allPatientSelections[6]
            currentTest!!.patientGDSQ8 = allPatientSelections[7]
            currentTest!!.patientGDSQ9 = allPatientSelections[8]
            currentTest!!.patientGDSQ10 = allPatientSelections[9]
            currentTest!!.patientGDSQ11 = allPatientSelections[10]
            currentTest!!.patientGDSQ12 = allPatientSelections[11]
            currentTest!!.patientGDSQ13 = allPatientSelections[12]
            currentTest!!.patientGDSQ14 = allPatientSelections[13]
            currentTest!!.patientGDSQ15 = allPatientSelections[14]
            currentTest!!.patientGDSTestResult = result
            currentTest!!.patientId = patient!!.patientId
            currentTest.testDate = calendar.time
            currentTest.testName = "Geriatric Depression Scale"

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

            var listOftests = ArrayList<Test>()
            if (patient!!.listOfTests!! != null)
            {
                for (i in 0 until patient!!.listOfTests!!.size -1)
                {
                    listOftests[i] = patient!!.listOfTests!!.get(i)!!
                }
                listOftests.add(currentTest)
                patient!!.listOfTests = null
                for (i in 0 until listOftests.size -1)
                {
                    patient.listOfTests!![i] = listOftests.get(i)
                }
            }
            realm.insertOrUpdate(currentTest)

            realm.insertOrUpdate(patient)

        }
    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }

}