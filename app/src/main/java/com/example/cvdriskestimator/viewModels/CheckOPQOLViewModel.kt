package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.BAICheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.OPQOLCheckFragment
import com.example.cvdriskestimator.Fragments.OPQOLCheckFragment2
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.BAITestEstimator
import com.example.cvdriskestimator.MedicalTestAlgorithms.OPQOLTestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Calendar

class CheckOPQOLViewModel : ViewModel()
{
    private lateinit var mainActivity: MainActivity
    private lateinit var opqolCheckFragment: OPQOLCheckFragment
    private lateinit var opqolCheckFragment2: OPQOLCheckFragment2
    private lateinit var realm : Realm
    private lateinit var opqolTestEstimator : OPQOLTestEstimator
    private var realmDAO = RealmDAO()
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var allPatientSelections : ArrayList<Int>
    var patientData = MutableLiveData<Patient>()
    var testDATA = MutableLiveData<Test>()


    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun passFragment(qolCheckFragment: OPQOLCheckFragment)
    {
        opqolCheckFragment = qolCheckFragment
    }

    fun passFragment2(qolCheckFragment2: OPQOLCheckFragment2)
    {
        opqolCheckFragment2 = qolCheckFragment2
    }

    fun passAllPatientSelections(answers : ArrayList<Int>)
    {
        allPatientSelections = answers
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "OPQOL Test")
        patientData.postValue(patientData.value)
        testDATA.postValue(testDATA.value)
    }

    //check all opqol questions for proper user input
    fun checkOPQOLTestPatient1(allPatientSelections : ArrayList<Int?>) : Boolean
    {
        return(checkQuestionForInputError(allPatientSelections.get(0) , 1))
                &&
                (checkQuestionForInputError(allPatientSelections.get(1) , 2))
                &&
                (checkQuestionForInputError(allPatientSelections.get(2) , 3))
                &&
                (checkQuestionForInputError(allPatientSelections.get(3) , 4))
                &&
                (checkQuestionForInputError(allPatientSelections.get(4) , 5))
                &&
                (checkQuestionForInputError(allPatientSelections.get(5) , 6))
                &&
                (checkQuestionForInputError(allPatientSelections.get(6) , 7))
                &&
                (checkQuestionForInputError(allPatientSelections.get(7) , 8))
                &&
                (checkQuestionForInputError(allPatientSelections.get(8) , 9))
                &&
                (checkQuestionForInputError(allPatientSelections.get(9) , 10))
                &&
                (checkQuestionForInputError(allPatientSelections.get(10) , 11))
                &&
                (checkQuestionForInputError(allPatientSelections.get(11) , 12))
                &&
                (checkQuestionForInputError(allPatientSelections.get(12) , 13))
                &&
                (checkQuestionForInputError(allPatientSelections.get(13) , 14))
                &&
                (checkQuestionForInputError(allPatientSelections.get(14) , 15))
                &&
                (checkQuestionForInputError(allPatientSelections.get(15) , 16))
                &&
                (checkQuestionForInputError(allPatientSelections.get(16) , 17))
    }

    fun checkOPQOLTestPatient2(allPatientSelections : ArrayList<Int?>) : Boolean
    {
        return (checkQuestionForInputError2(allPatientSelections.get(0) , 18)
            && (checkQuestionForInputError2(allPatientSelections.get(1) , 19))
            &&
            (checkQuestionForInputError(allPatientSelections.get(2) , 20))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(3) , 21))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(4) , 22))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(5) , 23))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(6) , 24))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(7) , 25))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(8) , 26))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(9) , 27))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(10) , 28))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(11) , 29))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(12) , 30))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(13) , 31))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(14) , 32))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(15) , 33))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(16) , 34))
            &&
            (checkQuestionForInputError2(allPatientSelections.get(17) , 35)))
    }

    fun openResultFragment()
    {
        opqolTestEstimator = OPQOLTestEstimator(allPatientSelections)
        val result = opqolTestEstimator.opqolTestEstimator()
        storePatientOnRealm(allPatientSelections , result)
        resultFragment = ResultFragment.newInstance(result.toDouble() , 0.0 ,  13, null , allPatientSelections)
        mainActivity.fragmentTransaction(resultFragment)
    }

    fun history()
    {
        var patientId : String = ""
        var testName : String = ""
        realm.executeTransaction {
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            var patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()
            patientId = patient!!.patientId
            testName = "OPQOL Test"
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

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "Beck Anxiety Index").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testId" , testID).equalTo("testName" , "OPQOL Test").findAll()
        }

        return tests!!.get(tests!!.size -1)!!
    }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int> , score : Int)
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

            currentTest!!.patientOPQOLQ1 = allPatientSelections[0]
            currentTest!!.patientOPQOLQ2 = allPatientSelections[1]
            currentTest!!.patientOPQOLQ3 = allPatientSelections[2]
            currentTest!!.patientOPQOLQ4 = allPatientSelections[3]
            currentTest!!.patientOPQOLQ5 = allPatientSelections[4]
            currentTest!!.patientOPQOLQ6 = allPatientSelections[5]
            currentTest!!.patientOPQOLQ7 = allPatientSelections[6]
            currentTest!!.patientOPQOLQ8 = allPatientSelections[7]
            currentTest!!.patientOPQOLQ9 = allPatientSelections[8]
            currentTest!!.patientOPQOLQ10 = allPatientSelections[9]
            currentTest!!.patientOPQOLQ11 = allPatientSelections[10]
            currentTest!!.patientOPQOLQ12 = allPatientSelections[11]
            currentTest!!.patientOPQOLQ13 = allPatientSelections[12]
            currentTest!!.patientOPQOLQ14 = allPatientSelections[13]
            currentTest!!.patientOPQOLQ15 = allPatientSelections[14]
            currentTest!!.patientOPQOLQ16 = allPatientSelections[15]
            currentTest!!.patientOPQOLQ17 = allPatientSelections[16]
            currentTest!!.patientOPQOLQ18 = allPatientSelections[17]
            currentTest!!.patientOPQOLQ19 = allPatientSelections[18]
            currentTest!!.patientOPQOLQ20 = allPatientSelections[19]
            currentTest!!.patientOPQOLQ21 = allPatientSelections[20]
            currentTest!!.patientOPQOLQ22 = allPatientSelections[21]
            currentTest!!.patientOPQOLQ23 = allPatientSelections[22]
            currentTest!!.patientOPQOLQ24 = allPatientSelections[23]
            currentTest!!.patientOPQOLQ25 = allPatientSelections[24]
            currentTest!!.patientOPQOLQ26 = allPatientSelections[25]
            currentTest!!.patientOPQOLQ27 = allPatientSelections[26]
            currentTest!!.patientOPQOLQ28 = allPatientSelections[27]
            currentTest!!.patientOPQOLQ29 = allPatientSelections[28]
            currentTest!!.patientOPQOLQ30 = allPatientSelections[29]
            currentTest!!.patientOPQOLQ31 = allPatientSelections[30]
            currentTest!!.patientOPQOLQ32 = allPatientSelections[31]
            currentTest!!.patientOPQOLQ33 = allPatientSelections[32]
            currentTest!!.patientOPQOLQ34 = allPatientSelections[33]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.testDate = calendar.time
            currentTest!!.testName = "OPQOL Test"
            currentTest!!.OPQOLTestReesult = score

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



        }
    }


    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            opqolCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO , questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun checkQuestionForInputError2(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            opqolCheckFragment2.showSelectionError("Please select an answer for question No : " + questionNO , questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int> , score : Int) : Job =
        viewModelScope.launch{
            storePatientOnDB(allPatientSelections , score)
        }

}