package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.GDSCheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.MDITestEstimator
import com.example.cvdriskestimator.RealmDB.GDSTest
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
    var testData = MutableLiveData<GDSTest>()

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
        testData = realmDAO.fetchGDSTestData(patientData.value!!.patientId ,  "Geriatric Depression Scale")
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
            val result = mdiTestEstimator.calculateBDI(allPatientSelections)
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

    fun fetchHistoryTest(patientId : String, testDate : String) : GDSTest
    {
        var tests : RealmResults<GDSTest>? = null
        realm.executeTransaction {

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Geriatric Depression Scale").findAll()
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
            tests = realm.where(GDSTest::class.java).equalTo("patientId" , patientId).equalTo("testId" , testDate).findAll()
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
            var currentTest = GDSTest()
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
            val dateCount = realm.where(GDSTest::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(GDSTest::class.java).equalTo("testDate" , calendar.time).findFirst()!!
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
                var testList = realm.where(GDSTest::class.java).findAll()
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

            var listOftests = ArrayList<GDSTest>()
            if (patient!!.listOfGDSTests!! != null)
            {
                for (i in 0 until patient!!.listOfGDSTests!!.size -1)
                {
                    listOftests[i] = patient!!.listOfGDSTests!!.get(i)!! as GDSTest
                }
                listOftests.add(currentTest)
                patient!!.listOfGDSTests = null
                for (i in 0 until listOftests.size -1)
                {
                    patient.listOfGDSTests!![i] = listOftests.get(i)
                }
            }
            realm.insertOrUpdate(currentTest)

//            var dummyTest1 = Test()
//
//            val calendar1: Calendar = Calendar.getInstance()
//            calendar1.set(Calendar.YEAR , 2023)
//            calendar1.set(Calendar.MONTH , 1)
//            calendar1.set(Calendar.DAY_OF_MONTH , 10)
//            calendar1.set(Calendar.HOUR_OF_DAY, 10)
//            calendar1.set(Calendar.MINUTE, 0)
//            calendar1.set(Calendar.SECOND, 0)
//            val dateCount1 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount1 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest1!!.patientGDSQ1 = 0
//            dummyTest1!!.patientGDSQ2 = 1
//            dummyTest1!!.patientGDSQ3 = 1
//            dummyTest1!!.patientGDSQ4 = 0
//            dummyTest1!!.patientGDSQ5 = 1
//            dummyTest1!!.patientGDSQ6 = 0
//            dummyTest1!!.patientGDSQ7 = 0
//            dummyTest1!!.patientGDSQ8 = 1
//            dummyTest1!!.patientGDSQ9 = 1
//            dummyTest1!!.patientGDSQ10 = 1
//            dummyTest1!!.patientGDSQ11 = 1
//            dummyTest1!!.patientGDSQ12 = 1
//            dummyTest1!!.patientGDSQ13 = 0
//            dummyTest1!!.patientGDSQ14 = 0
//            dummyTest1!!.patientGDSQ15 = 0
//            dummyTest1!!.patientGDSTestResult = 10
//            dummyTest1!!.patientId = patient!!.patientId
//            dummyTest1.testDate = calendar1.time
//            dummyTest1.testId = (currentTest.testId.toInt() + 1).toString()
//            dummyTest1.testName = "Geriatric Depression Scale"
//
//            var testId1 : Int = 0
//            if (dateCount1.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId1 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId1 += 1
//                    currentTest.testId = testId1.toString()
//                }
//                else
//                {
//                    testId1 = 1
//                    currentTest.testId = testId1.toString()
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
//                listOftests1.add(currentTest)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests1.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests1.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest1)
//
//            var dummyTest2 = Test()
//
//            val calendar2: Calendar = Calendar.getInstance()
//            calendar2.set(Calendar.YEAR , 2022)
//            calendar2.set(Calendar.MONTH , 12)
//            calendar2.set(Calendar.DAY_OF_MONTH , 5)
//            calendar2.set(Calendar.HOUR_OF_DAY, 10)
//            calendar2.set(Calendar.MINUTE, 0)
//            calendar2.set(Calendar.SECOND, 0)
//            val dateCount2 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount2 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest2!!.patientGDSQ1 = 0
//            dummyTest2!!.patientGDSQ2 = 1
//            dummyTest2!!.patientGDSQ3 = 1
//            dummyTest2!!.patientGDSQ4 = 0
//            dummyTest2!!.patientGDSQ5 = 1
//            dummyTest2!!.patientGDSQ6 = 0
//            dummyTest2!!.patientGDSQ7 = 0
//            dummyTest2!!.patientGDSQ8 = 1
//            dummyTest2!!.patientGDSQ9 = 1
//            dummyTest2!!.patientGDSQ10 = 1
//            dummyTest2!!.patientGDSQ11 = 1
//            dummyTest2!!.patientGDSQ12 = 1
//            dummyTest2!!.patientGDSQ13 = 0
//            dummyTest2!!.patientGDSQ14 = 0
//            dummyTest2!!.patientGDSQ15 = 0
//            dummyTest2!!.patientGDSTestResult = 10
//            dummyTest2!!.patientId = patient!!.patientId
//            dummyTest2.testDate = calendar2.time
//            dummyTest2.testId = (dummyTest1.testId.toInt() + 1).toString()
//            dummyTest2.testName = "Geriatric Depression Scale"
//
//            var testId2 : Int = 0
//            if (dateCount2.toInt() == 0)
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
//                listOftests2.add(currentTest)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests2.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests2.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest2)
//
//
//            var dummyTest3 = Test()
//
//            val calendar3: Calendar = Calendar.getInstance()
//            calendar3.set(Calendar.YEAR , 2023)
//            calendar3.set(Calendar.MONTH , 1)
//            calendar3.set(Calendar.DAY_OF_MONTH , 5)
//            calendar3.set(Calendar.HOUR_OF_DAY, 10)
//            calendar3.set(Calendar.MINUTE, 0)
//            calendar3.set(Calendar.SECOND, 0)
//            val dateCount3 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount3 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest3!!.patientGDSQ1 = 0
//            dummyTest3!!.patientGDSQ2 = 1
//            dummyTest3!!.patientGDSQ3 = 1
//            dummyTest3!!.patientGDSQ4 = 0
//            dummyTest3!!.patientGDSQ5 = 1
//            dummyTest3!!.patientGDSQ6 = 0
//            dummyTest3!!.patientGDSQ7 = 0
//            dummyTest3!!.patientGDSQ8 = 1
//            dummyTest3!!.patientGDSQ9 = 1
//            dummyTest3!!.patientGDSQ10 = 1
//            dummyTest3!!.patientGDSQ11 = 1
//            dummyTest3!!.patientGDSQ12 = 1
//            dummyTest3!!.patientGDSQ13 = 0
//            dummyTest3!!.patientGDSQ14 = 0
//            dummyTest3!!.patientGDSQ15 = 0
//            dummyTest3!!.patientGDSTestResult = 10
//            dummyTest3!!.patientId = patient!!.patientId
//            dummyTest3.testDate = calendar3.time
//            dummyTest3.testId = (dummyTest2.testId.toInt() + 1).toString()
//            dummyTest3.testName = "Geriatric Depression Scale"
//
//            var testId4 : Int = 0
//            if (dateCount3.toInt() == 0)
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
//                listOftests4.add(currentTest)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests4.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests4.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest3)
//
//            var dummyTest4 = Test()
//
//            val calendar4: Calendar = Calendar.getInstance()
//            calendar4.set(Calendar.YEAR , 2023)
//            calendar4.set(Calendar.MONTH , 2)
//            calendar4.set(Calendar.DAY_OF_MONTH , 2)
//            calendar4.set(Calendar.HOUR_OF_DAY, 10)
//            calendar4.set(Calendar.MINUTE, 0)
//            calendar4.set(Calendar.SECOND, 0)
//            val dateCount4 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount4 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest4!!.patientGDSQ1 = 0
//            dummyTest4!!.patientGDSQ2 = 1
//            dummyTest4!!.patientGDSQ3 = 1
//            dummyTest4!!.patientGDSQ4 = 0
//            dummyTest4!!.patientGDSQ5 = 1
//            dummyTest4!!.patientGDSQ6 = 0
//            dummyTest4!!.patientGDSQ7 = 0
//            dummyTest4!!.patientGDSQ8 = 1
//            dummyTest4!!.patientGDSQ9 = 1
//            dummyTest4!!.patientGDSQ10 = 1
//            dummyTest4!!.patientGDSQ11 = 1
//            dummyTest4!!.patientGDSQ12 = 1
//            dummyTest4!!.patientGDSQ13 = 0
//            dummyTest4!!.patientGDSQ14 = 0
//            dummyTest4!!.patientGDSQ15 = 0
//            dummyTest4!!.patientGDSTestResult = 10
//            dummyTest4!!.patientId = patient!!.patientId
//            dummyTest4.testDate = calendar3.time
//            dummyTest4.testId = (dummyTest3.testId.toInt() + 1).toString()
//            dummyTest4.testName = "Geriatric Depression Scale"

//            var testId5 : Int = 0
//            if (dateCount4.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId5 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId5 += 1
//                    currentTest.testId = testId5.toString()
//                }
//                else
//                {
//                    testId5 = 1
//                    currentTest.testId = testId5.toString()
//                }
//            }
//
//            var listOftests5 = ArrayList<Test>()
//            if (patient!!.listOfTests!! != null)
//            {
//                for (i in 0 until patient!!.listOfTests!!.size -1)
//                {
//                    listOftests5[i] = patient!!.listOfTests!!.get(i)!!
//                }
//                listOftests5.add(currentTest)
//                patient!!.listOfTests = null
//                for (i in 0 until listOftests5.size -1)
//                {
//                    patient.listOfTests!![i] = listOftests5.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest4)
//
//            realm.insertOrUpdate(patient)

        }
    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }

}