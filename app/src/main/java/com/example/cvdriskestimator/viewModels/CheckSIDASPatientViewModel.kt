package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.MDICheckFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.Fragments.SIDASCheckFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.MDITestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckSIDASPatientViewModel : ViewModel() {

    private lateinit var mainActivity : MainActivity
    private lateinit var sidasCheckFragment: SIDASCheckFragment
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

    fun passFragment(fragment : SIDASCheckFragment)
    {
        sidasCheckFragment = fragment
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
        realm = Realm.getDefaultInstance()
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

    fun history()
    {
        var patientId : String = ""
        var testName : String = ""
        realm.executeTransaction {
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            var patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()
            patientId = patient!!.patientId
            testName = "SIDAS"
        }
        val bundle = Bundle()
        bundle.putString("patientId" , patientId)
        bundle.putString("testName" , testName)
        historyFragment = HistoryFragment()
        historyFragment.arguments = bundle
        mainActivity.fragmentTransaction(historyFragment)
    }

    fun fetchHistoryTest(patientId : String, testID : String) : Test
    {
        var tests : RealmResults<Test>? = null
        realm.executeTransaction {

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Major Depression Index").findAll()
//            var dummyTest = dummyTestList.get(dummyTestList.size -1)
//            var dummyTestDate = Calendar.getInstance()
//            if (testDate.day > 1)
//            {
//                dummyTestDate.set(Calendar.YEAR , testDate.year + 1900)
//                dummyTestDate.set(Calendar.MONTH , testDate.month)
//                dummyTestDate.set(Calendar.DAY_OF_MONTH , testDate.day - 1)
//            }
//            else
//            {
//                dummyTestDate.set(Calendar.MONTH , testDate.month -1)
//                dummyTestDate.set(Calendar.DAY_OF_MONTH , testDate.day - 1)
//                if (testDate.month == 1)
//                {
//                    dummyTestDate.set(Calendar.YEAR , testDate.year -1 + 1900)
//                    dummyTestDate.set(Calendar.MONTH , 12)
//                    dummyTestDate.set(Calendar.DAY_OF_MONTH , 31)
//                }
//            }
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testId" , testID).equalTo("testName" , "SIDAS").findAll()
        }

        return tests!!.get(tests!!.size - 1)!!
    }

    private fun fetchPatientData(username : String) {
        patientData = realmDAO.fetchPatientData(username)
        testData = realmDAO.fetchTestData(patientData.value!!.patientId , "SIDAS")
        patientData.postValue(patientData.value)
        testData.postValue(testData.value)
    }

    fun checkMDITestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)))
        {
            val result = mdiTestEstimator.calculateSIDASScore(allPatientSelections)
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
        }
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 , 15 , null , null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            sidasCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
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
//            val date = Date()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            val calendar: Calendar = Calendar.getInstance()
//            calendar.set(Calendar.YEAR , date.year)
//            calendar.set(Calendar.MONTH , date.month)
//            calendar.set(Calendar.DAY_OF_MONTH , date.day)
//            calendar.set(Calendar.HOUR_OF_DAY, date.hours)
//            calendar.set(Calendar.MINUTE, date.minutes)
//            calendar.set(Calendar.SECOND, date.seconds)
            //check if the current date is already in the test database
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            currentTest!!.patientSIDASQ1 = allPatientSelections[0]
            currentTest!!.patientSIDASQ2 = allPatientSelections[1]
            currentTest!!.patientSIDASQ3 = allPatientSelections[2]
            currentTest!!.patientSIDASQ4 = allPatientSelections[3]
            currentTest!!.patientSIDASQ5 = allPatientSelections[4]

            currentTest!!.patientId = patient!!.patientId
            currentTest.testDate = calendar.time
            currentTest!!.sidasTestResult = result
            currentTest!!.testName = "SIDAS"

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
//
//
//            var dummyTest1 = Test()
////            val date = Date()
////            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
//            val calendar1: Calendar = Calendar.getInstance()
//            calendar1.set(Calendar.YEAR , 2022)
//            calendar1.set(Calendar.MONTH , 11)
//            calendar1.set(Calendar.DAY_OF_MONTH , 15)
//            calendar1.set(Calendar.HOUR_OF_DAY, 10)
//            calendar1.set(Calendar.MINUTE, 0)
//            calendar1.set(Calendar.SECOND, 0)
//
//            dummyTest1!!.patientMDIQ1 = allPatientSelections[0]
//            dummyTest1!!.patientMDIQ2 = allPatientSelections[1]
//            dummyTest1!!.patientMDIQ3 = allPatientSelections[2]
//            dummyTest1!!.patientMDIQ4 = allPatientSelections[3]
//            dummyTest1!!.patientMDIQ5 = allPatientSelections[4]
//            dummyTest1!!.patientMDIQ6 = allPatientSelections[5]
//            dummyTest1!!.patientMDIQ7 = allPatientSelections[6]
//            dummyTest1!!.patientMDIQ8 = allPatientSelections[7]
//            dummyTest1!!.patientMDIQ9 = allPatientSelections[8]
//            dummyTest1!!.patientMDIQ10 = allPatientSelections[9]
//            dummyTest1!!.patientMDIQ11 = allPatientSelections[10]
//            dummyTest1!!.patientMDIQ12 = allPatientSelections[11]
//            dummyTest1!!.patientMDIQ13 = allPatientSelections[12]
//            dummyTest1!!.patientId = patient!!.patientId
//            dummyTest1!!.testId = (currentTest.testId.toInt() + 1).toString()
//            dummyTest1.testDate = calendar.time
//            dummyTest1!!.patientMDITestResult = 65
//            dummyTest1!!.testName = "Major Depression Index"
//
//            var testId1 : Int = 0
//            if (dateCount.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId1 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId1 += 1
//                    dummyTest1.testId = testId1.toString()
//                }
//                else
//                {
//                    testId1 = 1
//                    dummyTest1.testId = testId1.toString()
//                }
//            }
//
//            var listOftests1 = ArrayList<Test>()
//            if (patient!!.listOfTests!! != null)
//            {
//                for (i in 0 until patient!!.listOfTests!!.size -1)
//                {
//                    listOftests1[i] = patient!!.listOfTests!!.get(i)!!
//                }
//                listOftests1.add(dummyTest1)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests1.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests1.get(i)
//                }
//            }
//
//            realm.insertOrUpdate(dummyTest1)
//
//            var dummyTest2 = Test()
////            val date = Date()
////            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
//            val calendar2: Calendar = Calendar.getInstance()
//            calendar2.set(Calendar.YEAR , 2022)
//            calendar2.set(Calendar.MONTH , 9)
//            calendar2.set(Calendar.DAY_OF_MONTH , 20)
//            calendar2.set(Calendar.HOUR_OF_DAY, 10)
//            calendar2.set(Calendar.MINUTE, 0)
//            calendar2.set(Calendar.SECOND, 0)
//
//            dummyTest2!!.patientMDIQ1 = allPatientSelections[0]
//            dummyTest2!!.patientMDIQ2 = allPatientSelections[1]
//            dummyTest2!!.patientMDIQ3 = allPatientSelections[2]
//            dummyTest2!!.patientMDIQ4 = allPatientSelections[3]
//            dummyTest2!!.patientMDIQ5 = allPatientSelections[4]
//            dummyTest2!!.patientMDIQ6 = allPatientSelections[5]
//            dummyTest2!!.patientMDIQ7 = allPatientSelections[6]
//            dummyTest2!!.patientMDIQ8 = allPatientSelections[7]
//            dummyTest2!!.patientMDIQ9 = allPatientSelections[8]
//            dummyTest2!!.patientMDIQ10 = allPatientSelections[9]
//            dummyTest2!!.patientMDIQ11 = allPatientSelections[10]
//            dummyTest2!!.patientMDIQ12 = allPatientSelections[11]
//            dummyTest2!!.patientMDIQ13 = allPatientSelections[12]
//            dummyTest2!!.patientId = patient!!.patientId
//            dummyTest2!!.testId = (dummyTest1.testId.toInt() + 1).toString()
//            dummyTest2.testDate = calendar.time
//            dummyTest2!!.patientMDITestResult = 55
//            dummyTest2!!.testName = "Major Depression Index"
//
//            var testId4 : Int = 0
//            if (dateCount.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId4 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId4 += 1
//                    currentTest.testId = testId4.toString()
//                }
//                else
//                {
//                    testId4 = 1
//                    currentTest.testId = testId4.toString()
//                }
//            }
//
//            var listOftests4 = ArrayList<Test>()
//            if (patient!!.listOfTests!! != null)
//            {
//                for (i in 0 until patient!!.listOfTests!!.size -1)
//                {
//                    listOftests4[i] = patient!!.listOfTests!!.get(i)!!
//                }
//                listOftests4.add(dummyTest2)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests4.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests4.get(i)
//                }
//            }
//
//            realm.insertOrUpdate(dummyTest2)
//
//            var dummyTest3 = Test()
////            val date = Date()
////            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
//            val calendar3: Calendar = Calendar.getInstance()
//            calendar3.set(Calendar.YEAR , 2023)
//            calendar3.set(Calendar.MONTH , 1)
//            calendar3.set(Calendar.DAY_OF_MONTH , 15)
//            calendar3.set(Calendar.HOUR_OF_DAY, 10)
//            calendar3.set(Calendar.MINUTE, 0)
//            calendar3.set(Calendar.SECOND, 0)
//
//            dummyTest3!!.patientMDIQ1 = allPatientSelections[0]
//            dummyTest3!!.patientMDIQ2 = allPatientSelections[1]
//            dummyTest3!!.patientMDIQ3 = allPatientSelections[2]
//            dummyTest3!!.patientMDIQ4 = allPatientSelections[3]
//            dummyTest3!!.patientMDIQ5 = allPatientSelections[4]
//            dummyTest3!!.patientMDIQ6 = allPatientSelections[5]
//            dummyTest3!!.patientMDIQ7 = allPatientSelections[6]
//            dummyTest3!!.patientMDIQ8 = allPatientSelections[7]
//            dummyTest3!!.patientMDIQ9 = allPatientSelections[8]
//            dummyTest3!!.patientMDIQ10 = allPatientSelections[9]
//            dummyTest3!!.patientMDIQ11 = allPatientSelections[10]
//            dummyTest3!!.patientMDIQ12 = allPatientSelections[11]
//            dummyTest3!!.patientMDIQ13 = allPatientSelections[12]
//            dummyTest3!!.patientId = patient!!.patientId
//            dummyTest3!!.testId = (currentTest.testId.toInt() + 1).toString()
//            dummyTest3.testDate = calendar.time
//            dummyTest3!!.patientMDITestResult = 65
//            dummyTest3!!.testName = "Major Depression Index"
//
//            var testId3 : Int = 0
//            if (dateCount.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId3 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId3 += 1
//                    dummyTest3.testId = testId3.toString()
//                }
//                else
//                {
//                    testId3 = 1
//                    dummyTest3.testId = testId3.toString()
//                }
//            }
//
//            var listOftests3 = ArrayList<Test>()
//            if (patient!!.listOfTests!! != null)
//            {
//                for (i in 0 until patient!!.listOfTests!!.size -1)
//                {
//                    listOftests3[i] = patient!!.listOfTests!!.get(i)!!
//                }
//                listOftests3.add(dummyTest3)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests3.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests3.get(i)
//                }
//            }
//
//            realm.insertOrUpdate(dummyTest3)
//
//            var dummyTest4 = Test()
////            val date = Date()
////            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
//            val calendar4: Calendar = Calendar.getInstance()
//            calendar4.set(Calendar.YEAR , 2022)
//            calendar4.set(Calendar.MONTH , 9)
//            calendar4.set(Calendar.DAY_OF_MONTH , 20)
//            calendar4.set(Calendar.HOUR_OF_DAY, 10)
//            calendar4.set(Calendar.MINUTE, 0)
//            calendar4.set(Calendar.SECOND, 0)
//
//            dummyTest4!!.patientMDIQ1 = allPatientSelections[0]
//            dummyTest4!!.patientMDIQ2 = allPatientSelections[1]
//            dummyTest4!!.patientMDIQ3 = allPatientSelections[2]
//            dummyTest4!!.patientMDIQ4 = allPatientSelections[3]
//            dummyTest4!!.patientMDIQ5 = allPatientSelections[4]
//            dummyTest4!!.patientMDIQ6 = allPatientSelections[5]
//            dummyTest4!!.patientMDIQ7 = allPatientSelections[6]
//            dummyTest4!!.patientMDIQ8 = allPatientSelections[7]
//            dummyTest4!!.patientMDIQ9 = allPatientSelections[8]
//            dummyTest4!!.patientMDIQ10 = allPatientSelections[9]
//            dummyTest4!!.patientMDIQ11 = allPatientSelections[10]
//            dummyTest4!!.patientMDIQ12 = allPatientSelections[11]
//            dummyTest4!!.patientMDIQ13 = allPatientSelections[12]
//            dummyTest4!!.patientId = patient!!.patientId
//            dummyTest4!!.testId = (dummyTest1.testId.toInt() + 1).toString()
//            dummyTest4.testDate = calendar.time
//            dummyTest4!!.patientMDITestResult = 475
//            dummyTest4!!.testName = "Major Depression Index"
//
//            var testId2 : Int = 0
//            if (dateCount.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId2 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId2 += 1
//                    currentTest.testId = testId2.toString()
//                }
//                else
//                {
//                    testId2 = 1
//                    currentTest.testId = testId2.toString()
//                }
//            }
//
//            var listOftests2 = ArrayList<Test>()
//            if (patient!!.listOfTests!! != null)
//            {
//                for (i in 0 until patient!!.listOfTests!!.size -1)
//                {
//                    listOftests2[i] = patient!!.listOfTests!!.get(i)!!
//                }
//                listOftests2.add(dummyTest4)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests2.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests2.get(i)
//                }
//            }
//
//            realm.insertOrUpdate(dummyTest4)

            realm.insertOrUpdate(patient)

        }
    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }

}