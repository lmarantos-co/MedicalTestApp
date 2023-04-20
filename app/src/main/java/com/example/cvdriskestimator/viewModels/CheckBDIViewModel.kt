package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BeckDepressionInventoryFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BAITestEstimator
import com.example.cvdriskestimator.MedicalTestAlgorithms.BDITestEstimator
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

class CheckBDIPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var bdiCheckFragment: BeckDepressionInventoryFragment
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

    fun passFragment(BDICheckFragment: BeckDepressionInventoryFragment)
    {
        bdiCheckFragment = BDICheckFragment
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "Beck Depression Inventory")
        patientData.postValue(patientData.value)
        testDATA.postValue(testDATA.value)
    }

    fun checkBDITestPatient(allPatientSelections : ArrayList<Int?>)
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
            var bdiTestEstimator = BDITestEstimator(allPatientSelections!!)
            val result = bdiTestEstimator.bdiTestEstimator()
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
        }
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 ,  8, null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            bdiCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO , questionNO)
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
            testName = "Beck Depression Inventory"
        }
        val bundle = Bundle()
        bundle.putString("patientId" , patientId)
        bundle.putString("testName" , testName)
        historyFragment = HistoryFragment()
        historyFragment.arguments = bundle
        mainActivity.fragmentTransaction(historyFragment)
    }

    fun fetchHistoryTest(patientId : String, testId : String) : Test
    {
        var tests : RealmResults<Test>? = null
        realm.executeTransaction {

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Beck Depression Inventory").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testDate" , testId).equalTo("testName" , "Beck Depression Inventory").findAll()
        }

        return tests!!.get(tests!!.size -1)!!
    }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?> , score : Int)
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

            currentTest!!.patientBDIQ1 = allPatientSelections[0]
            currentTest!!.patientBDIQ2 = allPatientSelections[1]
            currentTest!!.patientBDIQ3 = allPatientSelections[2]
            currentTest!!.patientBDIQ4 = allPatientSelections[3]
            currentTest!!.patientBDIQ5 = allPatientSelections[4]
            currentTest!!.patientBDIQ6 = allPatientSelections[5]
            currentTest!!.patientBDIQ7 = allPatientSelections[6]
            currentTest!!.patientBDIQ8 = allPatientSelections[7]
            currentTest!!.patientBDIQ9 = allPatientSelections[8]
            currentTest!!.patientBDIQ10 = allPatientSelections[9]
            currentTest!!.patientBDIQ11 = allPatientSelections[10]
            currentTest!!.patientBDIQ12 = allPatientSelections[11]
            currentTest!!.patientBDIQ13 = allPatientSelections[12]
            currentTest!!.patientBDIQ14 = allPatientSelections[13]
            currentTest!!.patientBDIQ15 = allPatientSelections[14]
            currentTest!!.patientBDIQ16 = allPatientSelections[15]
            currentTest!!.patientBDIQ17 = allPatientSelections[16]
            currentTest!!.patientBDIQ18 = allPatientSelections[17]
            currentTest!!.patientBDIQ19 = allPatientSelections[18]
            currentTest!!.patientBDIQ20 = allPatientSelections[19]
            currentTest!!.patientBDIQ21 = allPatientSelections[20]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.testDate = calendar.time
            currentTest!!.testName = "Beck Depression Inventory"
            currentTest!!.patientBDITestResult = score

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
            currentTest.testId = testId.toString()
//            realm.insertOrUpdate(currentTest)
            //update the user record within realm database
//            realm.copyToRealmOrUpdate(patient)

