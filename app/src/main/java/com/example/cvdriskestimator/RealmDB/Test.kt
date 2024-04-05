package com.example.cvdriskestimator.RealmDB

import androidx.room.ForeignKey
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.*

@RealmClass
open class Test : RealmModel
{
    @PrimaryKey
    var testId : String = ""

    @Required
    var patientId : String = ""

    var testDate : Date? = null

    @Required
    var testName : String = ""

    //CVD TEST

    @Required
    var patientAge : String = ""

    @Required
    var patientSex : String = ""

    @Required
    var patientRace : String = ""

    @Required
    var SSB : String = ""

    @Required
    var TCH : String = ""

    @Required
    var HDL : String = ""

    @Required
    var smoker : String = ""

    var cvdTestResult : Int? = null

    //DIABETES CHECK

    @Required
    var treatment : String = ""

    @Required
    var patientPAM : String = ""

    @Required
    var patientSteroids : String = ""

    @Required
    var patientBMI : String = ""

    @Required
    var patientSiblings : String = ""

    var diabetesTestResult : Double? = null


    //variables related with Major Depression Inventory Test

    var patientMDIQ1 : Int? = null


    var patientMDIQ2 : Int? = null


    var patientMDIQ3 : Int? = null


    var patientMDIQ4 : Int? = null


    var patientMDIQ5 : Int? = null


    var patientMDIQ6 : Int? = null


    var patientMDIQ7 : Int? = null


    var patientMDIQ8 : Int? = null


    var patientMDIQ9 : Int? = null


    var patientMDIQ10 : Int? = null


    var patientMDIQ11 : Int? = null


    var patientMDIQ12 : Int? = null


    var patientMDIQ13 : Int? = null

    var patientMDITestResult : Int? = null

    //variables related with Beck Anxiety Inventory Test

    var patientBAIQ1 : Int? = null


    var patientBAIQ2 : Int? = null


    var patientBAIQ3 : Int? = null


    var patientBAIQ4 : Int? = null


    var patientBAIQ5 : Int? = null


    var patientBAIQ6 : Int? = null


    var patientBAIQ7 : Int? = null


    var patientBAIQ8 : Int? = null


    var patientBAIQ9 : Int? = null


    var patientBAIQ10 : Int? = null


    var patientBAIQ11 : Int? = null


    var patientBAIQ12 : Int? = null


    var patientBAIQ13 : Int? = null

    var patientBAIQ14 : Int? = null


    var patientBAIQ15 : Int? = null


    var patientBAIQ16 : Int? = null


    var patientBAIQ17 : Int? = null


    var patientBAIQ18 : Int? = null


    var patientBAIQ19 : Int? = null


    var patientBAIQ20 : Int? = null


    var patientBAIQ21 : Int? = null

    var patientBAITestResult : Int? = null

    //variables related wth the Med Diet Score Test

    var patientMDSQ1 : Int? = null

    var patientMDSQ2 : Int? = null

    var patientMDSQ3 : Int? = null

    var patientMDSQ4: Int? = null

    var patientMDSQ5 : Int? = null

    var patientMDSQ6 : Int? = null

    var patientMDSQ7 : Int? = null

    var patientMDSQ8 : Int? = null

    var patientMDSQ9 : Int? = null

    var patientMDSQ10 : Int? = null

    var patientMDSQ11 : Int? = null

    var patientMDSTestResult : Int? = null

    //variables related with the Brief Pain Inventory Index

    var patientBPIQ1 : Int? = null

    var patientBPIQ2 : Int? = null

    var patientBPIQ3 : Int? = null

    var patientBPIQ4 : Int? = null

    var patientBPIQ5 : Int? = null

    var patientBPIQ6 : Int? = null

    var patientBPIQ7 : Int? = null

    var patientBPIQ8 : Int? = null

    var patientBPIQ9 : Int? = null

    var patientBPIQ10 : Int? = null

    var patientBPIQ11 : Int? = null

    var patientBPIQ12 : Int? = null

    var patientBPIcircleX : Float? = null

    var patientBPIcircleY : Float? = null

    var patientBPITestSeverityResult : Float? = null

    var patientBPITestInterferenceResult : Float? = null


    //variables related with GDS Test

    var patientGDSQ1 : Int? = null

    var patientGDSQ2 : Int? = null

