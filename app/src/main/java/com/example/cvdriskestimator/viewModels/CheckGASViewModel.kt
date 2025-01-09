package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.GASCheckFragment1
import com.example.cvdriskestimator.Fragments.GASCheckFragment2
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
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
import java.util.Calendar
import java.util.Date

class CheckGASViewModel : ViewModel() {

    private lateinit var mainActivity : MainActivity
    private lateinit var gasCheckFragment: GASCheckFragment1
    private lateinit var gasCheckFragment2: GASCheckFragment2
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

    fun passFragment(fragment : GASCheckFragment1)
    {
        gasCheckFragment = fragment
    }

    fun passFragmen2(fragment : GASCheckFragment2)
    {
        gasCheckFragment2 = fragment
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
        testData = realmDAO.fetchTestData(patientData.value!!.patientId ,  "GAS")
        patientData.postValue(patientData.value)
        testData.postValue(testData.value)
    }

    fun checkGASTestPatient1(allPatientSelections : ArrayList<Int?>) : Boolean
    {
        if ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
            && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
            && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
            && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
            && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10))
            && (checkQuestionForInputError(allPatientSelections[10] , 11) && (checkQuestionForInputError(allPatientSelections[11] , 12)))
            && (checkQuestionForInputError(allPatientSelections[12] , 13) && (checkQuestionForInputError(allPatientSelections[13] , 14)))
            && (checkQuestionForInputError(allPatientSelections[14] , 15)))
        {
//            val result = mdiTestEstimator.calculateGAS(allPatientSelections)
//            storePatientOnRealm(allPatientSelections , result)
//            openResultFragment(result)
            return true
        }
        else
        {
            return false
        }
    }

    fun checkGASTestPatient2(allPatientSelections : ArrayList<Int?>) : Boolean
    {
        if ((checkQuestionForInputError2(allPatientSelections[15] , 16)) && (checkQuestionForInputError2(allPatientSelections[16]  , 17))
            && (checkQuestionForInputError2(allPatientSelections[17]  , 18)) && (checkQuestionForInputError2(allPatientSelections[18]  , 19))
            && (checkQuestionForInputError2(allPatientSelections[19]  , 20)) && (checkQuestionForInputError2(allPatientSelections[20]  , 21))
            && (checkQuestionForInputError2(allPatientSelections[21]  , 22)) && (checkQuestionForInputError2(allPatientSelections[22]  , 23))
            && (checkQuestionForInputError2(allPatientSelections[23]  , 24)) && (checkQuestionForInputError2(allPatientSelections[24]  , 25))
            && (checkQuestionForInputError2(allPatientSelections[25] , 26) && (checkQuestionForInputError2(allPatientSelections[26] , 27)))
            && (checkQuestionForInputError2(allPatientSelections[27] , 28) && (checkQuestionForInputError2(allPatientSelections[28] , 29)))
            && (checkQuestionForInputError2(allPatientSelections[29] , 30)))
        {
            val result = mdiTestEstimator.calculateGAS(allPatientSelections)
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
            return true
        }
        else
        {
            return false
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
            testName = "GAS"
        }
        val bundle = Bundle()
        bundle.putString("patientId" , patientId)
        bundle.putString("testName" , testName)
        historyFragment = HistoryFragment()
        historyFragment.arguments = bundle
        mainActivity.fragmentTransaction(historyFragment)
    }

    fun fetchHistoryTest(patientId : String, testDate : String) : Test
    {
        var tests : RealmResults<Test>? = null
        realm.executeTransaction {

            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testId" , testDate).equalTo("testName" , "GAS").findAll()
        }

        return tests!!.get(tests!!.size -1)!!
    }

    private fun openResultFragment(testResult : ArrayList<Int>)
    {
        resultFragment = ResultFragment.newInstance(0.0 , 0.0 , 14 , null , testResult)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            gasCheckFragment.showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
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
            gasCheckFragment2.showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?> , result : ArrayList<Int>) : Job =
        viewModelScope.launch{
            storePatientOnDB(allPatientSelections , result)
        }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?> , result : ArrayList<Int>)
    {
        //execute transaction on realm
        realm.executeTransaction {

            //store the patient data on the database
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            val patient = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , username).findFirst()
            var currentTest = Test()
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
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , calendar.time).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar.time).findFirst()!!
            }

            currentTest!!.patientGASQ1 = allPatientSelections[0]
            currentTest!!.patientGASQ2 = allPatientSelections[1]
            currentTest!!.patientGASQ3 = allPatientSelections[2]
            currentTest!!.patientGASQ4 = allPatientSelections[3]
            currentTest!!.patientGASQ5 = allPatientSelections[4]
            currentTest!!.patientGASQ6 = allPatientSelections[5]
            currentTest!!.patientGASQ7 = allPatientSelections[6]
            currentTest!!.patientGASQ8 = allPatientSelections[7]
            currentTest!!.patientGASQ9 = allPatientSelections[8]
            currentTest!!.patientGASQ10 = allPatientSelections[9]
            currentTest!!.patientGASQ11 = allPatientSelections[10]
            currentTest!!.patientGASQ12 = allPatientSelections[11]
            currentTest!!.patientGASQ13 = allPatientSelections[12]
            currentTest!!.patientGASQ14 = allPatientSelections[13]
            currentTest!!.patientGASQ15 = allPatientSelections[14]
            currentTest!!.patientGASQ16 = allPatientSelections[15]
            currentTest!!.patientGASQ17 = allPatientSelections[16]
            currentTest!!.patientGASQ18 = allPatientSelections[17]
            currentTest!!.patientGASQ19 = allPatientSelections[18]
            currentTest!!.patientGASQ20 = allPatientSelections[19]
            currentTest!!.patientGASQ21 = allPatientSelections[20]
            currentTest!!.patientGASQ22 = allPatientSelections[21]
            currentTest!!.patientGASQ23 = allPatientSelections[22]
            currentTest!!.patientGASQ24 = allPatientSelections[23]
            currentTest!!.patientGASQ25 = allPatientSelections[24]
            currentTest!!.patientGASQ26 = allPatientSelections[25]
            currentTest!!.patientGASQ27 = allPatientSelections[26]
            currentTest!!.patientGASQ28 = allPatientSelections[27]
            currentTest!!.patientGASQ29 = allPatientSelections[28]
            currentTest!!.patientGASQ30 = allPatientSelections[29]
            currentTest!!.patientGASTestResult = result[0] + result[1] + result[2]
            currentTest!!.patientGASSomatic = result[0]
            currentTest!!.patientGASCognitive = result[1]
            currentTest!!.patientGASCognitive = result[2]

            currentTest!!.patientId = patient!!.patientId
            currentTest.testDate = calendar.time
            currentTest.testName = "GAS"

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


        }
    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }

}