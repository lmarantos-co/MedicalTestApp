package com.example.cvdriskestimator.viewModels


import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.DASSCheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.DASSTestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CheckDASSPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var dassCheckFragment: DASSCheckFragment
    private lateinit var realm : Realm
    private lateinit var dassTestEstimator : DASSTestEstimator
    private var realmDAO = RealmDAO()
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment

    var patientData = MutableLiveData<Patient>()
    var testDATA = MutableLiveData<Test>()


    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun passFragment(DASSCheckFragment: DASSCheckFragment)
    {
        dassCheckFragment = DASSCheckFragment
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "DASS")
        patientData.postValue(patientData.value)
        testDATA.postValue(testDATA.value)
    }

    fun checkDASSTestPatient(allPatientSelections : ArrayList<Int?>)
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
            dassTestEstimator = DASSTestEstimator(allPatientSelections)
            val result = dassTestEstimator.DASSScorEstimator()
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
        }
    }

    private fun openResultFragment(testResult : Triple<Int , Int , Int>)
    {
        resultFragment = ResultFragment.newInstance(testResult.first.toDouble() , testResult.second.toDouble() ,  11 , testResult.third.toDouble())
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            dassCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO , questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?> , score : Triple<Int , Int , Int>) : Job =
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
            testName = "DASS"
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

            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "DASS").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).lessThanOrEqualTo("testDate" , testDate).equalTo("testName" , "DASS").findAll()
        }

        return tests!!.get(tests!!.size -1)!!
    }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?> , score : Triple<Int , Int , Int>)
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

            currentTest!!.patientDASSQ1 = allPatientSelections[0]
            currentTest!!.patientDASSQ2 = allPatientSelections[1]
            currentTest!!.patientDASSQ3 = allPatientSelections[2]
            currentTest!!.patientDASSQ4 = allPatientSelections[3]
            currentTest!!.patientDASSQ5 = allPatientSelections[4]
            currentTest!!.patientDASSQ6 = allPatientSelections[5]
            currentTest!!.patientDASSQ7 = allPatientSelections[6]
            currentTest!!.patientDASSQ8 = allPatientSelections[7]
            currentTest!!.patientDASSQ9 = allPatientSelections[8]
            currentTest!!.patientDASSQ10 = allPatientSelections[9]
            currentTest!!.patientDASSQ11 = allPatientSelections[10]
            currentTest!!.patientDASSQ12 = allPatientSelections[11]
            currentTest!!.patientDASSQ13 = allPatientSelections[12]
            currentTest!!.patientDASSQ14 = allPatientSelections[13]
            currentTest!!.patientDASSQ15 = allPatientSelections[14]
            currentTest!!.patientDASSQ16 = allPatientSelections[15]
            currentTest!!.patientDASSQ17 = allPatientSelections[16]
            currentTest!!.patientDASSQ18 = allPatientSelections[17]
            currentTest!!.patientDASSQ19 = allPatientSelections[18]
            currentTest!!.patientDASSQ20 = allPatientSelections[19]
            currentTest!!.patientDASSQ21 = allPatientSelections[20]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.testDate = calendar.time
            currentTest!!.testName = "DASS"
            currentTest!!.dassAnxietyResult = score.first
            currentTest!!.dassDepressionResult = score.second
            currentTest!!.dassStressResult = score.third

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

            var dummytest1 = Test()
//            val date = Date()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            val calendar2: Calendar = Calendar.getInstance()
            calendar2.set(Calendar.YEAR , 2023)
            calendar2.set(Calendar.MONTH , 1)
            calendar2.set(Calendar.DAY_OF_MONTH , 10)
            calendar2.set(Calendar.HOUR_OF_DAY, 18)
            calendar2.set(Calendar.MINUTE, 0)
            calendar2.set(Calendar.SECOND, 0)
            //check if the current date is already in the test database
            val dateCount1 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount1 > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            dummytest1!!.patientDASSQ1 = 3
            dummytest1!!.patientDASSQ2 = 2
            dummytest1!!.patientDASSQ3 = 1
            dummytest1!!.patientDASSQ4 = 2
            dummytest1!!.patientDASSQ5 = 1
            dummytest1!!.patientDASSQ6 = 2
            dummytest1!!.patientDASSQ7 = 3
            dummytest1!!.patientDASSQ8 = 2
            dummytest1!!.patientDASSQ9 = 1
            dummytest1!!.patientDASSQ10 = 3
            dummytest1!!.patientDASSQ11 = 2
            dummytest1!!.patientDASSQ12 = 3
            dummytest1!!.patientDASSQ13 = 3
            dummytest1!!.patientDASSQ14 = 2
            dummytest1!!.patientDASSQ15 = 1
            dummytest1!!.patientDASSQ16 = 2
            dummytest1!!.patientDASSQ17 = 3
            dummytest1!!.patientDASSQ18 = 2
            dummytest1!!.patientDASSQ19 = 1
            dummytest1!!.patientDASSQ20 = 3
            dummytest1!!.patientDASSQ21 = 2
            dummytest1!!.patientId = patient!!.patientId
            dummytest1!!.testDate = calendar2.time
            dummytest1!!.testName = "DASS"
            dummytest1!!.testId = (currentTest.testId.toInt() + 1).toString()
            dummytest1!!.dassAnxietyResult = 7
            dummytest1!!.dassDepressionResult = 12
            dummytest1!!.dassStressResult = 15

            var testId1 : Int = 0
            if (dateCount1.toInt() == 0)
            {
                var testList = realm.where(Test::class.java).findAll()
                if (testList.size > 0)
                {
                    testId1 = testList.get(testList.size -1)!!.testId.toInt()
                    testId1 += 1
                    currentTest.testId = testId1.toString()
                }
                else
                {
                    testId1 = 1
                    currentTest.testId = testId1.toString()
                }
            }

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
            realm.insertOrUpdate(dummytest1)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

            var dummytest2 = Test()