    var patientGDSQ3 : Int? = null

    var patientGDSQ4 : Int? = null

    var patientGDSQ5 : Int? = null

    var patientGDSQ6 : Int? = null

    var patientGDSQ7 : Int? = null

    var patientGDSQ8 : Int? = null

    var patientGDSQ9 : Int? = null

    var patientGDSQ10 : Int? = null

    var patientGDSQ11 : Int? = null

    var patientGDSQ12 : Int? = null

    var patientGDSQ13 : Int? = null

    var patientGDSQ14 : Int? = null

    var patientGDSQ15 : Int? = null

    var patientGDSTestResult : Int? = null

    //variables related with the PDQ Test

    var patientPDQQ1 : Int? = null

    var patientPDQQ2 : Int? = null

    var patientPDQQ3 : Int? = null

    var patientPDQQ4 : Int? = null

    var patientPDQQ5 : Int? = null

    var patientPDQQ6 : Int? = null

    var patientPDQQ7 : Int? = null

    var patientPDQQ8 : Int? = null

    var patientPDQQ9 : Int? = null

    var patientPDQQ10 : Int? = null

    var patientPDQQ11 : Int? = null

    var patientPDQQ12 : Int? = null

    var patientPDQQ13 : Int? = null

    var patientPDQQ14 : Int? = null

    var patientPDQQ15 : Int? = null

    var patientPDQQ16 : Int? = null

    var patientPDQQ17 : Int? = null

    var patientPDQQ18 : Int? = null

    var patientPDQQ19 : Int? = null

    var patientPDQQ20 : Int? = null

    var patientPDQQ21 : Int? = null

    var patientPDQQ22 : Int? = null

    var patientPDQQ23 : Int? = null

    var patientPDQQ24 : Int? = null

    var patientPDQQ25 : Int? = null

    var patientPDQQ26 : Int? = null

    var patientPDQQ27 : Int? = null

    var patientPDQQ28 : Int? = null

    var patientPDQQ29 : Int? = null

    var patientPDQQ30 : Int? = null

    var patientPDQQ31 : Int? = null

    var patientPDQQ32 : Int? = null

    var patientPDQQ33 : Int? = null

    var patientPDQQ34 : Int? = null

    var patientPDQQ35 : Int? = null

    var patientPDQQ36 : Int? = null

    var patientPDQQ37 : Int? = null

    var patientPDQQ38 : Int? = null

    var patientPDQQ39 : Int? = null

    //variables related with Beck Depression Inventory Test

    var patientBDIQ1 : Int? = null


    var patientBDIQ2 : Int? = null


    var patientBDIQ3 : Int? = null


    var patientBDIQ4 : Int? = null


    var patientBDIQ5 : Int? = null


    var patientBDIQ6 : Int? = null


    var patientBDIQ7 : Int? = null


    var patientBDIQ8 : Int? = null


    var patientBDIQ9 : Int? = null


    var patientBDIQ10 : Int? = null


    var patientBDIQ11 : Int? = null


    var patientBDIQ12 : Int? = null


    var patientBDIQ13 : Int? = null

    var patientBDIQ14 : Int? = null


    var patientBDIQ15 : Int? = null


    var patientBDIQ16 : Int? = null


    var patientBDIQ17 : Int? = null


    var patientBDIQ18 : Int? = null


    var patientBDIQ19 : Int? = null


    var patientBDIQ20 : Int? = null


    var patientBDIQ21 : Int? = null

    var patientBDITestResult : Int? = null


    //variables related with hamilton Depression

    var patientHAMDQ1 : Int? = null


    var patientHAMDQ2 : Int? = null


    var patientHAMDQ3 : Int? = null


    var patientHAMDQ4 : Int? = null


    var patientHAMDQ5 : Int? = null


    var patientHAMDQ6 : Int? = null


    var patientHAMDQ7 : Int? = null


    var patientHAMDQ8 : Int? = null


    var patientHAMDQ9 : Int? = null


    var patientHAMDQ10 : Int? = null


    var patientHAMDQ11 : Int? = null


    var patientHAMDQ12 : Int? = null


    var patientHAMDQ13 : Int? = null

    var patientHAMDQ14 : Int? = null


    var patientHAMDQ15 : Int? = null


    var patientHAMDQ16 : Int? = null


    var patientHAMDQ17 : Int? = null


