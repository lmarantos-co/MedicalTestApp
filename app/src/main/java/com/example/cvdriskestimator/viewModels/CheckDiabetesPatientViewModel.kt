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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).lessThanOrEqualTo("testDate" , testDate).equalTo("testName" , "DIABETES").findAll()
        }

        return tests!!.get(tests!!.size - 1)!!
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


            var dummytest1 = Test()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //check if the current date is already in the test database
            val calendar2: Calendar = Calendar.getInstance()
            calendar2.set(Calendar.YEAR , 2023)
            calendar2.set(Calendar.MONTH , 2)
            calendar2.set(Calendar.DAY_OF_MONTH , 14)
            calendar2.set(Calendar.HOUR_OF_DAY, 14)
            calendar2.set(Calendar.MINUTE, 0)
            calendar2.set(Calendar.SECOND, 0)
            val dateCount1 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount1 > 0)
//            {
//                dummytest1 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }

            dummytest1.patientAge = age
            dummytest1.patientPAM = pam
            dummytest1.patientSteroids = Steroids
            dummytest1.patientSex = sex
            dummytest1.patientBMI = BMI
            dummytest1.patientSiblings = familyStatus
            dummytest1.smoker = SmokingStatus
            dummytest1.patientId = patient.patientId
            dummytest1.testDate = calendar2.time
            dummytest1.testId = (currentTest.testId.toInt() + 1).toString()
            dummytest1.diabetesTestResult = 15.0.toDouble()
            dummytest1.testName = "DIABETES"

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

            var listOfTest1 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    listOfTest1[i] = patient.listOfTests!!.get(i)!!
                }
                patient.listOfTests = null
                listOfTest1.add(dummytest1)
                for (i in 0 until listOfTest1.size -1)
                {
                    patient.listOfTests?.set(i, listOfTest1.get(i))
                }
            }
            realm.insertOrUpdate(dummytest1)

            var dummytest2 = Test()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //check if the current date is already in the test database
            val calendar3: Calendar = Calendar.getInstance()
            calendar3.set(Calendar.YEAR , 2023)
            calendar3.set(Calendar.MONTH , 2)
            calendar3.set(Calendar.DAY_OF_MONTH , 14)
            calendar3.set(Calendar.HOUR_OF_DAY, 14)
            calendar3.set(Calendar.MINUTE, 0)
            calendar3.set(Calendar.SECOND, 0)
//            val dateCount2 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount2 > 0)
//            {
//                dummytest2 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }

            dummytest2.patientAge = age
            dummytest2.patientPAM = pam
            dummytest2.patientSteroids = Steroids
            dummytest2.patientSex = sex
            dummytest2.patientBMI = BMI
            dummytest2.patientSiblings = familyStatus
            dummytest2.smoker = SmokingStatus
            dummytest2.patientId = patient.patientId
            dummytest2.testDate = calendar3.time
            dummytest2.testId = (dummytest1.testId.toInt() + 1).toString()
            dummytest2.diabetesTestResult = 8.0.toDouble()
            dummytest2.testName = "DIABETES"

            var testId3 : Int = 0
//            if (dateCount2.toInt() == 0)
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

            var listOfTest3 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    listOfTest3[i] = patient.listOfTests!!.get(i)!!
                }
                patient.listOfTests = null
                listOfTest3.add(currentTest)
                for (i in 0 until listOfTest3.size -1)
                {
                    patient.listOfTests?.set(i, listOfTest3.get(i))
                }
            }
            realm.insertOrUpdate(dummytest2)

            var dummytest3 = Test()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //check if the current date is already in the test database
            val calendar4: Calendar = Calendar.getInstance()
            calendar4.set(Calendar.YEAR , 2023)
            calendar4.set(Calendar.MONTH , 1)
            calendar4.set(Calendar.DAY_OF_MONTH , 14)
            calendar4.set(Calendar.HOUR_OF_DAY, 14)
            calendar4.set(Calendar.MINUTE, 0)
            calendar4.set(Calendar.SECOND, 0)
//            val dateCount3 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount3 > 0)
//            {
//                dummytest3 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }

            dummytest3.patientAge = age
            dummytest3.patientPAM = pam
            dummytest3.patientSteroids = Steroids
            dummytest3.patientSex = sex
            dummytest3.patientBMI = BMI
            dummytest3.patientSiblings = familyStatus
            dummytest3.smoker = SmokingStatus
            dummytest3.patientId = patient.patientId
            dummytest3.testDate = calendar4.time
            dummytest3.testId = (dummytest2.testId.toInt() + 1).toString()
            dummytest3.diabetesTestResult = 7.0.toDouble()
            dummytest3.testName = "DIABETES"

            var testId4 : Int = 0
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

            var listOfTest4 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    listOfTest4[i] = patient.listOfTests!!.get(i)!!
                }
                patient.listOfTests = null
                listOfTest4.add(currentTest)
                for (i in 0 until listOfTest4.size -1)
                {
                    patient.listOfTests?.set(i, listOfTest4.get(i))
                }
            }
            realm.insertOrUpdate(dummytest3)

            var dummytest4 = Test()
//            var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //check if the current date is already in the test database
            val calendar5: Calendar = Calendar.getInstance()
            calendar5.set(Calendar.YEAR , 2022)
            calendar5.set(Calendar.MONTH , 8)
            calendar5.set(Calendar.DAY_OF_MONTH , 14)
            calendar5.set(Calendar.HOUR_OF_DAY, 14)
            calendar5.set(Calendar.MINUTE, 0)
            calendar5.set(Calendar.SECOND, 0)
//            val dateCount4 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
//            if (dateCount4 > 0)
//            {
//                dummytest3 = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
//            }

            dummytest4.patientAge = age
            dummytest4.patientPAM = pam
            dummytest4.patientSteroids = Steroids
            dummytest4.patientSex = sex
            dummytest4.patientBMI = BMI
            dummytest4.patientSiblings = familyStatus
            dummytest4.smoker = SmokingStatus
            dummytest4.patientId = patient.patientId
            dummytest4.testDate = calendar5.time
            dummytest4.testId = (dummytest3.testId.toInt() + 1).toString()
            dummytest4.diabetesTestResult = 5.0.toDouble()
            dummytest4.testName = "DIABETES"

            var testId2 : Int = 0
//            if (dateCount4.toInt() == 0)
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

            var listOfTest2 = ArrayList<Test>()
            if (patient.listOfTests != null)
            {
                for (i in 0 until patient.listOfTests!!.size -1)
                {
                    listOfTest2[i] = patient.listOfTests!!.get(i)!!
                }
                patient.listOfTests = null
                listOfTest2.add(currentTest)
                for (i in 0 until listOfTest2.size -1)
                {
                    patient.listOfTests?.set(i, listOfTest2.get(i))
                }
            }
            realm.insertOrUpdate(dummytest4)

            realm.insertOrUpdate(patient)

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