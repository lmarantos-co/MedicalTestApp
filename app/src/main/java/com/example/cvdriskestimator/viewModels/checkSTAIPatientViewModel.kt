package com.example.cvdriskestimator.viewModels


import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.Fragments.STAICheckFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.StaiTestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CheckSTAIPatientViewModel : ViewModel() {


    private lateinit var mainActivity: MainActivity
    private lateinit var staiCheckFragment: STAICheckFragment
    private lateinit var realm : Realm
    private lateinit var staiTestEstimator : StaiTestEstimator
    private var realmDAO = RealmDAO()
    private lateinit var resultFragment: ResultFragment
    private lateinit var historyFragment: HistoryFragment

    var patientData = MutableLiveData<Patient>()
    var testDATA = MutableLiveData<Test>()


    fun passActivity(activity: MainActivity)
    {
        mainActivity = activity
    }

    fun passFragment(sTAICheckFragment: STAICheckFragment)
    {
        staiCheckFragment = sTAICheckFragment
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
        testDATA = realmDAO.fetchTestData(patientData.value!!.patientId , "STAI")
        patientData.postValue(patientData.value)
        testDATA.postValue(testDATA.value)
    }

    fun checkSTAITestPatient(allPatientSelections : ArrayList<Int?>)
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
            && (checkQuestionForInputError(allPatientSelections[20] , 21)) && (checkQuestionForInputError(allPatientSelections[21]  , 22))
            && (checkQuestionForInputError(allPatientSelections[22]  , 23)) && (checkQuestionForInputError(allPatientSelections[23]  , 24))
            && (checkQuestionForInputError(allPatientSelections[24]  , 25)) && (checkQuestionForInputError(allPatientSelections[25]  , 26))
            && (checkQuestionForInputError(allPatientSelections[26]  , 27)) && (checkQuestionForInputError(allPatientSelections[27]  , 28))
            && (checkQuestionForInputError(allPatientSelections[28]  , 29)) && (checkQuestionForInputError(allPatientSelections[29]  , 30))
            && (checkQuestionForInputError(allPatientSelections[30]  , 31)) && (checkQuestionForInputError(allPatientSelections[31]  , 32))
            && (checkQuestionForInputError(allPatientSelections[32]  , 33)) && (checkQuestionForInputError(allPatientSelections[33]  , 34))
            && (checkQuestionForInputError(allPatientSelections[34]  , 35)) && (checkQuestionForInputError(allPatientSelections[35]  , 36))
            && (checkQuestionForInputError(allPatientSelections[36]  , 37)) && (checkQuestionForInputError(allPatientSelections[37]  , 38))
            && (checkQuestionForInputError(allPatientSelections[38]  , 39)) && (checkQuestionForInputError(allPatientSelections[39]  , 40)))
        {
            val result = StaiTestEstimator(allPatientSelections).calculateStateAndTraitScores()
            storePatientOnRealm(allPatientSelections , result)
            openResultFragment(result)
        }
    }

    private fun openResultFragment(testResult : Pair<Int , Int>)
    {
        resultFragment = ResultFragment.newInstance(testResult.first.toDouble() , testResult.second.toDouble() ,  10 , null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            staiCheckFragment.showSelectionError("Please select an answer for question No : $questionNO", questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?> , score : Pair<Int , Int>) : Job =
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
            testName = "STAI"
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

            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "STAI").findAll()
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
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).lessThanOrEqualTo("testDate" , testDate).equalTo("testName" , "STAI").findAll()
        }

        return tests!!.get(tests!!.size - 1)!!
    }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?> , results : Pair<Int , Int>)
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

            currentTest!!.patientSTAISQ1 = allPatientSelections[0]
            currentTest!!.patientSTAISQ2 = allPatientSelections[1]
            currentTest!!.patientSTAISQ3 = allPatientSelections[2]
            currentTest!!.patientSTAISQ4 = allPatientSelections[3]
            currentTest!!.patientSTAISQ5 = allPatientSelections[4]
            currentTest!!.patientSTAISQ6 = allPatientSelections[5]
            currentTest!!.patientSTAISQ7 = allPatientSelections[6]
            currentTest!!.patientSTAISQ8 = allPatientSelections[7]
            currentTest!!.patientSTAISQ9 = allPatientSelections[8]
            currentTest!!.patientSTAISQ10 = allPatientSelections[9]
            currentTest!!.patientSTAISQ11 = allPatientSelections[10]
            currentTest!!.patientSTAISQ12 = allPatientSelections[11]
            currentTest!!.patientSTAISQ13 = allPatientSelections[12]
            currentTest!!.patientSTAISQ14 = allPatientSelections[13]
            currentTest!!.patientSTAISQ15 = allPatientSelections[14]
            currentTest!!.patientSTAISQ16 = allPatientSelections[15]
            currentTest!!.patientSTAISQ17 = allPatientSelections[16]
            currentTest!!.patientSTAISQ18 = allPatientSelections[17]
            currentTest!!.patientSTAISQ19 = allPatientSelections[18]
            currentTest!!.patientSTAISQ20 = allPatientSelections[19]
            currentTest!!.patientSTAITQ21 = allPatientSelections[20]
            currentTest!!.patientSTAITQ22 = allPatientSelections[21]
            currentTest!!.patientSTAITQ23 = allPatientSelections[22]
            currentTest!!.patientSTAITQ24 = allPatientSelections[23]
            currentTest!!.patientSTAITQ25 = allPatientSelections[24]
            currentTest!!.patientSTAITQ26 = allPatientSelections[25]
            currentTest!!.patientSTAITQ27 = allPatientSelections[26]
            currentTest!!.patientSTAITQ28 = allPatientSelections[27]
            currentTest!!.patientSTAITQ29 = allPatientSelections[28]
            currentTest!!.patientSTAITQ30 = allPatientSelections[29]
            currentTest!!.patientSTAITQ31 = allPatientSelections[30]
            currentTest!!.patientSTAITQ32 = allPatientSelections[31]
            currentTest!!.patientSTAITQ33 = allPatientSelections[32]
            currentTest!!.patientSTAITQ34 = allPatientSelections[33]
            currentTest!!.patientSTAITQ35 = allPatientSelections[34]
            currentTest!!.patientSTAITQ36 = allPatientSelections[35]
            currentTest!!.patientSTAITQ37 = allPatientSelections[36]
            currentTest!!.patientSTAITQ38 = allPatientSelections[37]
            currentTest!!.patientSTAITQ39 = allPatientSelections[38]
            currentTest!!.patientSTAITQ40 = allPatientSelections[39]
            currentTest!!.patientId = patient!!.patientId
            currentTest!!.testDate = calendar.time
            currentTest!!.testName = "STAI"
            currentTest!!.patientSTAISScore = results.first
            currentTest!!.patientSTAITScore = results.second

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

    fun formatDate(date: Date): String {
        return SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date)
    }

}