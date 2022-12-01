package com.example.cvdriskestimator.RealmDB

import androidx.lifecycle.MutableLiveData
import io.realm.Realm

class RealmDAO {

    private var realm = Realm.getDefaultInstance()
    private  var patientData = MutableLiveData<Patient>()
    private var testdata = MutableLiveData<Test>()

    //fetch the patient record of the Patient
    fun fetchPatientData(userName : String) : MutableLiveData<Patient>
    {
        patientData.value = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , userName).findFirst()
        return patientData
    }

    //fetch the last test data of the Patient
    fun fetchTestData(patientId : String , testName : String) : MutableLiveData<Test>
    {
        when (testName)
        {
            "CVD" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("TCH").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "DIABETES" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientPAM").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "MDI" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientMDIQ1").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "BAI" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientBAIQ1").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "MDS" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientMDSQ1").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "BPI" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientBPIQ1").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "GDS" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientGDSQ1").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "PDQ" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientPDQQ1").equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
        }
        return testdata
    }

}