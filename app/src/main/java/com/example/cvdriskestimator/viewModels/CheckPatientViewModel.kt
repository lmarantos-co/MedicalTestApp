package com.example.cvdriskestimator.viewModels

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioGroup
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvdriskestimator.Fragments.CheckFragment
import com.example.cvdriskestimator.Fragments.HistoryFragment
import com.example.cvdriskestimator.Fragments.ResultFragment
import com.example.cvdriskestimator.Fragments.ResultTimelineFragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.MedicalTestAlgorithms.CVDRiskEstimator
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.RealmDB.RealmDAO
import com.example.cvdriskestimator.RealmDB.Test
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
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
    private lateinit var timelineFragment: ResultTimelineFragment
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

    fun openTimeLineFragment()
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
        timelineFragment = ResultTimelineFragment()
        timelineFragment.arguments = bundle
        mainActivity.fragmentTransaction(timelineFragment)
    }

    fun fetchHistoryTest(patientId : String, testID : String) : Test
    {
        var tests : RealmResults<Test>? = null
        realm.executeTransaction {

//            var dummyTestList = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testName" , "CardioVascularDisease").findAll()
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
//                    dummyTestDate.set(Calendar.YEAR , testDate.year -1 +  1900)
//                    dummyTestDate.set(Calendar.MONTH , 12)
//                    dummyTestDate.set(Calendar.DAY_OF_MONTH , 31)
//                }
//            }
            tests = realm.where(Test::class.java).equalTo("patientId" , patientId).equalTo("testId" , testID).equalTo("testName" , "CardioVascularDisease").findAll()
        }


        return tests!!.get(tests!!.size -1)!!
    }

    private fun initialiseUserDummy() {
        realm.executeTransaction {
            val dummyPatient : Patient? =  realm.where(Patient::class.java).isNotNull("patientId") .equalTo("userName" , "tempUser").findFirst()
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
        testDATA = realmDAO.fetchTestData(patientDATA.value!!.patientId , "CardioVascularDisease")
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
        var allTests = realm.where(Test::class.java).findAll()
        val username = prefs.getString("userName", "tempUser")
        //get the user record
//        val realm = Realm.getInstance(mainActivity.passRealmConfig())
        //run the realm async transaction
        realm.executeTransaction { realmTransaction ->
            val patient : Patient = realm.where(Patient::class.java).equalTo("userName" , username).findFirst()!!
            //update the user record with the relevant data

            var currentTest = Test()
 //           val date = Date()
 //           var currentDate = Date(date.year , date.month , date.date , date.hours , date.minutes ,date.seconds)
            //default time zone
            //default time zone
            val defaultZoneId: ZoneId = ZoneId.systemDefault()
            val localDate: LocalDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
            val text: String = localDate.format(formatter)
            val parsedDate: LocalDate = LocalDate.parse(text, formatter)
            val covertedDate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())

            val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())

            val calendar0: Calendar = Calendar.getInstance()
//            calendar0.set(Calendar.YEAR , date.year)
//            calendar0.set(Calendar.MONTH , date.month)
//            calendar0.set(Calendar.DAY_OF_MONTH , date.day)
//            calendar0.set(Calendar.HOUR_OF_DAY, date.hours)
//            calendar0.set(Calendar.MINUTE, date.minutes)
//            calendar0.set(Calendar.SECOND, date.seconds)

            //check if the current date is already in the test database
            val dateCount = realm.where(Test::class.java).equalTo("testDate" , calendar0.time).count()
            if (dateCount > 0)
            {
                currentTest = realm.where(Test::class.java).equalTo("testDate" , calendar0.time).findFirst()!!
            }
            currentTest.patientSex = sex
            currentTest.patientAge = age
            currentTest.SSB = sbp
            currentTest.TCH = TCH
            currentTest.HDL = HDL
            currentTest.smoker = smokingStatus
            currentTest.treatment = treatmentStatus
            currentTest.patientId = patient.patientId
            currentTest.testDate = calendar0.time
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


            //insert dummy tests
//            var dummytest1 = Test()
//            //var dummyDate1 = Date(2021 , 5 , 15 , 10 , 30 , 0)
//            val calendar: Calendar = Calendar.getInstance()
//            calendar.set(Calendar.YEAR , 2021)
//            calendar.set(Calendar.MONTH , 5)
//            calendar.set(Calendar.DAY_OF_MONTH , 15)
//            calendar.set(Calendar.HOUR_OF_DAY, 10)
//            calendar.set(Calendar.MINUTE, 30)
//            calendar.set(Calendar.SECOND, 0)
//            dummytest1.patientSex = "MALE"
//            dummytest1.patientAge = "40"
//            dummytest1.SSB = "100"
//            dummytest1.TCH = "210"
//            dummytest1.HDL = "50"
//            dummytest1.smoker = "Current"
//            dummytest1.treatment = "Yes"
//            dummytest1.testDate = calendar.time
//            dummytest1.patientId = patient.patientId
//            dummytest1!!.cvdTestResult = 60
//            dummytest1!!.testName = "CardioVascularDisease"
//            dummytest1!!.testId = ((currentTest!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest1)
////
//            var dummytest2 = Test()
//            //var dummyDate2 = Date(2022 , 6 , 5 , 18 , 15 , 0)
//            val calendar2: Calendar = Calendar.getInstance()
//            calendar2.set(Calendar.YEAR , 2022)
//            calendar2.set(Calendar.MONTH , 6)
//            calendar2.set(Calendar.DAY_OF_MONTH , 5)
//            calendar2.set(Calendar.HOUR_OF_DAY, 18)
//            calendar2.set(Calendar.MINUTE, 15)
//            calendar2.set(Calendar.SECOND, 0)
//            dummytest2.patientSex = "MALE"
//            dummytest2.patientAge = "40"
//            dummytest2.SSB = "120"
//            dummytest2.TCH = "260"
//            dummytest2.HDL = "40"
//            dummytest2.smoker = "Current"
//            dummytest2.patientId = patient.patientId
//            dummytest2.treatment = "Yes"
//            dummytest2.testDate = calendar2.time
//            dummytest2!!.cvdTestResult = 40
//            dummytest2!!.testName = "CardioVascularDisease"
//            dummytest2!!.testId = ((dummytest1!!.testId).toInt() + 1).toString()
////
//            realm.insertOrUpdate(dummytest2)
////
//            var dummytest3 = Test()
//            var dummyDate3 = Date(2022 , 6 , 25 , 18 , 15 , 0)
//            val calendar3: Calendar = Calendar.getInstance()
//            calendar3.set(Calendar.YEAR , 2022)
//            calendar3.set(Calendar.MONTH , 6)
//            calendar3.set(Calendar.DAY_OF_MONTH , 25)
//            calendar3.set(Calendar.HOUR_OF_DAY, 18)
//            calendar3.set(Calendar.MINUTE, 15)
//            calendar3.set(Calendar.SECOND, 0)
//            dummytest3.patientSex = "MALE"
//            dummytest3.patientAge = "40"
//            dummytest3.SSB = "160"
//            dummytest3.TCH = "180"
//            dummytest3.HDL = "70"
//            dummytest3.patientId = patient.patientId
//            dummytest3.smoker = "Current"
//            dummytest3.treatment = "No"
//            dummytest3.testDate = calendar3.time
//            dummytest3!!.cvdTestResult = 80
//            dummytest3!!.testName = "CardioVascularDisease"
//            dummytest3!!.testId = ((dummytest2!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest3)
////
//            var dummytest4 = Test()
//            //var dummyDate4 = Date(2022 , 7 , 5 , 18 , 15 , 0)
//            val calendar4: Calendar = Calendar.getInstance()
//            calendar4.set(Calendar.YEAR , 2022)
//            calendar4.set(Calendar.MONTH , 7)
//            calendar4.set(Calendar.DAY_OF_MONTH , 5)
//            calendar4.set(Calendar.HOUR_OF_DAY, 18)
//            calendar4.set(Calendar.MINUTE, 15)
//            calendar4.set(Calendar.SECOND, 0)
//            dummytest4.patientSex = "MALE"
//            dummytest4.patientAge = "40"
//            dummytest4.SSB = "120"
//            dummytest4.TCH = "180"
//            dummytest4.HDL = "70"
//            dummytest4.patientId = patient.patientId
//            dummytest4.smoker = "Current"
//            dummytest4.treatment = "No"
//            dummytest4.testDate = calendar4.time
//            dummytest4!!.cvdTestResult = 30
//            dummytest4!!.testName = "CardioVascularDisease"
//            dummytest4!!.testId = ((dummytest3!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest4)
//
//            var dummytest5 = Test()
//            var dummyDate5 = Date(2022 , 8 , 5 , 18 , 15 , 0)
//            val calendar5: Calendar = Calendar.getInstance()
//            calendar5.set(Calendar.YEAR , 2022)
//            calendar5.set(Calendar.MONTH , 8)
//            calendar5.set(Calendar.DAY_OF_MONTH , 5)
////            calendar5.set(Calendar.HOUR_OF_DAY, 18)
////            calendar5.set(Calendar.MINUTE, 15)
////            calendar5.set(Calendar.SECOND, 0)
//            dummytest5.patientSex = "MALE"
//            dummytest5.patientAge = "40"
//            dummytest5.SSB = "120"
//            dummytest5.TCH = "180"
//            dummytest5.HDL = "70"
//            dummytest5.patientId = patient.patientId
//            dummytest5.smoker = "Current"
//            dummytest5.treatment = "no"
//            dummytest5.testDate = calendar5.time
//            dummytest5!!.cvdTestResult = 30
//            dummytest5!!.testName = "CardioVascularDisease"
//            dummytest5!!.testId = ((dummytest4!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest5)
//
//            var dummytest6 = Test()
//            var dummyDate6 = Date(2022 , 9 , 5 , 18 , 15 , 0)
//            val calendar6: Calendar = Calendar.getInstance()
//            calendar6.set(Calendar.YEAR , 2022)
//            calendar6.set(Calendar.MONTH , 9)
//            calendar6.set(Calendar.DAY_OF_MONTH , 5)
////            calendar6.set(Calendar.HOUR_OF_DAY, 18)
////            calendar6.set(Calendar.MINUTE, 15)
////            calendar6.set(Calendar.SECOND, 0)
//            dummytest6.patientSex = "MALE"
//            dummytest6.patientAge = "40"
//            dummytest6.SSB = "120"
//            dummytest6.TCH = "180"
//            dummytest6.HDL = "70"
//            dummytest6.patientId = patient.patientId
//            dummytest6.smoker = "Current"
//            dummytest6.treatment = "no"
//            dummytest6.testDate = calendar6.time
//            dummytest6!!.cvdTestResult = 80
//            dummytest6!!.testName = "CardioVascularDisease"
//            dummytest6!!.testId = ((dummytest5!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest6)
//
//            var dummytest8 = Test()
//            var dummyDate8 = Date(2022 , 10 , 5 , 18 , 15 , 0)
//            val calendar8: Calendar = Calendar.getInstance()
//            calendar8.set(Calendar.YEAR , 2022)
//            calendar8.set(Calendar.MONTH , 10)
//            calendar8.set(Calendar.DAY_OF_MONTH , 5)
////            calendar8.set(Calendar.HOUR_OF_DAY, 18)
////            calendar8.set(Calendar.MINUTE, 15)
////            calendar8.set(Calendar.SECOND, 0)
//            dummytest8.patientSex = "MALE"
//            dummytest8.patientAge = "40"
//            dummytest8.SSB = "120"
//            dummytest8.TCH = "180"
//            dummytest8.HDL = "70"
//            dummytest8.patientId = patient.patientId
//            dummytest8.smoker = "Current"
//            dummytest8.treatment = "no"
//            dummytest8.testDate = calendar8.time
//            dummytest8!!.cvdTestResult = 80
//            dummytest8!!.testName = "CardioVascularDisease"
//            dummytest8!!.testId = ((dummytest6!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest8)
//
//            var dummytest9 = Test()
//            var dummyDate9 = Date(2022 , 12 , 1 , 18 , 15 , 0)
//            val calendar9: Calendar = Calendar.getInstance()
//            calendar9.set(Calendar.YEAR , 2022)
//            calendar9.set(Calendar.MONTH , 12)
//            calendar9.set(Calendar.DAY_OF_MONTH , 5)
////            calendar9.set(Calendar.HOUR_OF_DAY, 18)
////            calendar9.set(Calendar.MINUTE, 15)
////            calendar9.set(Calendar.SECOND, 0)
//            dummytest9.patientSex = "MALE"
//            dummytest9.patientAge = "40"
//            dummytest9.SSB = "120"
//            dummytest9.TCH = "180"
//            dummytest9.HDL = "70"
//            dummytest9.patientId = patient.patientId
//            dummytest9.smoker = "Current"
//            dummytest9.treatment = "no"
//            dummytest9.testDate = calendar9.time
//            dummytest9!!.cvdTestResult = 10
//            dummytest9!!.testName = "CardioVascularDisease"
//            dummytest9!!.testId = ((dummytest8!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest9)
//
//            var dummytest10 = Test()
//            var dummyDate10 = Date(2022 , 12 , 2 , 18 , 15 , 0)
//            val calendar10: Calendar = Calendar.getInstance()
//            calendar10.set(Calendar.YEAR , 2022)
//            calendar10.set(Calendar.MONTH , 12)
//            calendar10.set(Calendar.DAY_OF_MONTH , 2)
////            calendar10.set(Calendar.HOUR_OF_DAY, 18)
////            calendar10.set(Calendar.MINUTE, 15)
////            calendar10.set(Calendar.SECOND, 0)
//            dummytest10.patientSex = "MALE"
//            dummytest10.patientAge = "40"
//            dummytest10.SSB = "120"
//            dummytest10.TCH = "180"
//            dummytest10.HDL = "70"
//            dummytest10.patientId = patient.patientId
//            dummytest10.smoker = "Current"
//            dummytest10.treatment = "no"
//            dummytest10.testDate = calendar10.time
//            dummytest10!!.cvdTestResult = 35
//            dummytest10!!.testName = "CardioVascularDisease"
//            dummytest10!!.testId = ((dummytest9!!.testId).toInt() + 1).toString()
//            realm.insertOrUpdate(dummytest10)

            //update the user record within realm database
            realm.copyToRealmOrUpdate(patient)
        }

    }

    fun convertDate(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString)
    }


    private fun calculateCVDRisk(sex : String , age : Int , sbp : Int, TCH : Int, HDL : Int , smokingStatus : String , treatmentStatus : String) {
//        Toast.makeText(mainActivity.applicationContext , "The Framingham Risk Score for you is : " + cvdRiskEstimator.calculateCVDRisk(sex , age , sbp , TCH , HDL , smokingStatus , treatmentStatus) + " % \n" + mainActivity.resources.getString(
//            R.string.cvd_response_text), Toast.LENGTH_LONG ).show()
        val riskResult = cvdRiskEstimator.calculateCVDRisk(sex , age , sbp , TCH , HDL , smokingStatus , treatmentStatus)
        resultFragment = ResultFragment.newInstance(riskResult.toDouble(),0.0 ,  1 , null , null)
        mainActivity.fragmentTransaction(resultFragment)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}