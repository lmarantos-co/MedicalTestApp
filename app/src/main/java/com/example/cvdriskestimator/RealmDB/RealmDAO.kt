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
            "CardioVascularDisease" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("TCH").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "DIABETES" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientPAM").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "MDI" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientMDIQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "Beck Anxiety Index" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientBAIQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "MDS" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientMDSQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "Brief Pain Inventory" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientBPIQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "Beck Depression Inventory" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientBDIQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "Geriatric Depression Scale" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientGDSQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "PDQ" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientPDQQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "Major Depression Index" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientMDIQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "DASS" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientDASSQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "STAI" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientSTAISQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "ZUNG" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientZUNGQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
            "Hammilton Depression" ->
            {
                var testList = realm.where(Test::class.java).isNotNull("patientHAMDQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
                if (testList.size > 0)
                    testdata.value = testList.get(testList.size -1)!!
            }
        }
        return testdata
    }

}