    var patientHAMDTestResult : Int? = null


    //Stai Test

    //state

    var patientSTAISQ1 : Int? = null


    var patientSTAISQ2 : Int? = null


    var patientSTAISQ3 : Int? = null


    var patientSTAISQ4 : Int? = null


    var patientSTAISQ5 : Int? = null


    var patientSTAISQ6 : Int? = null


    var patientSTAISQ7 : Int? = null


    var patientSTAISQ8 : Int? = null


    var patientSTAISQ9 : Int? = null


    var patientSTAISQ10 : Int? = null


    var patientSTAISQ11 : Int? = null


    var patientSTAISQ12 : Int? = null


    var patientSTAISQ13 : Int? = null

    var patientSTAISQ14 : Int? = null

    var patientSTAISQ15 : Int? = null

    var patientSTAISQ16 : Int? = null

    var patientSTAISQ17 : Int? = null

    var patientSTAISQ18 : Int? = null

    var patientSTAISQ19 : Int? = null

    var patientSTAISQ20 : Int? = null


    //trait

    var patientSTAITQ21 : Int? = null


    var patientSTAITQ22 : Int? = null


    var patientSTAITQ23 : Int? = null


    var patientSTAITQ24 : Int? = null


    var patientSTAITQ25 : Int? = null


    var patientSTAITQ26 : Int? = null


    var patientSTAITQ27 : Int? = null


    var patientSTAITQ28 : Int? = null


    var patientSTAITQ29 : Int? = null


    var patientSTAITQ30 : Int? = null


    var patientSTAITQ31 : Int? = null


    var patientSTAITQ32 : Int? = null

    var patientSTAITQ33 : Int? = null

    var patientSTAITQ34 : Int? = null

    var patientSTAITQ35 : Int? = null

    var patientSTAITQ36 : Int? = null

    var patientSTAITQ37 : Int? = null

    var patientSTAITQ38 : Int? = null

    var patientSTAITQ39 : Int? = null

    var patientSTAITQ40 : Int? = null

    var patientSTAISScore : Int? = null
    var patientSTAITScore : Int? = null

    //DASS Test

    var patientDASSQ1 : Int? = null

    var patientDASSQ2 : Int? = null

    var patientDASSQ3 : Int? = null

    var patientDASSQ4 : Int? = null

    var patientDASSQ5 : Int? = null

    var patientDASSQ6 : Int? = null

    var patientDASSQ7 : Int? = null

    var patientDASSQ8 : Int? = null

    var patientDASSQ9 : Int? = null

    var patientDASSQ10 : Int? = null

    var patientDASSQ11 : Int? = null

    var patientDASSQ12 : Int? = null

    var patientDASSQ13 : Int? = null

    var patientDASSQ14 : Int? = null

    var patientDASSQ15 : Int? = null

    var patientDASSQ16 : Int? = null

    var patientDASSQ17 : Int? = null

    var patientDASSQ18 : Int? = null

    var patientDASSQ19 : Int? = null

    var patientDASSQ20 : Int? = null

    var patientDASSQ21 : Int? = null

    var dassStressResult : Int? = null
    var dassAnxietyResult : Int? = null
    var dassDepressionResult : Int? = null


    //ZUNG Test

    var patientZUNGQ1 : Int? = null

    var patientZUNGQ2 : Int? = null

    var patientZUNGQ3 : Int? = null

    var patientZUNGQ4 : Int? = null

    var patientZUNGQ5 : Int? = null

    var patientZUNGQ6 : Int? = null

    var patientZUNGQ7 : Int? = null

    var patientZUNGQ8 : Int? = null

    var patientZUNGQ9 : Int? = null

    var patientZUNGQ10 : Int? = null

    var patientZUNGQ11 : Int? = null

    var patientZUNGQ12 : Int? = null

    var patientZUNGQ13 : Int? = null

    var patientZUNGQ14 : Int? = null

    var patientZUNGQ15 : Int? = null

    var patientZUNGQ16 : Int? = null

    var patientZUNGQ17 : Int? = null

    var patientZUNGQ18 : Int? = null

    var patientZUNGQ19 : Int? = null

    var patientZUNGQ20 : Int? = null

    var zungTestReesult : Int? = null

    //OPQOL Test

    var patientOPQOLQ1 : Int? = null

