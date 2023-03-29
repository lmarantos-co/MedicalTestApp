package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BAICheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BAITestEstimator
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

class CheckBAIPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var baiCheckFragment: BAICheckFragment
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "Beck Anxiety Index")
        patientData.postValue(patientData.value)
        testDATA.postValue(testDATA.value)
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
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 ,  4 , null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            baiCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO , questionNO)
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
            testName = "Beck Anxiety Index"
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

            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Beck Anxiety Index").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).lessThanOrEqualTo("testDate" , testDate).equalTo("testName" , "Beck Anxiety Index").findAll()
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

            currentTest!!.patientBAIQ1 = allPatientSelections[0]
            currentTest!!.patientBAIQ2 = allPatientSelections[1]
            currentTest!!.patientBAIQ3 = allPatientSelections[2]
            currentTest!!.patientBAIQ4 = allPatientSelections[3]
            currentTest!!.patientBAIQ5 = allPatientSelections[4]
            currentTest!!.patientBAIQ6 = allPatientSelections[5]
            currentTest!!.patientBAIQ7 = allPatientSelections[6]
            currentTest!!.patientBAIQ8 = allPatientSelections[7]
            currentTest!!.patientBAIQ9 = allPatientSelections[8]
            currentTest!!.patientBAIQ10 = allPatientSelections[9]
            currentTest!!.patientBAIQ11 = allPatientSelections[10]
            currentTest!!.patientBAIQ12 = allPatientSelections[11]
            currentTest!!.patientBAIQ13 = allPatientSelections[12]
            currentTest!!.patientBAIQ14 = allPatientSelections[13]
            currentTest!!.patientBAIQ15 = allPatientSelections[14]
            currentTest!!.patientBAIQ16 = allPatientSelections[15]
            currentTest!!.patientBAIQ17 = allPatientSelections[16]
            currentTest!!.patientBAIQ18 = allPatientSelections[17]
            currentTest!!.patientBAIQ19 = allPatientSelections[18]
            currentTest!!.patientBAIQ20 = allPatientSelections[19]
            currentTest!!.patientBAIQ21 = allPatientSelections[20]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.testDate = calendar.time
            currentTest!!.testName = "Beck Anxiety Index"
            currentTest!!.patientBAITestResult = score

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

            var calendar2 = Calendar.getInstance()
            calendar2.set(Calendar.YEAR , 2023)
            calendar2.set(Calendar.MONTH , 1)
            calendar2.set(Calendar.DAY_OF_MONTH , 12)
            calendar2.set(Calendar.HOUR_OF_DAY, 12)
            calendar2.set(Calendar.MINUTE, 0)
            calendar2.set(Calendar.SECOND, 0)

            var dummyTest1 = Test()

            dummyTest1!!.patientBAIQ1 = 2
            dummyTest1!!.patientBAIQ2 = 1
            dummyTest1!!.patientBAIQ3 = 3
            dummyTest1!!.patientBAIQ4 = 3
            dummyTest1!!.patientBAIQ5 = 2
            dummyTest1!!.patientBAIQ6 = 2
            dummyTest1!!.patientBAIQ7 = 3
            dummyTest1!!.patientBAIQ8 = 1
            dummyTest1!!.patientBAIQ9 = 1
            dummyTest1!!.patientBAIQ10 = 2
            dummyTest1!!.patientBAIQ11 = 3
            dummyTest1!!.patientBAIQ12 = 2
            dummyTest1!!.patientBAIQ13 = 2
            dummyTest1!!.patientBAIQ14 = 2
            dummyTest1!!.patientBAIQ15 = 2
            dummyTest1!!.patientBAIQ16 = 3
            dummyTest1!!.patientBAIQ17 = 3
            dummyTest1!!.patientBAIQ18 = 2
            dummyTest1!!.patientBAIQ19 = 1
            dummyTest1!!.patientBAIQ20 = 2
            dummyTest1!!.patientBAIQ21 = 2
            dummyTest1!!.patientId = patient!!.patientId
            dummyTest1!!.testId = (currentTest.testId.toInt() + 1).toString()
            dummyTest1!!.testDate = calendar2.time
            dummyTest1!!.testName = "Beck Anxiety Index"
            dummyTest1!!.patientBAITestResult = 17


            //add the test to the patient test list
            var testList1 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    testList1.add(patient.listOfTests!!.get(i)!!)
                }
                testList1.add(currentTest)
                patient.listOfTests = null
                for (i in 0 until testList1.size -1)
                {
                    patient.listOfTests!![i] = testList1.get(i)
                }
            }
            realm.insertOrUpdate(dummyTest1)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

            var dummyTest2 = Test()

            var calendar3 = Calendar.getInstance()
            calendar3.set(Calendar.YEAR , 2023)
            calendar3.set(Calendar.MONTH , 2)
            calendar3.set(Calendar.DAY_OF_MONTH , 6)
            calendar3.set(Calendar.HOUR_OF_DAY, 12)
            calendar3.set(Calendar.MINUTE, 0)
            calendar3.set(Calendar.SECOND, 0)

            dummyTest2!!.patientBAIQ1 = 1
            dummyTest2!!.patientBAIQ2 = 2
            dummyTest2!!.patientBAIQ3 = 2
            dummyTest2!!.patientBAIQ4 = 2
            dummyTest2!!.patientBAIQ5 = 3
            dummyTest2!!.patientBAIQ6 = 2
            dummyTest2!!.patientBAIQ7 = 1
            dummyTest2!!.patientBAIQ8 = 2
            dummyTest2!!.patientBAIQ9 = 2
            dummyTest2!!.patientBAIQ10 = 1
            dummyTest2!!.patientBAIQ11 = 3
            dummyTest2!!.patientBAIQ12 = 2
            dummyTest2!!.patientBAIQ13 = 1
            dummyTest2!!.patientBAIQ14 = 1
            dummyTest2!!.patientBAIQ15 = 3
            dummyTest2!!.patientBAIQ16 = 3
            dummyTest2!!.patientBAIQ17 = 3
            dummyTest2!!.patientBAIQ18 = 3
            dummyTest2!!.patientBAIQ19 = 2
            dummyTest2!!.patientBAIQ20 = 3
            dummyTest2!!.patientBAIQ21 = 2
            dummyTest2!!.patientId = currentTest.patientId
            dummyTest2!!.testId = (dummyTest1.testId.toInt() + 1).toString()
            dummyTest2!!.testDate = calendar.time
            dummyTest2!!.testName = "Beck Anxiety Index"
            dummyTest2!!.patientBAITestResult = 10


            //add the test to the patient test list
            var testList2 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    testList2.add(patient.listOfTests!!.get(i)!!)
                }
                testList2.add(currentTest)
                patient.listOfTests = null
                for (i in 0 until testList2.size -1)
                {
                    patient.listOfTests!![i] = testList2.get(i)
                }
            }
            realm.insertOrUpdate(dummyTest2)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

            var dummyTest3 = Test()

            var calendar4 = Calendar.getInstance()
            calendar4.set(Calendar.YEAR , 2022)
            calendar4.set(Calendar.MONTH , 12)
            calendar4.set(Calendar.DAY_OF_MONTH , 17)
            calendar4.set(Calendar.HOUR_OF_DAY, 12)
            calendar4.set(Calendar.MINUTE, 0)
            calendar4.set(Calendar.SECOND, 0)

            dummyTest3!!.patientBAIQ1 = 2
            dummyTest3!!.patientBAIQ2 = 3
            dummyTest3!!.patientBAIQ3 = 3
            dummyTest3!!.patientBAIQ4 = 1
            dummyTest3!!.patientBAIQ5 = 2
            dummyTest3!!.patientBAIQ6 = 1
            dummyTest3!!.patientBAIQ7 = 2
            dummyTest3!!.patientBAIQ8 = 1
            dummyTest3!!.patientBAIQ9 = 1
            dummyTest3!!.patientBAIQ10 = 2
            dummyTest3!!.patientBAIQ11 = 2
            dummyTest3!!.patientBAIQ12 = 3
            dummyTest3!!.patientBAIQ13 = 2
            dummyTest3!!.patientBAIQ14 = 2
            dummyTest3!!.patientBAIQ15 = 1
            dummyTest3!!.patientBAIQ16 = 2
            dummyTest3!!.patientBAIQ17 = 1
            dummyTest3!!.patientBAIQ18 = 2
            dummyTest3!!.patientBAIQ19 = 3
            dummyTest3!!.patientBAIQ20 = 3
            dummyTest3!!.patientBAIQ21 = 1
            dummyTest3!!.patientId = patient.patientId
            dummyTest3!!.testId = (dummyTest2.testId.toInt() + 1).toString()
            dummyTest3!!.testDate = calendar4.time
            dummyTest3!!.testName = "Beck Anxiety Index"
            dummyTest3!!.patientBAITestResult = 18


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
            realm.insertOrUpdate(dummyTest3)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

            var dummyTest4 = Test()


            var calendar5 = Calendar.getInstance()
            calendar5.set(Calendar.YEAR , 2022)
            calendar5.set(Calendar.MONTH , 8)
            calendar5.set(Calendar.DAY_OF_MONTH , 12)
            calendar5.set(Calendar.HOUR_OF_DAY, 12)
            calendar5.set(Calendar.MINUTE, 0)
            calendar5.set(Calendar.SECOND, 0)

            dummyTest4!!.patientBAIQ1 = 1
            dummyTest4!!.patientBAIQ2 = 1
            dummyTest4!!.patientBAIQ3 = 2
            dummyTest4!!.patientBAIQ4 = 1
            dummyTest4!!.patientBAIQ5 = 1
            dummyTest4!!.patientBAIQ6 = 1
            dummyTest4!!.patientBAIQ7 = 2
            dummyTest4!!.patientBAIQ8 = 1
            dummyTest4!!.patientBAIQ9 = 1
            dummyTest4!!.patientBAIQ10 = 1
            dummyTest4!!.patientBAIQ11 = 1
            dummyTest4!!.patientBAIQ12 = 2
            dummyTest4!!.patientBAIQ13 = 1
            dummyTest4!!.patientBAIQ14 = 1
            dummyTest4!!.patientBAIQ15 = 1
            dummyTest4!!.patientBAIQ16 = 2
            dummyTest4!!.patientBAIQ17 = 1
            dummyTest4!!.patientBAIQ18 = 2
            dummyTest4!!.patientBAIQ19 = 1
            dummyTest4!!.patientBAIQ20 = 1
            dummyTest4!!.patientBAIQ21 = 1
            dummyTest4!!.patientId = currentTest.patientId
            dummyTest4!!.testId = (dummyTest3.testId.toInt() + 1).toString()
            dummyTest4!!.testDate = calendar5.time
            dummyTest4!!.testName = "Beck Anxiety Index"
            dummyTest4!!.patientBAITestResult = 21


            //add the test to the patient test list
            var testList4 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    testList4.add(patient.listOfTests!!.get(i)!!)
                }
                testList4.add(currentTest)
                patient.listOfTests = null
                for (i in 0 until testList4.size -1)
                {
                    patient.listOfTests!![i] = testList4.get(i)
                }
            }
            realm.insertOrUpdate(dummyTest4)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

        }
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date)
    }

}