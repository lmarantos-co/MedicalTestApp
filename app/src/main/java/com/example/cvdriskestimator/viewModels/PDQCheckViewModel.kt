package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.*
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.MDITestEstimator
import com.example.cvdriskestimator.MedicalTestAlgorithms.PDQTestEstimator
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class PDQCheckViewModel : ViewModel() {

    private lateinit var mainActivity : MainActivity
    private lateinit var pdqCheckFragment: Fragment
    private lateinit  var realm : Realm
    private  var realmDAO =  RealmDAO()
    private var mdiTestEstimator = MDITestEstimator()
    private lateinit var resultFragment : ResultFragment

    //mutable data that hold the patient data
    var patientData = MutableLiveData<Patient>()
    var testData = MutableLiveData<Test>()



    //mutable data to hold the all patient data in between fragment transactions
    var allPatientData = MutableLiveData<IntArray>()

    fun passActivity(activity : MainActivity)
    {
        mainActivity = activity
    }

    fun passFragment(fragment : Fragment)
    {
        pdqCheckFragment = fragment
    }

    fun passAllPatientData(data : IntArray)
    {
        allPatientData.value = data
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
        testData = realmDAO.fetchTestData(patientData.value!!.patientId , "PDQ")
        patientData.postValue(patientData.value)
        testData.postValue(testData.value)
    }

    fun checkPDQTestPatient(allPatientSelections : IntArray) : Boolean
    {
        return ((checkQuestionForInputError(allPatientSelections[0] , 1)) && (checkQuestionForInputError(allPatientSelections[1]  , 2))
                && (checkQuestionForInputError(allPatientSelections[2]  , 3)) && (checkQuestionForInputError(allPatientSelections[3]  , 4))
                && (checkQuestionForInputError(allPatientSelections[4]  , 5)) && (checkQuestionForInputError(allPatientSelections[5]  , 6))
                && (checkQuestionForInputError(allPatientSelections[6]  , 7)) && (checkQuestionForInputError(allPatientSelections[7]  , 8))
                && (checkQuestionForInputError(allPatientSelections[8]  , 9)) && (checkQuestionForInputError(allPatientSelections[9]  , 10)))
    }

    private fun openResultFragment(testResult : Int)
    {
        resultFragment = ResultFragment.newInstance(testResult.toDouble() , 0.0 , 8)
        mainActivity.fragmentTransaction(resultFragment)
    }

    fun initializePatientData()
    {
        var userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userame" , "tempUser")
        realm.executeTransaction {
            var patient = realm.where(Patient::class.java).equalTo("userName" , userName).findFirst()

            var PDQTest = Test()
            PDQTest!!.patientPDQQ1 = null
            PDQTest!!.patientPDQQ2 = null
            PDQTest!!.patientPDQQ3 = null
            PDQTest!!.patientPDQQ4 = null
            PDQTest!!.patientPDQQ5 = null
            PDQTest!!.patientPDQQ6 = null
            PDQTest!!.patientPDQQ7 = null
            PDQTest!!.patientPDQQ8 = null
            PDQTest!!.patientPDQQ9 = null
            PDQTest!!.patientPDQQ10 = null
            PDQTest!!.patientPDQQ11 = null
            PDQTest!!.patientPDQQ12 = null
            PDQTest!!.patientPDQQ13 = null
            PDQTest!!.patientPDQQ14 = null
            PDQTest!!.patientPDQQ15 = null
            PDQTest!!.patientPDQQ16 = null
            PDQTest!!.patientPDQQ17 = null
            PDQTest!!.patientPDQQ18 = null
            PDQTest!!.patientPDQQ19 = null
            PDQTest!!.patientPDQQ20 = null
            PDQTest!!.patientPDQQ21 = null
            PDQTest!!.patientPDQQ22 = null
            PDQTest!!.patientPDQQ23 = null
            PDQTest!!.patientPDQQ24 = null
            PDQTest!!.patientPDQQ25 = null
            PDQTest!!.patientPDQQ26 = null
            PDQTest!!.patientPDQQ27 = null
            PDQTest!!.patientPDQQ28 = null
            PDQTest!!.patientPDQQ29 = null
            PDQTest!!.patientPDQQ30 = null
            PDQTest!!.patientPDQQ31 = null
            PDQTest!!.patientPDQQ32 = null
            PDQTest!!.patientPDQQ33 = null
            PDQTest!!.patientPDQQ34 = null
            PDQTest!!.patientPDQQ35 = null
            PDQTest!!.patientPDQQ36 = null
            PDQTest!!.patientPDQQ37 = null
            PDQTest!!.patientPDQQ38 = null
            PDQTest!!.patientPDQQ39 = null

            //set the date for the test
            var dateFormatter = DateTimeFormatter.ofPattern("yyyy MM dd")
            var testDate = dateFormatter.parse(LocalDateTime.now().toString()).toString()

            var listOftests = ArrayList<Test>()
            if (patient!!.listOfTests!! != null)
            {
                for (i in 0 until patient!!.listOfTests!!.size)
                {
                    listOftests[i] = patient!!.listOfTests!!.get(i)!!
                }
                listOftests.add(PDQTest)
                patient!!.listOfTests = null
                for (i in 0 until listOftests.size)
                {
                    patient.listOfTests!![i] = listOftests.get(i)
                }
            }
            realm.insert(PDQTest)

            realm.insertOrUpdate(patient)
        }
    }



    private fun checkQuestionForInputError(value : Int?, questionNO: Int) : Boolean
    {
        var correctData : Boolean = false
        if (value == null)
        {
            if (pdqCheckFragment is PDQCheckFirstCategoryFragment)
                (pdqCheckFragment as PDQCheckFirstCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckSecondCategoryFragment)
                (pdqCheckFragment as PDQCheckSecondCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckThridCategoryFragment)
                (pdqCheckFragment as PDQCheckThridCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckFourthCategoryFragment)
                (pdqCheckFragment as PDQCheckFourthCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckFifthCategoryFragment)
                (pdqCheckFragment as PDQCheckFifthCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckSixthCategoryFragment)
                (pdqCheckFragment as PDQCheckSixthCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckSeventhCategoryFragment)
                (pdqCheckFragment as PDQCheckSeventhCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            if (pdqCheckFragment is PDQCheckEightCategoryFragment)
                (pdqCheckFragment as PDQCheckEightCategoryFragment).showSelectionError("Please select an answer for question No : " + questionNO, questionNO)
            correctData = false
        }
        else
            correctData = true
        return correctData
    }

    private fun storePatientOnRealm(allPatientSelections: ArrayList<Int?>) : Job =
        viewModelScope.launch{
            storePatientOnDB(allPatientSelections)
        }

    private fun storePatientOnDB(allPatientSelections: ArrayList<Int?>)
    {
        //execute transaction on realm
        realm.executeTransaction {

            //store the patient data on the database
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            val patient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , username).findFirst()

            var currentTest = Test()
            val date = Date.from(Calendar.getInstance().toInstant())

            val dateCount = realm.where(Test::class.java).equalTo("testDate" , date).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , date).findFirst()!!
            }
            currentTest!!.patientPDQQ1 = allPatientSelections[0]
            currentTest!!.patientPDQQ2 = allPatientSelections[1]
            currentTest!!.patientPDQQ3 = allPatientSelections[2]
            currentTest!!.patientPDQQ4 = allPatientSelections[3]
            currentTest!!.patientPDQQ5 = allPatientSelections[4]
            currentTest!!.patientPDQQ6 = allPatientSelections[5]
            currentTest!!.patientPDQQ7 = allPatientSelections[6]
            currentTest!!.patientPDQQ8 = allPatientSelections[7]
            currentTest!!.patientPDQQ9 = allPatientSelections[8]
            currentTest!!.patientPDQQ10 = allPatientSelections[9]
            currentTest!!.patientPDQQ11 = allPatientSelections[10]
            currentTest!!.patientPDQQ12 = allPatientSelections[11]
            currentTest!!.patientPDQQ13 = allPatientSelections[12]
            currentTest!!.patientPDQQ14 = allPatientSelections[13]
            currentTest!!.patientPDQQ15 = allPatientSelections[14]
            currentTest!!.patientPDQQ16 = allPatientSelections[15]
            currentTest!!.patientPDQQ17 = allPatientSelections[16]
            currentTest!!.patientPDQQ18 = allPatientSelections[17]
            currentTest!!.patientPDQQ19 = allPatientSelections[18]
            currentTest!!.patientPDQQ20 = allPatientSelections[19]
            currentTest!!.patientPDQQ21 = allPatientSelections[20]
            currentTest!!.patientPDQQ22 = allPatientSelections[21]
            currentTest!!.patientPDQQ23 = allPatientSelections[22]
            currentTest!!.patientPDQQ24 = allPatientSelections[23]
            currentTest!!.patientPDQQ25 = allPatientSelections[24]
            currentTest!!.patientPDQQ26 = allPatientSelections[25]
            currentTest!!.patientPDQQ27 = allPatientSelections[26]
            currentTest!!.patientPDQQ28 = allPatientSelections[27]
            currentTest!!.patientPDQQ29 = allPatientSelections[28]
            currentTest!!.patientPDQQ30 = allPatientSelections[29]
            currentTest!!.patientPDQQ31 = allPatientSelections[30]
            currentTest!!.patientPDQQ32 = allPatientSelections[31]
            currentTest!!.patientPDQQ33 = allPatientSelections[32]
            currentTest!!.patientPDQQ34 = allPatientSelections[33]
            currentTest!!.patientPDQQ35 = allPatientSelections[34]
            currentTest!!.patientPDQQ36 = allPatientSelections[35]
            currentTest!!.patientPDQQ37 = allPatientSelections[36]
            currentTest!!.patientPDQQ38 = allPatientSelections[37]
            currentTest!!.patientPDQQ39 = allPatientSelections[38]
            currentTest!!.patientId = patient!!.patientId
            currentTest.testDate = date

            var testId : Int = 0
            if (dateCount.toInt() == 0)
            {
                var testList = realm.where(Test::class.java).findAll()
                if (testList.size > 1)
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

            realm.insertOrUpdate(patient)

        }
    }

}