//            var dummyTest1 = Test()
//
//            var calendar2 = Calendar.getInstance()
//            calendar2.set(Calendar.YEAR , 2023)
//            calendar2.set(Calendar.MONTH , 1)
//            calendar2.set(Calendar.DAY_OF_MONTH , 12)
//            calendar2.set(Calendar.HOUR_OF_DAY, 12)
//            calendar2.set(Calendar.MINUTE, 0)
//            calendar2.set(Calendar.SECOND, 0)
//            dummyTest1!!.patientBDIQ1 = 4
//            dummyTest1!!.patientBDIQ2 = 3
//            dummyTest1!!.patientBDIQ3 = 4
//            dummyTest1!!.patientBDIQ4 = 1
//            dummyTest1!!.patientBDIQ5 = 2
//            dummyTest1!!.patientBDIQ6 = 3
//            dummyTest1!!.patientBDIQ7 = 4
//            dummyTest1!!.patientBDIQ8 = 3
//            dummyTest1!!.patientBDIQ9 = 4
//            dummyTest1!!.patientBDIQ10 = 2
//            dummyTest1!!.patientBDIQ11 = 1
//            dummyTest1!!.patientBDIQ12 = 3
//            dummyTest1!!.patientBDIQ13 = 2
//            dummyTest1!!.patientBDIQ14 = 3
//            dummyTest1!!.patientBDIQ15 = 3
//            dummyTest1!!.patientBDIQ16 = 2
//            dummyTest1!!.patientBDIQ17 = 3
//            dummyTest1!!.patientBDIQ18 = 1
//            dummyTest1!!.patientBDIQ19 = 3
//            dummyTest1!!.patientBDIQ20 = 2
//            dummyTest1!!.patientBDIQ21 = 3
//            dummyTest1!!.patientId = patient!!.patientId
//            dummyTest1!!.testDate = calendar2.time
//            dummyTest1!!.testName = "Beck Depression Inventory"
//            dummyTest1.testId = currentTest.testId + 1
//            dummyTest1!!.patientBDITestResult = 65
//
//            var testId1 : Int = 0
//            if (dateCount.toInt() == 0)
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
//            //add the test to the patient test list
//            var testList1 = ArrayList<Test>()
//            if (patient.listOfTests != null)
//            {
//                for (i in 0 until patient.listOfTests!!.size -1)
//                {
//                    testList1.add(patient.listOfTests!!.get(i)!!)
//                }
//                testList1.add(currentTest)
//                patient.listOfTests = null
//                for (i in 0 until testList1.size -1)
//                {
//                    patient.listOfTests!![i] = testList1.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest1)
//            //update the user record within realm database
//            realm.copyToRealmOrUpdate(patient)
//
//            var dummyTest2 = Test()
//
//            var calendar3 = Calendar.getInstance()
//            calendar3.set(Calendar.YEAR , 2022)
//            calendar3.set(Calendar.MONTH , 11)
//            calendar3.set(Calendar.DAY_OF_MONTH , 12)
//            calendar3.set(Calendar.HOUR_OF_DAY, 12)
//            calendar3.set(Calendar.MINUTE, 0)
//            calendar3.set(Calendar.SECOND, 0)
//            dummyTest2!!.patientBDIQ1 = 4
//            dummyTest2!!.patientBDIQ2 = 3
//            dummyTest2!!.patientBDIQ3 = 4
//            dummyTest2!!.patientBDIQ4 = 2
//            dummyTest2!!.patientBDIQ5 = 1
//            dummyTest2!!.patientBDIQ6 = 4
//            dummyTest2!!.patientBDIQ7 = 1
//            dummyTest2!!.patientBDIQ8 = 2
//            dummyTest2!!.patientBDIQ9 = 1
//            dummyTest2!!.patientBDIQ10 = 3
//            dummyTest2!!.patientBDIQ11 = 2
//            dummyTest2!!.patientBDIQ12 = 1
//            dummyTest2!!.patientBDIQ13 = 2
//            dummyTest2!!.patientBDIQ14 = 2
//            dummyTest2!!.patientBDIQ15 = 2
//            dummyTest2!!.patientBDIQ16 = 1
//            dummyTest2!!.patientBDIQ17 = 2
//            dummyTest2!!.patientBDIQ18 = 1
//            dummyTest2!!.patientBDIQ19 = 3
//            dummyTest2!!.patientBDIQ20 = 2
//            dummyTest2!!.patientBDIQ21 = 2
//            dummyTest2!!.patientId = patient!!.patientId
//            dummyTest2.testId = (dummyTest1.testId + 1).toString()
//            dummyTest2!!.testDate = calendar3.time
//            dummyTest2!!.testName = "Beck Depression Inventory"
//            dummyTest2!!.patientBDITestResult = 55
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
//            //add the test to the patient test list
//            var testList2 = ArrayList<Test>()
//            if (patient.listOfTests != null)
//            {
//                for (i in 0 until patient.listOfTests!!.size -1)
//                {
//                    testList2.add(patient.listOfTests!!.get(i)!!)
//                }
//                testList2.add(currentTest)
//                patient.listOfTests = null
//                for (i in 0 until testList2.size -1)
//                {
//                    patient.listOfTests!![i] = testList2.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest1)
//            //update the user record within realm database
//            realm.copyToRealmOrUpdate(patient)
//
//            var dummyTest3 = Test()
//
//            var calendar4 = Calendar.getInstance()
//            calendar4.set(Calendar.YEAR , 2022)
//            calendar4.set(Calendar.MONTH , 10)
//            calendar4.set(Calendar.DAY_OF_MONTH , 11)
//            calendar4.set(Calendar.HOUR_OF_DAY, 22)
//            calendar4.set(Calendar.MINUTE, 0)
//            calendar4.set(Calendar.SECOND, 0)
//            dummyTest3!!.patientBDIQ1 = 4
//            dummyTest3!!.patientBDIQ2 = 3
//            dummyTest3!!.patientBDIQ3 = 4
//            dummyTest3!!.patientBDIQ4 = 2
//            dummyTest3!!.patientBDIQ5 = 1
//            dummyTest3!!.patientBDIQ6 = 4
//            dummyTest3!!.patientBDIQ7 = 1
//            dummyTest3!!.patientBDIQ8 = 2
//            dummyTest3!!.patientBDIQ9 = 1
//            dummyTest3!!.patientBDIQ10 = 3
//            dummyTest3!!.patientBDIQ11 = 2
//            dummyTest3!!.patientBDIQ12 = 1
//            dummyTest3!!.patientBDIQ13 = 2
//            dummyTest3!!.patientBDIQ14 = 2
//            dummyTest3!!.patientBDIQ15 = 2
//            dummyTest3!!.patientBDIQ16 = 1
//            dummyTest3!!.patientBDIQ17 = 2
//            dummyTest3!!.patientBDIQ18 = 1
//            dummyTest3!!.patientBDIQ19 = 3
//            dummyTest3!!.patientBDIQ20 = 2
//            dummyTest3!!.patientBDIQ21 = 2
//            dummyTest3!!.patientId = patient!!.patientId
//            dummyTest3.testId = (dummyTest2.testId + 1).toString()
//            dummyTest3!!.testDate = calendar4.time
//            dummyTest3!!.testName = "Beck Depression Inventory"
//            dummyTest3!!.patientBDITestResult = 35
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
//            //add the test to the patient test list
//            var testList4 = ArrayList<Test>()
//            if (patient.listOfTests != null)
//            {
//                for (i in 0 until patient.listOfTests!!.size -1)
//                {
//                    testList4.add(patient.listOfTests!!.get(i)!!)
//                }
//                testList4.add(currentTest)
//                patient.listOfTests = null
//                for (i in 0 until testList4.size -1)
//                {
//                    patient.listOfTests!![i] = testList4.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest1)
//            //update the user record within realm database
//            realm.copyToRealmOrUpdate(patient)
//
//            var dummyTest4 = Test()
//
//            var calendar5 = Calendar.getInstance()
//            calendar5.set(Calendar.YEAR , 2023)
//            calendar5.set(Calendar.MONTH , 2)
//            calendar5.set(Calendar.DAY_OF_MONTH , 7)
//            calendar5.set(Calendar.HOUR_OF_DAY, 18)
//            calendar5.set(Calendar.MINUTE, 0)
//            calendar5.set(Calendar.SECOND, 0)
//            dummyTest4!!.patientBDIQ1 = 4
//            dummyTest4!!.patientBDIQ2 = 3
//            dummyTest4!!.patientBDIQ3 = 4
//            dummyTest4!!.patientBDIQ4 = 2
//            dummyTest4!!.patientBDIQ5 = 1
//            dummyTest4!!.patientBDIQ6 = 4
//            dummyTest4!!.patientBDIQ7 = 1
//            dummyTest4!!.patientBDIQ8 = 2
//            dummyTest4!!.patientBDIQ9 = 1
//            dummyTest4!!.patientBDIQ10 = 3
//            dummyTest4!!.patientBDIQ11 = 2
//            dummyTest4!!.patientBDIQ12 = 1
//            dummyTest4!!.patientBDIQ13 = 2
//            dummyTest4!!.patientBDIQ14 = 2
//            dummyTest4!!.patientBDIQ15 = 2
//            dummyTest4!!.patientBDIQ16 = 1
//            dummyTest4!!.patientBDIQ17 = 2
//            dummyTest4!!.patientBDIQ18 = 1
//            dummyTest4!!.patientBDIQ19 = 3
//            dummyTest4!!.patientBDIQ20 = 2
//            dummyTest4!!.patientBDIQ21 = 2
//            dummyTest4!!.patientId = patient!!.patientId
//            dummyTest4!!.testDate = calendar5.time
//            dummyTest4.testId = (dummyTest3.testId + 1).toString()
//            dummyTest4!!.testName = "Beck Depression Inventory"
//            dummyTest4!!.patientBDITestResult = 35

            var testId3 : Int = 0
            if (dateCount.toInt() == 0)
            {
                var testList = realm.where(Test::class.java).findAll()
                if (testList.size > 0)
                {
                    testId3 = testList.get(testList.size -1)!!.testId.toInt()
                    testId3 += 1
                    currentTest.testId = testId3.toString()
                }
                else
                {
                    testId3 = 1
                    currentTest.testId = testId3.toString()
                }
            }

            //add the test to the patient test list
            var testList3 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    testList3.add(patient.listOfTests!!.get(i)!!)
                }
                testList3.add(currentTest)
                patient.listOfTests = null
                for (i in 0 until testList3.size -1)
                {
                    patient.listOfTests!![i] = testList3.get(i)
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