    fun getOPQOLQ1() : Int?
    {
        return patientOPQOLQ1
    }

    var patientOPQOLQ2 : Int? = null

    fun getOPQOLQ2() : Int?
    {
        return patientOPQOLQ2
    }

    var patientOPQOLQ3 : Int? = null

    fun getOPQOLQ3() : Int?
    {
        return patientOPQOLQ3
    }

    var patientOPQOLQ4 : Int? = null

    fun getOPQOLQ4() : Int?
    {
        return patientOPQOLQ4
    }

    var patientOPQOLQ5 : Int? = null

    fun getOPQOLQ5() : Int?
    {
        return patientOPQOLQ5
    }

    var patientOPQOLQ6 : Int? = null

    fun getOPQOLQ6() : Int?
    {
        return patientOPQOLQ6
    }

    var patientOPQOLQ7 : Int? = null

    fun getOPQOLQ7() : Int?
    {
        return patientOPQOLQ7
    }

    var patientOPQOLQ8 : Int? = null

    fun getOPQOLQ8() : Int?
    {
        return patientOPQOLQ8
    }

    var patientOPQOLQ9 : Int? = null

    fun getOPQOLQ9() : Int?
    {
        return patientOPQOLQ9
    }

    var patientOPQOLQ10 : Int? = null

    fun getOPQOLQ10() : Int?
    {
        return patientOPQOLQ10
    }

    var patientOPQOLQ11 : Int? = null

    fun getOPQOLQ11() : Int?
    {
        return patientOPQOLQ11
    }

    var patientOPQOLQ12 : Int? = null

    fun getOPQOLQ12() : Int?
    {
        return patientOPQOLQ12
    }

    var patientOPQOLQ13 : Int? = null

    fun getOPQOLQ13() : Int?
    {
        return patientOPQOLQ13
    }

    var patientOPQOLQ14 : Int? = null

    fun getOPQOLQ14() : Int?
    {
        return patientOPQOLQ14
    }

    var patientOPQOLQ15 : Int? = null

    fun getOPQOLQ15() : Int?
    {
        return patientOPQOLQ15
    }

    var patientOPQOLQ16 : Int? = null

    fun getOPQOLQ16() : Int?
    {
        return patientOPQOLQ16
    }

    var patientOPQOLQ17 : Int? = null

    fun getOPQOLQ17() : Int?
    {
        return patientOPQOLQ17
    }

    var patientOPQOLQ18 : Int? = null

    fun getOPQOLQ18() : Int?
    {
        return patientOPQOLQ18
    }

    var patientOPQOLQ19 : Int? = null

    fun getOPQOLQ19() : Int?
    {
        return patientOPQOLQ19
    }

    var patientOPQOLQ20 : Int? = null

    fun getOPQOLQ20() : Int?
    {
        return patientOPQOLQ20
    }

    var patientOPQOLQ21 : Int? = null

    fun getOPQOLQ21() : Int?
    {
        return patientOPQOLQ21
    }

    var patientOPQOLQ22 : Int? = null

    fun getOPQOLQ22() : Int?
    {
        return patientOPQOLQ22
    }

    var patientOPQOLQ23 : Int? = null

    fun getOPQOLQ23() : Int?
    {
        return patientOPQOLQ23
    }

    var patientOPQOLQ24 : Int? = null

    fun getOPQOLQ24() : Int?
    {
        return patientOPQOLQ24
    }

    var patientOPQOLQ25 : Int? = null

    fun getOPQOLQ25() : Int?
    {
        return patientOPQOLQ25
    }

    var patientOPQOLQ26 : Int? = null

    fun getOPQOLQ26() : Int?
    {
        return patientOPQOLQ26
    }

    var patientOPQOLQ27 : Int? = null

    fun getOPQOLQ27() : Int?
    {
        return patientOPQOLQ27
    }

    var patientOPQOLQ28 : Int? = null

    fun getOPQOLQ28() : Int?
    {
        return patientOPQOLQ28
    }

    var patientOPQOLQ29 : Int? = null

    fun getOPQOLQ29() : Int?
    {
        return patientOPQOLQ29
    }

    var patientOPQOLQ30 : Int? = null

    fun getOPQOLQ30() : Int?
    {
        return patientOPQOLQ30
    }

    var patientOPQOLQ31 : Int? = null

