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

    var patientOPQOLQ2 : Int? = null

    var patientOPQOLQ3 : Int? = null

    var patientOPQOLQ4 : Int? = null

    var patientOPQOLQ5 : Int? = null

    var patientOPQOLQ6 : Int? = null

    var patientOPQOLQ7 : Int? = null

    var patientOPQOLQ8 : Int? = null

    var patientOPQOLQ9 : Int? = null

    var patientOPQOLQ10 : Int? = null

    var patientOPQOLQ11 : Int? = null

    var patientOPQOLQ12 : Int? = null

    var patientOPQOLQ13 : Int? = null

    var patientOPQOLQ14 : Int? = null

    var patientOPQOLQ15 : Int? = null

    var patientOPQOLQ16 : Int? = null

    var patientOPQOLQ17 : Int? = null

    var patientOPQOLQ18 : Int? = null

    var patientOPQOLQ19 : Int? = null

    var patientOPQOLQ20 : Int? = null

    var patientOPQOLQ21 : Int? = null

    var patientOPQOLQ22 : Int? = null

    var patientOPQOLQ23 : Int? = null

    var patientOPQOLQ24 : Int? = null

    var patientOPQOLQ25 : Int? = null

    var patientOPQOLQ26 : Int? = null

    var patientOPQOLQ27 : Int? = null

    var patientOPQOLQ28 : Int? = null

    var patientOPQOLQ29 : Int? = null

    var patientOPQOLQ30 : Int? = null

    var patientOPQOLQ31 : Int? = null

    var patientOPQOLQ32 : Int? = null

    var patientOPQOLQ33 : Int? = null

    var patientOPQOLQ34 : Int? = null

    var patientOPQOLQ35 : Int? = null

    var OPQOLTestReesult : Int? = null

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

}