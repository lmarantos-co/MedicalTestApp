package com.example.cvdriskestimator.viewModels


import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.HamiltonDepressionFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.HammitlonTestEstimator
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

class CheckHAMDPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var hamdCheckFragment: HamiltonDepressionFragment
    private lateinit var realm : Realm
    private lateinit var hammTestEstimator : HammitlonTestEstimator
    private var realmDAO = RealmDAO()
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment

    var patientData = MutableLiveData<Patient>()
    var testDATA = MutableLiveData<Test>()


    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "Hammilton Depression")
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
            && (checkQuestionForInputError(allPatientSelections[14]  , 15)) && (checkQuestionForInputError(allPatientSelections[15]  , 16))
            && (checkQuestionForInputError(allPatientSelections[16]  , 17)))
        {
            var hammitlonTestEstimator = HammitlonTestEstimator(allPatientSelections)
            val result = hammitlonTestEstimator.hamTestEstimator()
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
        }
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 ,  9 , null)
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

    fun fetchHistoryTest(patientId : String, testID : String) : Test
    {
        var tests : RealmResults<Test>? = null
        realm.executeTransaction {

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Hammilton Depression").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testId" , testID).equalTo("testName" , "Hammilton Depression").findAll()
        }

        return tests!!.get(tests!!.size - 1)!!
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