    fun getOPQOLQ31() : Int?
    {
        return patientOPQOLQ31
    }

    var patientOPQOLQ32 : Int? = null

    fun getOPQOLQ32() : Int?
    {
        return patientOPQOLQ32
    }

    var patientOPQOLQ33 : Int? = null

    fun getOPQOLQ33() : Int?
    {
        return patientOPQOLQ33
    }

    var patientOPQOLQ34 : Int? = null

    fun getOPQOLQ34() : Int?
    {
        return patientOPQOLQ34
    }

    var patientOPQOLQ35 : Int? = null

    fun getOPQOLQ35() : Int?
    {
        return patientOPQOLQ1
    }

    var OPQOLTestReesult : Int? = null

    fun getOPQOLQResult() : Int?
    {
        return OPQOLTestReesult
    }

    //variables related with GAS Test

    var patientGASQ1 : Int? = null

    var patientGASQ2 : Int? = null

    var patientGASQ3 : Int? = null

    var patientGASQ4 : Int? = null

    var patientGASQ5 : Int? = null

    var patientGASQ6 : Int? = null

    var patientGASQ7 : Int? = null

    var patientGASQ8 : Int? = null

    var patientGASQ9 : Int? = null

    var patientGASQ10 : Int? = null

    var patientGASQ11 : Int? = null

    var patientGASQ12 : Int? = null

    var patientGASQ13 : Int? = null

    var patientGASQ14 : Int? = null

    var patientGASQ15 : Int? = null

    var patientGASQ16 : Int? = null

    var patientGASQ17 : Int? = null

    var patientGASQ18 : Int? = null

    var patientGASQ19 : Int? = null

    var patientGASQ20 : Int? = null

    var patientGASQ21 : Int? = null

    var patientGASQ22 : Int? = null

    var patientGASQ23 : Int? = null

    var patientGASQ24 : Int? = null

    var patientGASQ25 : Int? = null

    var patientGASQ26 : Int? = null

    var patientGASQ27 : Int? = null

    var patientGASQ28 : Int? = null

    var patientGASQ29 : Int? = null

    var patientGASQ30 : Int? = null

    var patientGASTestResult : Int? = null

    var patientGASSomatic : Int? = null
    var patientGASCognitive : Int? = null
    var patientGASAffective : Int? = null

    //SIDAS Test
    var patientSIDASQ1 : Int? = null

    var patientSIDASQ2 : Int? = null

    var patientSIDASQ3 : Int? = null

    var patientSIDASQ4 : Int? = null

    var patientSIDASQ5 : Int? = null

    var sidasTestResult : Int? = null