//            val date = Date()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            val calendar3: Calendar = Calendar.getInstance()
            calendar3.set(Calendar.YEAR , 2022)
            calendar3.set(Calendar.MONTH , 10)
            calendar3.set(Calendar.DAY_OF_MONTH , 10)
            calendar3.set(Calendar.HOUR_OF_DAY, 18)
            calendar3.set(Calendar.MINUTE, 0)
            calendar3.set(Calendar.SECOND, 0)
            //check if the current date is already in the test database
            val dateCount2 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount2 > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            dummytest2!!.patientDASSQ1 = 3
            dummytest2!!.patientDASSQ2 = 2
            dummytest2!!.patientDASSQ3 = 1
            dummytest2!!.patientDASSQ4 = 2
            dummytest2!!.patientDASSQ5 = 1
            dummytest2!!.patientDASSQ6 = 2
            dummytest2!!.patientDASSQ7 = 3
            dummytest2!!.patientDASSQ8 = 2
            dummytest2!!.patientDASSQ9 = 1
            dummytest2!!.patientDASSQ10 = 3
            dummytest2!!.patientDASSQ11 = 2
            dummytest2!!.patientDASSQ12 = 3
            dummytest2!!.patientDASSQ13 = 3
            dummytest2!!.patientDASSQ14 = 2
            dummytest2!!.patientDASSQ15 = 1
            dummytest2!!.patientDASSQ16 = 2
            dummytest2!!.patientDASSQ17 = 3
            dummytest2!!.patientDASSQ18 = 2
            dummytest2!!.patientDASSQ19 = 1
            dummytest2!!.patientDASSQ20 = 3
            dummytest2!!.patientDASSQ21 = 2
            dummytest2!!.patientId = patient!!.patientId
            dummytest2!!.testDate = calendar3.time
            dummytest2!!.testName = "DASS"
            dummytest2!!.testId = (dummytest1.testId.toInt() + 1).toString()
            dummytest2!!.dassAnxietyResult = 7
            dummytest2!!.dassDepressionResult = 12
            dummytest2!!.dassStressResult = 15

            var testId2 : Int = 0
            if (dateCount2.toInt() == 0)
            {
                var testList = realm.where(Test::class.java).findAll()
                if (testList.size > 0)
                {
                    testId2 = testList.get(testList.size -1)!!.testId.toInt()
                    testId2 += 1
                    currentTest.testId = testId2.toString()
                }
                else
                {
                    testId2 = 1
                    currentTest.testId = testId2.toString()
                }
            }

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
            realm.insertOrUpdate(dummytest2)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

            var dummytest3 = Test()
//            val date = Date()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            val calendar4: Calendar = Calendar.getInstance()
            calendar4.set(Calendar.YEAR , 2022)
            calendar4.set(Calendar.MONTH , 9)
            calendar4.set(Calendar.DAY_OF_MONTH , 10)
            calendar4.set(Calendar.HOUR_OF_DAY, 18)
            calendar4.set(Calendar.MINUTE, 0)
            calendar4.set(Calendar.SECOND, 0)
            //check if the current date is already in the test database
            val dateCount3 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount3 > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            dummytest3!!.patientDASSQ1 = 3
            dummytest3!!.patientDASSQ2 = 2
            dummytest3!!.patientDASSQ3 = 1
            dummytest3!!.patientDASSQ4 = 2
            dummytest3!!.patientDASSQ5 = 1
            dummytest3!!.patientDASSQ6 = 2
            dummytest3!!.patientDASSQ7 = 3
            dummytest3!!.patientDASSQ8 = 2
            dummytest3!!.patientDASSQ9 = 1
            dummytest3!!.patientDASSQ10 = 3
            dummytest3!!.patientDASSQ11 = 2
            dummytest3!!.patientDASSQ12 = 3
            dummytest3!!.patientDASSQ13 = 3
            dummytest3!!.patientDASSQ14 = 2
            dummytest3!!.patientDASSQ15 = 1
            dummytest3!!.patientDASSQ16 = 2
            dummytest3!!.patientDASSQ17 = 3
            dummytest3!!.patientDASSQ18 = 2
            dummytest3!!.patientDASSQ19 = 1
            dummytest3!!.patientDASSQ20 = 3
            dummytest3!!.patientDASSQ21 = 2
            dummytest3!!.patientId = patient!!.patientId
            dummytest3!!.testDate = calendar3.time
            dummytest3!!.testName = "DASS"
            dummytest3!!.testId = (dummytest2.testId.toInt() + 1).toString()
            dummytest3!!.dassAnxietyResult = 21
            dummytest3!!.dassDepressionResult = 18
            dummytest3!!.dassStressResult = 15

            var testId3 : Int = 0
            if (dateCount3.toInt() == 0)
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
            realm.insertOrUpdate(dummytest2)
            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)

        }
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date)
    }

}