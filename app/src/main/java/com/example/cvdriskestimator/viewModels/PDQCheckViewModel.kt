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
import io.realm.Realm
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PDQCheckViewModel : ViewModel() {

    private lateinit var mainActivity : MainActivity
    private lateinit var pdqCheckFragment: Fragment
    private lateinit  var realm : Realm
    private  var realmDAO =  RealmDAO()
    private var mdiTestEstimator = MDITestEstimator()
    private lateinit var resultFragment : ResultFragment

    //mutable data that hold the patient data
    var patientData = MutableLiveData<Patient>()

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
            var dummyPatient = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , "tempUser").findFirst()
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
        patientData.postValue(patientData.value)
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
            patient!!.patientPDQQ1 = null
            patient!!.patientPDQQ2 = null
            patient!!.patientPDQQ3 = null
            patient!!.patientPDQQ4 = null
            patient!!.patientPDQQ5 = null
            patient!!.patientPDQQ6 = null
            patient!!.patientPDQQ7 = null
            patient!!.patientPDQQ8 = null
            patient!!.patientPDQQ9 = null
            patient!!.patientPDQQ10 = null
            patient!!.patientPDQQ11 = null
            patient!!.patientPDQQ12 = null
            patient!!.patientPDQQ13 = null
            patient!!.patientPDQQ14 = null
            patient!!.patientPDQQ15 = null
            patient!!.patientPDQQ16 = null
            patient!!.patientPDQQ17 = null
            patient!!.patientPDQQ18 = null
            patient!!.patientPDQQ19 = null
            patient!!.patientPDQQ20 = null
            patient!!.patientPDQQ21 = null
            patient!!.patientPDQQ22 = null
            patient!!.patientPDQQ23 = null
            patient!!.patientPDQQ24 = null
            patient!!.patientPDQQ25 = null
            patient!!.patientPDQQ26 = null
            patient!!.patientPDQQ27 = null
            patient!!.patientPDQQ28 = null
            patient!!.patientPDQQ29 = null
            patient!!.patientPDQQ30 = null
            patient!!.patientPDQQ31 = null
            patient!!.patientPDQQ32 = null
            patient!!.patientPDQQ33 = null
            patient!!.patientPDQQ34 = null
            patient!!.patientPDQQ35 = null
            patient!!.patientPDQQ36 = null
            patient!!.patientPDQQ37 = null
            patient!!.patientPDQQ38 = null
            patient!!.patientPDQQ39 = null
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

            patient!!.patientPDQQ1 = allPatientSelections[0]
            patient!!.patientPDQQ2 = allPatientSelections[1]
            patient!!.patientPDQQ3 = allPatientSelections[2]
            patient!!.patientPDQQ4 = allPatientSelections[3]
            patient!!.patientPDQQ5 = allPatientSelections[4]
            patient!!.patientPDQQ6 = allPatientSelections[5]
            patient!!.patientPDQQ7 = allPatientSelections[6]
            patient!!.patientPDQQ8 = allPatientSelections[7]
            patient!!.patientPDQQ9 = allPatientSelections[8]
            patient!!.patientPDQQ10 = allPatientSelections[9]
            patient!!.patientPDQQ11 = allPatientSelections[10]
            patient!!.patientPDQQ12 = allPatientSelections[11]
            patient!!.patientPDQQ13 = allPatientSelections[12]
            patient!!.patientPDQQ14 = allPatientSelections[13]
            patient!!.patientPDQQ15 = allPatientSelections[14]
            patient!!.patientPDQQ16 = allPatientSelections[15]
            patient!!.patientPDQQ17 = allPatientSelections[16]
            patient!!.patientPDQQ18 = allPatientSelections[17]
            patient!!.patientPDQQ19 = allPatientSelections[18]
            patient!!.patientPDQQ20 = allPatientSelections[19]
            patient!!.patientPDQQ21 = allPatientSelections[20]
            patient!!.patientPDQQ22 = allPatientSelections[21]
            patient!!.patientPDQQ23 = allPatientSelections[22]
            patient!!.patientPDQQ24 = allPatientSelections[23]
            patient!!.patientPDQQ25 = allPatientSelections[24]
            patient!!.patientPDQQ26 = allPatientSelections[25]
            patient!!.patientPDQQ27 = allPatientSelections[26]
            patient!!.patientPDQQ28 = allPatientSelections[27]
            patient!!.patientPDQQ29 = allPatientSelections[28]
            patient!!.patientPDQQ30 = allPatientSelections[29]
            patient!!.patientPDQQ31 = allPatientSelections[30]
            patient!!.patientPDQQ32 = allPatientSelections[31]
            patient!!.patientPDQQ33 = allPatientSelections[32]
            patient!!.patientPDQQ34 = allPatientSelections[33]
            patient!!.patientPDQQ35 = allPatientSelections[34]
            patient!!.patientPDQQ36 = allPatientSelections[35]
            patient!!.patientPDQQ37 = allPatientSelections[36]
            patient!!.patientPDQQ38 = allPatientSelections[37]
            patient!!.patientPDQQ39 = allPatientSelections[38]

            realm.insertOrUpdate(patient)

        }
    }

}