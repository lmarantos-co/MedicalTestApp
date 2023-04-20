package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.Fragments.medDietTestFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CheckMedDietTestViewModel : ViewModel(){

    private lateinit var realm : Realm
    private lateinit var mainActivity: MainActivity
    private lateinit var medDietTestFragment: medDietTestFragment
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment
    private var realmDAO = RealmDAO()
    var patientData = MutableLiveData<Patient>()
    var testData = MutableLiveData<Test>()


    fun setActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun setFragment(DietTestFragment: medDietTestFragment)
    {
        medDietTestFragment = DietTestFragment
    }

    fun initialiseRealm()
    {
        Realm.init(mainActivity.applicationContext)
        realm = Realm.getDefaultInstance()
    }

    fun initialiseUserDummy() : Job =
        viewModelScope.launch(Dispatchers.IO) {
            setUserDummyData()
        }


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

    private fun fetchPatientData(username : String) {
        patientData = realmDAO.fetchPatientData(username)
        testData = realmDAO.fetchTestData(patientData.value!!.patientId , "MDS")
        patientData.postValue(patientData.value)
        testData.postValue(testData.value)
    }

    fun checkMDSTestPatient(allPatientSelections : ArrayList<Int?>)
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10) )
            && (checkQuestionForInputError(allPatientSelections[10]  , 11))
           )
        {
            var score : Int = 0
            for (patientValue in allPatientSelections)
            {
                score += patientValue!!
            }
            storePatientData(allPatientSelections , score)
            openResultFragment(score)
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
            testName = "MDS"
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

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "MDS").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testId" , testID).equalTo("testName" , "MDS").findAll()
        }

        return tests!!.get(tests!!.size - 1)!!
    }

    fun openResultFragment(score : Int)
    {
        resultFragment = ResultFragment.newInstance(score.toDouble() , 0.0 ,  5, null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            medDietTestFragment.showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientData(medDietScoreValues : ArrayList<Int?> , score : Int)
    {
        var defaultInstance = Realm.getDefaultInstance()
        defaultInstance.executeTransaction {

            var userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")
            var patient = defaultInstance.where(Patient::class.java).isNotNull("patientId").equalTo("userName", userName).findFirst()

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
            val dateCount = defaultInstance.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount > 0)
            {
                currentTest = defaultInstance.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }
            currentTest!!.patientMDSQ1 = medDietScoreValues.get(0)
            currentTest!!.patientMDSQ2 = medDietScoreValues.get(1)
            currentTest!!.patientMDSQ3 = medDietScoreValues.get(2)
            currentTest!!.patientMDSQ4 = medDietScoreValues.get(3)
            currentTest!!.patientMDSQ5 = medDietScoreValues.get(4)
            currentTest!!.patientMDSQ6 = medDietScoreValues.get(5)
            currentTest!!.patientMDSQ7 = medDietScoreValues.get(6)
            currentTest!!.patientMDSQ8 = medDietScoreValues.get(7)
            currentTest!!.patientMDSQ9 = medDietScoreValues.get(8)
            currentTest!!.patientMDSQ10 = medDietScoreValues.get(9)
            currentTest!!.patientMDSQ11 = medDietScoreValues.get(10)
            currentTest!!.patientId = patient!!.patientId
            currentTest.testDate = calendar.time
            currentTest.testName = "MDS"
            currentTest!!.patientMDSTestResult = score

            var testId : Int = 0
            if (dateCount.toInt() == 0)
            {
                var testList = defaultInstance.where(Test::class.java).findAll()
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
            defaultInstance.insertOrUpdate(currentTest)
            defaultInstance.copyToRealmOrUpdate(patient)


        }
    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }

}