//            var dummyTest1 = Test()
//            val date = Date()
//            val calendar1: Calendar = Calendar.getInstance()
//            calendar1.set(Calendar.YEAR , 2022)
//            calendar1.set(Calendar.MONTH , 11)
//            calendar1.set(Calendar.DAY_OF_MONTH , 5)
//            calendar1.set(Calendar.HOUR_OF_DAY, 10)
//            calendar1.set(Calendar.MINUTE, 0)
//            calendar1.set(Calendar.SECOND,0)
//            //check if the current date is already in the test database
//            val dateCount1 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount1 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest1!!.patientHAMDQ1 = allPatientSelections[0]
//            dummyTest1!!.patientHAMDQ2 = allPatientSelections[1]
//            dummyTest1!!.patientHAMDQ3 = allPatientSelections[2]
//            dummyTest1!!.patientHAMDQ4 = allPatientSelections[3]
//            dummyTest1!!.patientHAMDQ5 = allPatientSelections[4]
//            dummyTest1!!.patientHAMDQ6 = allPatientSelections[5]
//            dummyTest1!!.patientHAMDQ7 = allPatientSelections[6]
//            dummyTest1!!.patientHAMDQ8 = allPatientSelections[7]
//            dummyTest1!!.patientHAMDQ9 = allPatientSelections[8]
//            dummyTest1!!.patientHAMDQ10 = allPatientSelections[9]
//            dummyTest1!!.patientHAMDQ11 = allPatientSelections[10]
//            dummyTest1!!.patientHAMDQ12 = allPatientSelections[11]
//            dummyTest1!!.patientHAMDQ13 = allPatientSelections[12]
//            dummyTest1!!.patientHAMDQ14 = allPatientSelections[13]
//            dummyTest1!!.patientHAMDQ15 = allPatientSelections[14]
//            dummyTest1!!.patientHAMDQ16 = allPatientSelections[15]
//            dummyTest1!!.patientHAMDQ17 = allPatientSelections[16]
//            dummyTest1!!.patientId = patient!!.patientId
//            dummyTest1!!.testDate = calendar1.time
//            dummyTest1!!.testName = "Hammilton Depression"
//            dummyTest1!!.patientHAMDTestResult = 35
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
//
//            var dummyTest2 = Test()
//            val calendar2: Calendar = Calendar.getInstance()
//            calendar2.set(Calendar.YEAR , 2023)
//            calendar2.set(Calendar.MONTH , 2)
//            calendar2.set(Calendar.DAY_OF_MONTH , 10)
//            calendar2.set(Calendar.HOUR_OF_DAY, 10)
//            calendar2.set(Calendar.MINUTE, 0)
//            calendar2.set(Calendar.SECOND,0)
//            //check if the current date is already in the test database
//            val dateCount2 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount2 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest2!!.patientHAMDQ1 = allPatientSelections[0]
//            dummyTest2!!.patientHAMDQ2 = allPatientSelections[1]
//            dummyTest2!!.patientHAMDQ3 = allPatientSelections[2]
//            dummyTest2!!.patientHAMDQ4 = allPatientSelections[3]
//            dummyTest2!!.patientHAMDQ5 = allPatientSelections[4]
//            dummyTest2!!.patientHAMDQ6 = allPatientSelections[5]
//            dummyTest2!!.patientHAMDQ7 = allPatientSelections[6]
//            dummyTest2!!.patientHAMDQ8 = allPatientSelections[7]
//            dummyTest2!!.patientHAMDQ9 = allPatientSelections[8]
//            dummyTest2!!.patientHAMDQ10 = allPatientSelections[9]
//            dummyTest2!!.patientHAMDQ11 = allPatientSelections[10]
//            dummyTest2!!.patientHAMDQ12 = allPatientSelections[11]
//            dummyTest2!!.patientHAMDQ13 = allPatientSelections[12]
//            dummyTest2!!.patientHAMDQ14 = allPatientSelections[13]
//            dummyTest2!!.patientHAMDQ15 = allPatientSelections[14]
//            dummyTest2!!.patientHAMDQ16 = allPatientSelections[15]
//            dummyTest2!!.patientHAMDQ17 = allPatientSelections[16]
//            dummyTest2!!.patientId = patient!!.patientId
//            dummyTest2!!.testDate = calendar2.time
//            dummyTest2!!.testName = "Hammilton Depression"
//            dummyTest2!!.patientHAMDTestResult = 40
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
//            realm.insertOrUpdate(dummyTest2)
//
//
//            var dummyTest3 = Test()
//            val calendar3: Calendar = Calendar.getInstance()
//            calendar3.set(Calendar.YEAR , 2022)
//            calendar3.set(Calendar.MONTH , 9)
//            calendar3.set(Calendar.DAY_OF_MONTH , 10)
//            calendar3.set(Calendar.HOUR_OF_DAY, 10)
//            calendar3.set(Calendar.MINUTE, 0)
//            calendar3.set(Calendar.SECOND,0)
//            //check if the current date is already in the test database
//            val dateCount3 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount3 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest3!!.patientHAMDQ1 = allPatientSelections[0]
//            dummyTest3!!.patientHAMDQ2 = allPatientSelections[1]
//            dummyTest3!!.patientHAMDQ3 = allPatientSelections[2]
//            dummyTest3!!.patientHAMDQ4 = allPatientSelections[3]
//            dummyTest3!!.patientHAMDQ5 = allPatientSelections[4]
//            dummyTest3!!.patientHAMDQ6 = allPatientSelections[5]
//            dummyTest3!!.patientHAMDQ7 = allPatientSelections[6]
//            dummyTest3!!.patientHAMDQ8 = allPatientSelections[7]
//            dummyTest3!!.patientHAMDQ9 = allPatientSelections[8]
//            dummyTest3!!.patientHAMDQ10 = allPatientSelections[9]
//            dummyTest3!!.patientHAMDQ11 = allPatientSelections[10]
//            dummyTest3!!.patientHAMDQ12 = allPatientSelections[11]
//            dummyTest3!!.patientHAMDQ13 = allPatientSelections[12]
//            dummyTest3!!.patientHAMDQ14 = allPatientSelections[13]
//            dummyTest3!!.patientHAMDQ15 = allPatientSelections[14]
//            dummyTest3!!.patientHAMDQ16 = allPatientSelections[15]
//            dummyTest3!!.patientHAMDQ17 = allPatientSelections[16]
//            dummyTest3!!.patientId = patient!!.patientId
//            dummyTest3!!.testDate = calendar3.time
//            dummyTest3!!.testName = "Hammilton Depression"
//            dummyTest3!!.patientHAMDTestResult = 22
//
//            var testId3 : Int = 0
//            if (dateCount3.toInt() == 0)
//            {
//                var testList = realm.where(Test::class.java).findAll()
//                if (testList.size > 0)
//                {
//                    testId3 = testList.get(testList.size -1)!!.testId.toInt()
//                    testId3 += 1
//                    currentTest.testId = testId3.toString()
//                }
//                else
//                {
//                    testId3 = 1
//                    currentTest.testId = testId3.toString()
//                }
//            }
//
//            //add the test to the patient test list
//            var testList3 = ArrayList<Test>()
//            if (patient.listOfTests != null)
//            {
//                for (i in 0 until patient.listOfTests!!.size -1)
//                {
//                    testList3.add(patient.listOfTests!!.get(i)!!)
//                }
//                testList3.add(currentTest)
//                patient.listOfTests = null
//                for (i in 0 until testList3.size -1)
//                {
//                    patient.listOfTests!![i] = testList3.get(i)
//                }
//            }
//            realm.insertOrUpdate(dummyTest3)
//
//            var dummyTest4 = Test()
//            val calendar4: Calendar = Calendar.getInstance()
//            calendar4.set(Calendar.YEAR , 2023)
//            calendar4.set(Calendar.MONTH , 1)
//            calendar4.set(Calendar.DAY_OF_MONTH , 5)
//            calendar4.set(Calendar.HOUR_OF_DAY, 10)
//            calendar4.set(Calendar.MINUTE, 0)
//            calendar4.set(Calendar.SECOND,0)
//            //check if the current date is already in the test database
//            val dateCount4 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount4 > 0)
//            {
//                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }
//
//            dummyTest2!!.patientHAMDQ1 = allPatientSelections[0]
//            dummyTest2!!.patientHAMDQ2 = allPatientSelections[1]
//            dummyTest2!!.patientHAMDQ3 = allPatientSelections[2]
//            dummyTest2!!.patientHAMDQ4 = allPatientSelections[3]
//            dummyTest2!!.patientHAMDQ5 = allPatientSelections[4]
//            dummyTest2!!.patientHAMDQ6 = allPatientSelections[5]
//            dummyTest2!!.patientHAMDQ7 = allPatientSelections[6]
//            dummyTest2!!.patientHAMDQ8 = allPatientSelections[7]
//            dummyTest2!!.patientHAMDQ9 = allPatientSelections[8]
//            dummyTest2!!.patientHAMDQ10 = allPatientSelections[9]
//            dummyTest2!!.patientHAMDQ11 = allPatientSelections[10]
//            dummyTest2!!.patientHAMDQ12 = allPatientSelections[11]
//            dummyTest2!!.patientHAMDQ13 = allPatientSelections[12]
//            dummyTest2!!.patientHAMDQ14 = allPatientSelections[13]
//            dummyTest2!!.patientHAMDQ15 = allPatientSelections[14]
//            dummyTest2!!.patientHAMDQ16 = allPatientSelections[15]
//            dummyTest2!!.patientHAMDQ17 = allPatientSelections[16]
//            dummyTest2!!.patientId = patient!!.patientId
//            dummyTest2!!.testDate = calendar4.time
//            dummyTest2!!.testName = "Hammilton Depression"
//            dummyTest2!!.patientHAMDTestResult = 7
//
//            var testId4 : Int = 0
//            if (dateCount4.toInt() == 0)
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
//            realm.insertOrUpdate(dummyTest4)

//            //update the user record within realm database
//            realm.copyToRealmOrUpdate(patient)
//
//
//            //update the user record within realm database
//            realm.copyToRealmOrUpdate(patient)

        }
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date)
    }

}