    fun resetAllfieldsToNull()
    {
        patientAge = ""

        patientSex = ""

        patientRace  = ""

        SSB  = ""

        TCH = ""

        HDL = ""

        smoker  = ""

        cvdTestResult = null

        //DIABETES CHECK

        treatment = ""

        patientPAM  = ""

        patientSteroids  = ""

        patientBMI = ""

        patientSiblings = ""

        diabetesTestResult  = null


        //variables related with Major Depression Inventory Test

        patientMDIQ1  = null


        patientMDIQ2 = null


        patientMDIQ3 = null


        patientMDIQ4 = null


        patientMDIQ5  = null


        patientMDIQ6 = null


        patientMDIQ7  = null


        patientMDIQ8  = null


        patientMDIQ9  = null


       patientMDIQ10  = null


        patientMDIQ11 = null


        patientMDIQ12 = null


        patientMDIQ13 = null

        patientMDITestResult = null

        //variables related with Beck Anxiety Inventory Test

        patientBAIQ1 = null


        patientBAIQ2 = null


        patientBAIQ3 = null


        patientBAIQ4 = null


        patientBAIQ5 = null


        patientBAIQ6 = null


        patientBAIQ7 = null


        patientBAIQ8 = null


        patientBAIQ9 = null


        patientBAIQ10 = null


        patientBAIQ11 = null


        patientBAIQ12 = null


        patientBAIQ13 = null

        patientBAIQ14 = null


        patientBAIQ15 = null


        patientBAIQ16 = null


        patientBAIQ17 = null

        patientBAIQ18  = null


        patientBAIQ19 = null


        patientBAIQ20 = null


        patientBAIQ21 = null

        patientBAITestResult = null

        //variables related wth the Med Diet Score Test

        patientMDSQ1 = null

        patientMDSQ2 = null

        patientMDSQ3 = null

        patientMDSQ4 = null

        patientMDSQ5= null

        patientMDSQ6= null

        patientMDSQ7 = null

        patientMDSQ8 = null

        patientMDSQ9= null

        patientMDSQ10= null

        patientMDSQ11 = null

        patientMDSTestResult= null

        //variables related with the Brief Pain Inventory Index

        patientBPIQ1 = null

        patientBPIQ2= null

        patientBPIQ3= null

        patientBPIQ4 = null

        patientBPIQ5 = null

        patientBPIQ6 = null

        patientBPIQ7= null

        patientBPIQ8= null

        patientBPIQ9= null

        patientBPIQ10 = null

        patientBPIQ11 = null

        patientBPIQ12 = null

        patientBPIcircleX = null

        patientBPIcircleY = null

        patientBPITestSeverityResult = null

        patientBPITestInterferenceResult = null


        //variables related with GDS Test

        patientGDSQ1 = null

        patientGDSQ2 = null

        patientGDSQ3  = null

        patientGDSQ4 = null

        patientGDSQ5 = null

        patientGDSQ6 = null

        patientGDSQ7 = null

        patientGDSQ8 = null

        patientGDSQ9 = null

        patientGDSQ10 = null

        patientGDSQ11 = null

        patientGDSQ12 = null

        patientGDSQ13 = null

        patientGDSQ14 = null

        patientGDSQ15 = null

        patientGDSTestResult = null

        //variables related with the PDQ Test

        patientPDQQ1 = null

        patientPDQQ2  = null

        patientPDQQ3 = null

        patientPDQQ4 = null

        patientPDQQ5 = null

        patientPDQQ6 = null

        patientPDQQ7  = null

        patientPDQQ8 = null

        patientPDQQ9= null

        patientPDQQ10 = null

        patientPDQQ11  = null

        patientPDQQ12 = null

        patientPDQQ13 = null

        patientPDQQ14 = null

        patientPDQQ15 = null

        patientPDQQ16 = null

        patientPDQQ17 = null

        patientPDQQ18 = null

        patientPDQQ19 = null

        patientPDQQ20 = null

        patientPDQQ21 = null

        patientPDQQ22 = null

        patientPDQQ23= null

        patientPDQQ24= null

       patientPDQQ25 = null

        patientPDQQ26 = null

        patientPDQQ27= null

        patientPDQQ28 = null

        patientPDQQ29 = null

        patientPDQQ30= null

        patientPDQQ31 = null

        patientPDQQ32  = null

        patientPDQQ33  = null

       patientPDQQ34 = null

        patientPDQQ35 = null

        patientPDQQ36 = null

        patientPDQQ37 = null

        patientPDQQ38 = null

        patientPDQQ39 = null

        //variables related with Beck Depression Inventory Test

        patientBDIQ1 = null


        patientBDIQ2 = null


        patientBDIQ3 = null


        patientBDIQ4 = null


        patientBDIQ5 = null


        patientBDIQ6 = null


        patientBDIQ7 = null


        patientBDIQ8 = null


        patientBDIQ9 = null


        patientBDIQ10 = null


        patientBDIQ11 = null


        patientBDIQ12 = null


        patientBDIQ13 = null

        patientBDIQ14 = null


        patientBDIQ15 = null


        patientBDIQ16 = null


        patientBDIQ17 = null


        patientBDIQ18 = null


        patientBDIQ19 = null


       patientBDIQ20 = null


        patientBDIQ21 = null

        patientBDITestResult= null


        //variables related with hamilton Depression

        patientHAMDQ1 = null


        patientHAMDQ2 = null


        patientHAMDQ3 = null


        patientHAMDQ4= null


        patientHAMDQ5= null


        patientHAMDQ6 = null


        patientHAMDQ7 =   null


        patientHAMDQ8 = null


        patientHAMDQ9= null


        patientHAMDQ10 = null


        patientHAMDQ11= null


        patientHAMDQ12 = null


        patientHAMDQ13= null

        patientHAMDQ14 = null


        patientHAMDQ15 = null


        patientHAMDQ16  = null


        patientHAMDQ17  = null


        patientHAMDTestResult  = null


        //Stai Test

        //state

        patientSTAISQ1 = null


        patientSTAISQ2  = null


        patientSTAISQ3 = null


        patientSTAISQ4= null


        patientSTAISQ5= null


        patientSTAISQ6= null


        patientSTAISQ7 = null


        patientSTAISQ8 = null


        patientSTAISQ9 = null


        patientSTAISQ10= null


        patientSTAISQ11 = null


        patientSTAISQ12 = null


        patientSTAISQ13 = null

        patientSTAISQ14 = null

        patientSTAISQ15 = null

        patientSTAISQ16 = null

        patientSTAISQ17 = null

        patientSTAISQ18 = null

        patientSTAISQ19 = null

        patientSTAISQ20 = null


        //trait

        patientSTAITQ21 = null


        patientSTAITQ22 = null


        patientSTAITQ23 = null


        patientSTAITQ24 = null


        patientSTAITQ25 = null


        patientSTAITQ26 = null


        patientSTAITQ27 =   null


        patientSTAITQ28 = null


        patientSTAITQ29 = null


        patientSTAITQ30= null


        patientSTAITQ31= null


        patientSTAITQ32 = null

        patientSTAITQ33 = null

        patientSTAITQ34 = null

        patientSTAITQ35  = null

        patientSTAITQ36 = null

        patientSTAITQ37 = null

        patientSTAITQ38 = null

        patientSTAITQ39 = null

        patientSTAITQ40 = null

        patientSTAISScore = null
        patientSTAITScore = null

        //DASS Test

        patientDASSQ1 = null

        patientDASSQ2 = null

        patientDASSQ3= null

        patientDASSQ4 = null

        patientDASSQ5= null

        patientDASSQ6= null

        patientDASSQ7 = null

        patientDASSQ8= null

        patientDASSQ9 = null

        patientDASSQ10= null

        patientDASSQ11= null

        patientDASSQ12 = null

        patientDASSQ13 = null

        patientDASSQ14 = null

        patientDASSQ15= null

        patientDASSQ16 =  null

        patientDASSQ17 = null

        patientDASSQ18= null

        patientDASSQ19= null

        patientDASSQ20 = null

        patientDASSQ21= null

        dassStressResult= null
        dassAnxietyResult= null
        dassDepressionResult= null


        //ZUNG Test

        patientZUNGQ1= null

        patientZUNGQ2 = null

        patientZUNGQ3 = null

        patientZUNGQ4 = null

        patientZUNGQ5 = null

        patientZUNGQ6 = null

        patientZUNGQ7 = null

        patientZUNGQ8 = null

        patientZUNGQ9 = null

        patientZUNGQ10 = null

        patientZUNGQ11 = null

        patientZUNGQ12 = null

        patientZUNGQ13 = null

        patientZUNGQ14= null

        patientZUNGQ15 = null

        patientZUNGQ16 = null

        patientZUNGQ17 = null

        patientZUNGQ18= null

        patientZUNGQ19 = null

        patientZUNGQ20 = null

        zungTestReesult = null

        //OPQOL Test

        //variables related with GAS Test

        patientGASQ1 = null

        patientGASQ2  = null

        patientGASQ3 = null

        patientGASQ4 = null

        patientGASQ5 = null

        patientGASQ6 = null

        patientGASQ7 = null

        patientGASQ8 = null

        patientGASQ9 = null

        patientGASQ10 = null

        patientGASQ11 = null

        patientGASQ12 = null

        patientGASQ13 = null

        patientGASQ14 = null

        patientGASQ15 = null

        patientGASQ16 = null

        patientGASQ17 = null

        patientGASQ18 = null

        patientGASQ19 = null
        patientGASQ20  = null

        patientGASQ21 = null

        patientGASQ22 = null

        patientGASQ23 = null

        patientGASQ24 = null

        patientGASQ25 = null

        patientGASQ26 = null

        patientGASQ27 = null

        patientGASQ28 = null

        patientGASQ29 = null

        patientGASQ30 = null

        patientGASTestResult= null

        patientGASSomatic = null
        patientGASCognitive = null
        patientGASAffective= null

        //SIDAS Test
        patientSIDASQ1 = null

        patientSIDASQ2 = null

        patientSIDASQ3 = null

        patientSIDASQ4 = null

       patientSIDASQ5 = null

       sidasTestResult = null
    }
}