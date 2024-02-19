package com.example.cvdriskestimator.RealmDB

import androidx.lifecycle.MutableLiveData
import io.realm.Realm

class RealmDAO {

    private var realm = Realm.getDefaultInstance()
    private  var patientData = MutableLiveData<Patient>()
    private var testdata = MutableLiveData<Test>()
    private var BPItestdata = MutableLiveData<BPITest>()
    private var BAItestdata = MutableLiveData<BAITest>()
    private var BDItestdata = MutableLiveData<BDITest>()
    private var CVDtestdata = MutableLiveData<CVDTest>()
    private var DIABETEStestdata = MutableLiveData<DiabetesTest>()
    private var DASStestdata = MutableLiveData<DASSTest>()
    private var GDSTestdata = MutableLiveData<GDSTest>()
    private var HAMTestdata = MutableLiveData<HAMTest>()
    private var MDITestdata = MutableLiveData<MDITest>()
    private var MDSTestdata = MutableLiveData<MDSTest>()
    private var STAITestdata = MutableLiveData<STAITest>()
    private var ZUNGTestdata = MutableLiveData<ZUNGTest>()


    //fetch the patient record of the Patient
    fun fetchPatientData(userName : String) : MutableLiveData<Patient>
    {
        patientData.value = realm.where(Patient::class.java).isNotNull("patientId").equalTo("userName" , userName).findFirst()
        return patientData
    }

    //fetch the last test data of the Patient

    fun fetchBPITestData(patientId : String , testName : String) : MutableLiveData<BPITest>
    {
        var testList = realm.where(BPITest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            BPItestdata.value =  realm.copyFromRealm(testList.get(testList.size -1)!!)
        return BPItestdata
    }

    fun fetchBAITestData(patientId : String , testName : String) : MutableLiveData<BAITest>
    {
        var testList = realm.where(BAITest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            BAItestdata.value = testList.get(testList.size -1)!!
        return BAItestdata
    }

    fun fetchBDITestData(patientId : String , testName : String) : MutableLiveData<BDITest>
    {
        var testList = realm.where(BDITest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            BDItestdata.value = testList.get(testList.size -1)!!
        return BDItestdata
    }

    fun fetchCVDTestData(patientId : String , testName : String) : MutableLiveData<CVDTest>
    {
        var testList = realm.where(CVDTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            CVDtestdata.value = testList.get(testList.size -1)!!
        return CVDtestdata
    }

    fun fetchDiabetesTestData(patientId : String , testName : String) : MutableLiveData<DiabetesTest>
    {
        var testList = realm.where(DiabetesTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            DIABETEStestdata.value = testList.get(testList.size -1)!!
        return DIABETEStestdata
    }

    fun fetchDASSTestData(patientId : String , testName : String) : MutableLiveData<DASSTest>
    {
        var testList = realm.where(DASSTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            DASStestdata.value = testList.get(testList.size -1)!!
        return DASStestdata
    }

    fun fetchGDSTestData(patientId : String , testName : String) : MutableLiveData<GDSTest>
    {
        var testList = realm.where(GDSTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            GDSTestdata.value = testList.get(testList.size -1)!!
        return GDSTestdata
    }

    fun fetchHamTestData(patientId : String , testName : String) : MutableLiveData<HAMTest>
    {
        var testList = realm.where(HAMTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            HAMTestdata.value = testList.get(testList.size -1)!!
        return HAMTestdata
    }

    fun fetchMDITestData(patientId : String , testName : String) : MutableLiveData<MDITest>
    {
        var testList = realm.where(MDITest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            MDITestdata.value = testList.get(testList.size -1)!!
        return MDITestdata
    }

    fun fetchMDSTestData(patientId : String , testName : String) : MutableLiveData<MDSTest>
    {
        var testList = realm.where(MDSTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            MDSTestdata.value = testList.get(testList.size -1)!!
        return MDSTestdata
    }

    fun fetchSTAITestData(patientId : String , testName : String) : MutableLiveData<STAITest>
    {
        var testList = realm.where(STAITest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            STAITestdata.value = testList.get(testList.size -1)!!
        return STAITestdata
    }

    fun fetchZUNGTestData(patientId : String , testName : String) : MutableLiveData<ZUNGTest>
    {
        var testList = realm.where(ZUNGTest::class.java).equalTo("patientId" , patientId).findAll()
        if (testList.size > 0)
            ZUNGTestdata.value = testList.get(testList.size -1)!!
        return ZUNGTestdata
    }

//    fun fetchTestData(patientId : String , testName : String) : MutableLiveData<Test>
//    {
//        when (testName)
//        {
//            "CardioVascularDisease" ->
//            {
//                var testList = realm.where(CVDTest::class.java).isNotNull("TCH").equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "DIABETES" ->
//            {
//                var testList = realm.where(DiabetesTest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "MDI" ->
//            {
//                var testList = realm.where(MDITest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "Beck Anxiety Index" ->
//            {
//                var testList = realm.where(BAITest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "MDS" ->
//            {
//                var testList = realm.where(MDSTest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "Brief Pain Inventory" ->
//            {
//                var testList = realm.where(BPITest::class.java).isNotNull("patientBPIQ1").equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value =  realm.copyFromRealm(testList.get(testList.size -1)!!)
//            }
//            "Beck Depression Inventory" ->
//            {
//                var testList = realm.where(BDITest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "Geriatric Depression Scale" ->
//            {
//                var testList = realm.where(GDSTest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "PDQ" ->
//            {
//                var testList = realm.where(Test::class.java).isNotNull("patientPDQQ1").equalTo("testName" , testName).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "Major Depression Index" ->
//            {
//                var testList = realm.where(MDITest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "DASS" ->
//            {
//                var testList = realm.where(DASSTest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "STAI" ->
//            {
//                var testList = realm.where(STAITest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "ZUNG" ->
//            {
//                var testList = realm.where(ZUNGTest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//            "Hammilton Depression" ->
//            {
//                var testList = realm.where(HAMTest::class.java).equalTo("patientId" , patientId).findAll()
//                if (testList.size > 0)
//                    testdata.value = testList.get(testList.size -1)!!
//            }
//        }
//        return testdata
//    }

}