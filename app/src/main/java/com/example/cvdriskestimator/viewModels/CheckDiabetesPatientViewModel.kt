package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.DiabetesCheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.Diabetes2Estimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CheckDiabetesPatientViewModel : ViewModel() , Observable {

    private lateinit var mainActivity: MainActivity
    private lateinit var diabetesCheckFragment: DiabetesCheckFragment
    private  var diabetes2Estimator = Diabetes2Estimator()
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var realm: Realm
    private lateinit var realmDAO : RealmDAO


    // Mutable Live Data for Patient
    var patientDATA = MutableLiveData<Patient>()
    var testDATA = MutableLiveData<Test>()


    fun setMainActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun setDiabetesCheckFragment(checkFragment: DiabetesCheckFragment)
    {
        diabetesCheckFragment = checkFragment
    }

    fun initRealm()
    {
        Realm.init(mainActivity.applicationContext)
        realm = Realm.getDefaultInstance()
        realmDAO = RealmDAO()
    }

    fun setPatientDataOnForm()
    {
        fetchPatientRecord()
    }

    fun setUserDummyData() : Job =
        viewModelScope.launch {
            initialiseUserDummy()
        }

    fun initialiseUserDummy() {
        realm.executeTransaction {
            var dummyPatient : Patient? =  realm.where(Patient::class.java).isNotNull("patientId") .equalTo("userName" , "tempUser").findFirst()
            if (dummyPatient == null)
            {
                val patient = Patient()
                patient.userName = "tempUser"
                patient.password = "dummy#1"
                realm.insertOrUpdate(patient)
            }

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
            testName = "DIABETES"
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

            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "DIABETES").findAll()
            var dummyTest = dummyTestList.get(dummyTestList.size -1)
            var dummyTestDate = Calendar.getInstance()
            if (testDate.day > 1)
            {
                dummyTestDate.set(Calendar.YEAR , testDate.year  + 1900)
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).greaterThanOrEqualTo("testDate" , testDate).equalTo("testName" , "DIABETES").findAll()
        }

        return tests!!.get(tests!!.size -1)!!
    }

    private fun checkAgeAndBMI(age : String,  BMI : String) : Boolean {
        var ageCorrect : Boolean = true
        var bmiCorrect : Boolean = true
        //check age for correct input
        if (age.equals(""))
        {
            ageCorrect = ageCorrect && false
            diabetesCheckFragment.displayAgeError("You must input a value for the age.")
        }
        else
        {
            if ((age.toInt() > 0) && (age.toInt() < 120))
            {
                ageCorrect = ageCorrect && true
            }
            else
            {
                diabetesCheckFragment.displayAgeError("Age must be between 1 and 120 years old.")
            }
        }

        if (BMI.equals(""))
        {
            bmiCorrect = bmiCorrect && false
            diabetesCheckFragment.displayBMIError("You must input a value for the BMI.")
        }
        else
        {
            if ((BMI.toDouble() > 10) && (BMI.toDouble() < 50))
            {
                bmiCorrect = bmiCorrect && true
            }
            else
            {
                bmiCorrect = bmiCorrect && false
                diabetesCheckFragment.displayBMIError("BMI must be between 10 and 50.")
            }
        }
        return (ageCorrect && bmiCorrect)
    }


//    private fun checkPatientSex(sex : String) : Boolean
//    {
//        var correctSex : Boolean
//        correctSex = (sex == "MALE") && (sex = "FEMALE")
//        if (correctSex == false)
//            diabetesCheckFragment.displaySexError("Please input your Sex.")
//    }

    fun checkPatientForDiabetesData(sex : String , pam : String, Steroids : String , age : String , BMI : String , familyStatus : String , SmokingStatus : String)
    {
        var result : Double = 0.0
        if (checkAgeAndBMI(age , BMI)) {
            result = diabetes2Estimator.calculateDiabetes2Risk(
                sex,
                pam,
                Steroids,
                age,
                BMI,
                familyStatus,
                SmokingStatus
            )
            registerOrUpdatePatient(sex, pam, Steroids, age, BMI, familyStatus, SmokingStatus, result)
            Log.d(
                "DIABETES",
                result.toString()
            )
            resultFragment = ResultFragment.newInstance(result , 0.0 , 2 , null)
            mainActivity.fragmentTransaction(resultFragment)
        }
    }

    private  fun fetchPatientRecord() {
        val prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
        val username = prefs.getString("userName" , "tempUser")
        patientDATA = realmDAO.fetchPatientData(username!!)
        testDATA = realmDAO.fetchTestData(patientDATA.value!!.patientId , "DIABETES")
        patientDATA.postValue(patientDATA.value)
        testDATA.postValue(testDATA.value)
    }

    fun registerOrUpdatePatient(sex : String , pam : String, Steroids : String , age : String , BMI : String , familyStatus : String , SmokingStatus : String , result : Double) : Job =
        viewModelScope.launch {
            registerPatient(sex , pam , Steroids , age , BMI  , familyStatus  , SmokingStatus , result)
    }

    private fun registerPatient(sex : String, pam : String, Steroids : String, age : String, BMI : String, familyStatus : String, SmokingStatus : String , result : Double)
    {
        var prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
        var userName = prefs.getString("userName", "tempUser")
        realm.executeTransaction {
            var patient : Patient = realm.where(Patient::class.java).equalTo("userName" , userName).findFirst()!!

            var currentTest = Test()
            val date = Date()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //check if the current date is already in the test database
            val calendar: Calendar = Calendar.getInstance()
//            calendar.set(Calendar.YEAR , date.year)
//            calendar.set(Calendar.MONTH , date.month)
//            calendar.set(Calendar.DAY_OF_MONTH , date.day)
    //        calendar.set(Calendar.HOUR_OF_DAY, date.hours)
    //        calendar.set(Calendar.MINUTE, date.minutes)
    //        calendar.set(Calendar.SECOND, date.seconds)
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            currentTest.patientAge = age
            currentTest.patientPAM = pam
            currentTest.patientSteroids = Steroids
            currentTest.patientSex = sex
            currentTest.patientBMI = BMI
            currentTest.patientSiblings = familyStatus
            currentTest.smoker = SmokingStatus
            currentTest.patientId = patient.patientId
            currentTest.testDate = calendar.time
            currentTest.diabetesTestResult = result
            currentTest.testName = "DIABETES"

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

            var listOfTest = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    listOfTest[i] = patient.listOfTests!!.get(i)!!
                }
                patient.listOfTests = null
                listOfTest.add(currentTest)
                for (i in 0 until listOfTest.size -1)
                {
                    patient.listOfTests?.set(i, listOfTest.get(i))
                }
            }
            realm.insertOrUpdate(currentTest)

            realm.insertOrUpdate(patient)
         }
    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}