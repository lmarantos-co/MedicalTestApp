package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.MedicalTestAlgorithms.CVDRiskEstimator
import com.example.cvdriskestimator.Fragments.CheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CheckPatientViewModel : ViewModel() , Observable {

    private var realm: Realm = Realm.getDefaultInstance()
    private lateinit var checkFragment : CheckFragment
    private lateinit var resultFragment: ResultFragment
    private lateinit var mainActivity: MainActivity
    private lateinit var cvdRiskEstimator: CVDRiskEstimator
    private lateinit var historyFragment: HistoryFragment
    private var allDataCorrect : Boolean = false
    private var sexStatus : String =""
    private var smokingStatus : String =""
    private var treatmentStatus : String =""
    private var realmDAO = RealmDAO()


    //mutable live data from realm database
    var patientDATA = MutableLiveData<Patient>()

    var testDATA = MutableLiveData<Test>()

    fun setFragment(chckFragment: CheckFragment)
    {
        checkFragment = chckFragment
    }

    fun setActivity(activity: MainActivity)
    {
        mainActivity = activity
    }


    fun setUserDummyData() : Job =
        viewModelScope.launch {
            initialiseUserDummy()
        }

    fun history()
    {
        var patientId : String = ""
        var testName : String = ""
        realm.executeTransaction {
            val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
            var patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()
            patientId = patient!!.patientId
            testName = "CardioVascularDisease"
        }
        val bundle = Bundle()
        bundle.putString("patientId" , patientId)
        bundle.putString("testName" , testName)
        historyFragment = HistoryFragment()
        historyFragment.arguments = bundle
        mainActivity.fragmentTransaction(historyFragment)
    }

    private fun initialiseUserDummy() {
        realm.executeTransaction {
            val dummyPatient : Patient? =  realm.where(Patient::class.java).isNotNull("id") .equalTo("userName" , "tempUser").findFirst()
            if (dummyPatient == null)
            {
                val patient = Patient()
                patient.userName = "tempUser"
                patient.password = "dummy#1"
                realm.insertOrUpdate(patient)
            }

        }
    }

    fun setPatientDataOnForm()
    {
        fetchPatientRecord()
    }

    private fun checkPatientAge(age : String) : Boolean {

        //age constraint must be between 20-70
        val correctAge: Boolean = if ((TextUtils.isDigitsOnly(age)) && (age != "")) {
            (age.toInt() >=20 ) && (age.toInt() <=79 )
        } else {
            false
        }
        if (!correctAge)
        {
            checkFragment.displayAgeError("Please enter a value between 20-79 years")
        }
        return correctAge
    }

    private fun checkSystolicBloodPressure(sbp : String) : Boolean {

        //ΣΒΒ constraint must be between 90-200
        val correctSBP: Boolean
        if ((TextUtils.isDigitsOnly(sbp)) && (!sbp.equals("")))
        {
            correctSBP = (sbp.toInt() >90 ) && (sbp.toInt() <=200 )
        }
        else
        {
            correctSBP = false
        }
        if (!correctSBP)
        {
            checkFragment.displaySBPError(" Please enter a value between 90-200")
        }
        return correctSBP
    }

    private fun checkTotalCholesterol(tch : String) : Boolean {
        var correctTCH: Boolean = if ((TextUtils.isDigitsOnly(tch)) && (tch != "")) {
            (tch.toInt() >= 130 ) && (tch.toInt() <= 320 )
        } else {
            false
        }
        if (!correctTCH)
        {
            checkFragment.displayTCHError("Please enter a value between 130-320")
        }
        return correctTCH
    }

    private fun checkHDLCholesterol(hdl : String) : Boolean{
        var correctHDL: Boolean
        if ((TextUtils.isDigitsOnly(hdl)) && (hdl != ""))
        {
            correctHDL = (hdl.toInt() >= 20 ) && (hdl.toInt() <= 100 )
        }
        else
        {
            correctHDL = false
        }
        if (!correctHDL)
        {
            checkFragment.displayHDLError("Please enter a value between 20-100")
        }
        return correctHDL
    }

    private fun checkSexStatus(sexRG : RadioGroup) : String
    {
        return sexStatus(sexRG.checkedRadioButtonId)
    }

    private fun checkSmokingStatus(smRG : RadioGroup) : String{
        return smokerStatus(smRG.checkedRadioButtonId)
    }

    private fun checkTreatmentStatus(trRG : RadioGroup) : String {
        return treatmentStatus(trRG.checkedRadioButtonId)
    }

    private fun sexStatus(id : Int) : String
    {
        var result : String
        result = ""
        when (id) {
            R.id.maleRB ->
            {
                result = "MALE"
            }
            R.id.femaleRB ->
            {
                result = "FEMALE"
            }
        }
        return result
    }

    private fun smokerStatus(id : Int) : String
    {
        var result : String
        result = ""
        when (id) {
            R.id.smokeRB1 -> {
                result = "Current"
            }
            R.id.smokeRB2 -> {
                result = "Former"
            }
            R.id.smokeRB3 -> {
                result = "Never"
            }
        }
        return result
    }

    private fun treatmentStatus(id : Int) : String
    {
        var result : String
        result = ""
        when (id) {
            R.id.treatRButton1 -> {
                result = "Yes"
            }
            R.id.treatRadioButton2 -> {
                result = "No"
            }
        }
        return result
    }

    fun checkOnAllDataConstraints(
        age: String,
        sex: RadioGroup,
        sbp: String,
        TCH: String,
        HDL: String,
        SmRG: RadioGroup,
        TrRG: RadioGroup
    ) {
        cvdRiskEstimator = CVDRiskEstimator()
        allDataCorrect = true
        allDataCorrect = checkPatientAge(age) && allDataCorrect
        allDataCorrect = checkSystolicBloodPressure(sbp) && allDataCorrect
        allDataCorrect = checkTotalCholesterol(TCH) && allDataCorrect
        allDataCorrect = checkHDLCholesterol(HDL) && allDataCorrect
        if (checkSexStatus(sex) == "")
        {
            checkFragment.displayRadioGroupError(1)
            allDataCorrect = false
        }
        else
        {
            sexStatus = checkSexStatus(sex)
        }
        if (checkSmokingStatus(SmRG) == "")
        {
            checkFragment.displayRadioGroupError(2)
            allDataCorrect = false
        }
        else
        {
            smokingStatus = checkSmokingStatus(SmRG)
        }
        if (checkTreatmentStatus(TrRG) == "")
        {
            checkFragment.displayRadioGroupError(3)
            allDataCorrect = false
        }
        else
        {
            treatmentStatus = checkTreatmentStatus(TrRG)
        }
        if (allDataCorrect)
            //save the user data for form pre-sets
        {
            val riskResult = cvdRiskEstimator.calculateCVDRisk(sexStatus , age.toInt() , sbp.toInt() , TCH.toInt() , HDL.toInt() , smokingStatus , treatmentStatus)
            savePatient(sexStatus, age, sbp, TCH, HDL, smokingStatus, treatmentStatus , riskResult)
            calculateCVDRisk(
                sexStatus,
                age.toInt(),
                sbp.toInt(),
                TCH.toInt(),
                HDL.toInt(),
                smokingStatus,
                treatmentStatus
            )
        }
    }


    private  fun fetchPatientRecord() {
        val prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
        val username = prefs.getString("userName" , "tempUser")
        patientDATA = realmDAO.fetchPatientData(username!!)
        testDATA = realmDAO.fetchTestData(patientDATA.value!!.patientId , "CVD")
        patientDATA.postValue(patientDATA.value)
        testDATA.postValue(testDATA.value)
    }

    fun savePatient(sex : String, age : String, sbp : String, TCH : String, HDL : String, smokingStatus : String, treatmentStatus : String , score : Int) : Job =
        viewModelScope.launch {
            saveUserDataToDB(sex , age , sbp, TCH , HDL , smokingStatus , treatmentStatus , score)
        }


    private  fun saveUserDataToDB(sex : String, age : String, sbp : String, TCH : String, HDL : String, smokingStatus: String, treatmentStatus: String , score : Int)
    {
        val prefs = mainActivity.getPreferences(Context.MODE_PRIVATE)
        val username = prefs.getString("userName", "tempUser")
        //get the user record
//        val realm = Realm.getInstance(mainActivity.passRealmConfig())
        //run the realm async transaction
        realm.executeTransaction { realmTransaction ->
            val patient : Patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()!!
            //update the user record with the relevant data

            var currentTest = Test()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            //check if the current date is already in the test database
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , currentDate).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , currentDate).findFirst()!!
            }
            currentTest.patientSex = sex
            currentTest.patientAge = age
            currentTest.SSB = sbp
            currentTest.TCH = TCH
            currentTest.HDL = HDL
            currentTest.smoker = smokingStatus
            currentTest.treatment = treatmentStatus
            currentTest.patientId = patient.patientId
            currentTest!!.testDate = currentDate
            currentTest!!.cvdTestResult = score
            currentTest!!.testName = "CardioVascularDisease"
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

    private fun calculateCVDRisk(sex : String , age : Int , sbp : Int, TCH : Int, HDL : Int , smokingStatus : String , treatmentStatus : String) {
//        Toast.makeText(mainActivity.applicationContext , "The Framingham Risk Score for you is : " + cvdRiskEstimator.calculateCVDRisk(sex , age , sbp , TCH , HDL , smokingStatus , treatmentStatus) + " % \n" + mainActivity.resources.getString(
//            R.string.cvd_response_text), Toast.LENGTH_LONG ).show()
        val riskResult = cvdRiskEstimator.calculateCVDRisk(sex , age , sbp , TCH , HDL , smokingStatus , treatmentStatus)
        resultFragment = ResultFragment.newInstance(riskResult.toDouble(),0.0 ,  1)
        mainActivity.fragmentTransaction(resultFragment)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}