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
    open var testId : String = ""

    @Required
    open var patientId : String = ""

    @Required
    open var testDate : Date? = null

    @Required
    open var testName : String = ""

    //CVD TEST

    @Required
    open var patientAge : String = ""

    @Required
    open var patientSex : String = ""

    @Required
    open var patientRace : String = ""

    @Required
    open var SSB : String = ""

    @Required
    open var TCH : String = ""

    @Required
    open var HDL : String = ""

    @Required
    open var smoker : String = ""

    open var cvdTestResult : Int? = null

    //DIABETES CHECK

    @Required
    open var treatment : String = ""

    @Required
    open var patientPAM : String = ""

    @Required
    open var patientSteroids : String = ""

    @Required
    open var patientBMI : String = ""

    @Required
    open var patientSiblings : String = ""

   open var diabetesTestResult : Double? = null


    //variables related with Major Depression Inventory Test

    open var patientMDIQ1 : Int? = null


    open var patientMDIQ2 : Int? = null


    open var patientMDIQ3 : Int? = null


    open var patientMDIQ4 : Int? = null


    open var patientMDIQ5 : Int? = null


    open var patientMDIQ6 : Int? = null


    open var patientMDIQ7 : Int? = null


    open var patientMDIQ8 : Int? = null


    open var patientMDIQ9 : Int? = null


    open var patientMDIQ10 : Int? = null


    open var patientMDIQ11 : Int? = null


    open var patientMDIQ12 : Int? = null


    open var patientMDIQ13 : Int? = null

    open var patientMDITestResult : Int? = null

    //variables related with Beck Anxiety Inventory Test

    open var patientBAIQ1 : Int? = null


    open var patientBAIQ2 : Int? = null


    open var patientBAIQ3 : Int? = null


    open var patientBAIQ4 : Int? = null


    open var patientBAIQ5 : Int? = null


    open var patientBAIQ6 : Int? = null


    open var patientBAIQ7 : Int? = null


    open var patientBAIQ8 : Int? = null


    open var patientBAIQ9 : Int? = null


    open var patientBAIQ10 : Int? = null


    open var patientBAIQ11 : Int? = null


    open var patientBAIQ12 : Int? = null


    open var patientBAIQ13 : Int? = null

    open var patientBAIQ14 : Int? = null


    open var patientBAIQ15 : Int? = null


    open var patientBAIQ16 : Int? = null


    open var patientBAIQ17 : Int? = null


    open var patientBAIQ18 : Int? = null


    open var patientBAIQ19 : Int? = null


    open var patientBAIQ20 : Int? = null


    open var patientBAIQ21 : Int? = null

    open var patientBAITestResult : Int? = null

    //variables related wth the Med Diet Score Test

    open var patientMDSQ1 : Int? = null

    open var patientMDSQ2 : Int? = null

    open var patientMDSQ3 : Int? = null

    open var patientMDSQ4: Int? = null

    open var patientMDSQ5 : Int? = null

    open var patientMDSQ6 : Int? = null

    open var patientMDSQ7 : Int? = null

    open var patientMDSQ8 : Int? = null

    open var patientMDSQ9 : Int? = null

    open var patientMDSQ10 : Int? = null

    open var patientMDSQ11 : Int? = null

    open var patientMDSTestResult : Int? = null

    //variables related with the Brief Pain Inventory Index

    open var patientBPIQ1 : Int? = null

    open var patientBPIQ2 : Int? = null

    open var patientBPIQ3 : Int? = null

    open var patientBPIQ4 : Int? = null

    open var patientBPIQ5 : Int? = null

    open var patientBPIQ6 : Int? = null

    open var patientBPIQ7 : Int? = null

    open var patientBPIQ8 : Int? = null

    open var patientBPIQ9 : Int? = null

    open var patientBPIQ10 : Int? = null

    open var patientBPIQ11 : Int? = null

    open var patientBPIQ12 : Int? = null

    open var patientBPIcircleX : Float? = null

    open var patientBPIcircleY : Float? = null

    open var patientBPITestSeverityResult : Float? = null

    open var patientBPITestInterferenceResult : Float? = null


    //variables related with GDS Test

    open var patientGDSQ1 : Int? = null

    open var patientGDSQ2 : Int? = null

    open var patientGDSQ3 : Int? = null

    open var patientGDSQ4 : Int? = null

    open var patientGDSQ5 : Int? = null

    open var patientGDSQ6 : Int? = null

    open var patientGDSQ7 : Int? = null

    open var patientGDSQ8 : Int? = null

    open var patientGDSQ9 : Int? = null

    open var patientGDSQ10 : Int? = null

    open var patientGDSQ11 : Int? = null

    open var patientGDSQ12 : Int? = null

    open var patientGDSQ13 : Int? = null

    open var patientGDSQ14 : Int? = null

    open var patientGDSQ15 : Int? = null

    open var patientGDSTestResult : Int? = null

    //variables related with the PDQ Test

    open var patientPDQQ1 : Int? = null

    open var patientPDQQ2 : Int? = null

    open var patientPDQQ3 : Int? = null

    open var patientPDQQ4 : Int? = null

    open var patientPDQQ5 : Int? = null

    open var patientPDQQ6 : Int? = null

    open var patientPDQQ7 : Int? = null

    open var patientPDQQ8 : Int? = null

    open var patientPDQQ9 : Int? = null

    open var patientPDQQ10 : Int? = null

    open var patientPDQQ11 : Int? = null

    open var patientPDQQ12 : Int? = null

    open var patientPDQQ13 : Int? = null

    open var patientPDQQ14 : Int? = null

    open var patientPDQQ15 : Int? = null

    open var patientPDQQ16 : Int? = null

    open var patientPDQQ17 : Int? = null

    open var patientPDQQ18 : Int? = null

    open var patientPDQQ19 : Int? = null

    open var patientPDQQ20 : Int? = null

    open var patientPDQQ21 : Int? = null

    open var patientPDQQ22 : Int? = null

    open var patientPDQQ23 : Int? = null

    open var patientPDQQ24 : Int? = null

    open var patientPDQQ25 : Int? = null

    open var patientPDQQ26 : Int? = null

    open var patientPDQQ27 : Int? = null

    open var patientPDQQ28 : Int? = null

    open var patientPDQQ29 : Int? = null

    open var patientPDQQ30 : Int? = null

    open var patientPDQQ31 : Int? = null

    open var patientPDQQ32 : Int? = null

    open var patientPDQQ33 : Int? = null

    open var patientPDQQ34 : Int? = null

    open var patientPDQQ35 : Int? = null

    open var patientPDQQ36 : Int? = null

    open var patientPDQQ37 : Int? = null

    open var patientPDQQ38 : Int? = null

    open var patientPDQQ39 : Int? = null

    //variables related with Beck Depression Inventory Test

    open var patientBDIQ1 : Int? = null


    open var patientBDIQ2 : Int? = null


    open var patientBDIQ3 : Int? = null


    open var patientBDIQ4 : Int? = null


    open var patientBDIQ5 : Int? = null


    open var patientBDIQ6 : Int? = null


    open var patientBDIQ7 : Int? = null


    open var patientBDIQ8 : Int? = null


    open var patientBDIQ9 : Int? = null


    open var patientBDIQ10 : Int? = null


    open var patientBDIQ11 : Int? = null


    open var patientBDIQ12 : Int? = null


    open var patientBDIQ13 : Int? = null

    open var patientBDIQ14 : Int? = null


    open var patientBDIQ15 : Int? = null


    open var patientBDIQ16 : Int? = null


    open var patientBDIQ17 : Int? = null


    open var patientBDIQ18 : Int? = null


    open var patientBDIQ19 : Int? = null


    open var patientBDIQ20 : Int? = null


    open var patientBDIQ21 : Int? = null

    open var patientBDITestResult : Int? = null


    //variables related with hamilton Depression

    open var patientHAMDQ1 : Int? = null


    open var patientHAMDQ2 : Int? = null


    open var patientHAMDQ3 : Int? = null


    open var patientHAMDQ4 : Int? = null


    open var patientHAMDQ5 : Int? = null


    open var patientHAMDQ6 : Int? = null


    open var patientHAMDQ7 : Int? = null


    open var patientHAMDQ8 : Int? = null


    open var patientHAMDQ9 : Int? = null


    open var patientHAMDQ10 : Int? = null


    open var patientHAMDQ11 : Int? = null


    open var patientHAMDQ12 : Int? = null


    open var patientHAMDQ13 : Int? = null

    open var patientHAMDQ14 : Int? = null


    open var patientHAMDQ15 : Int? = null


    open var patientHAMDQ16 : Int? = null


    open var patientHAMDQ17 : Int? = null


    open var patientHAMDTestResult : Int? = null


    //Stai Test

    //state

    open var patientSTAISQ1 : Int? = null


    open var patientSTAISQ2 : Int? = null


    open var patientSTAISQ3 : Int? = null


    open var patientSTAISQ4 : Int? = null


    open var patientSTAISQ5 : Int? = null


    open var patientSTAISQ6 : Int? = null


    open var patientSTAISQ7 : Int? = null


    open var patientSTAISQ8 : Int? = null


    open var patientSTAISQ9 : Int? = null


    open var patientSTAISQ10 : Int? = null


    open var patientSTAISQ11 : Int? = null


    open var patientSTAISQ12 : Int? = null


    open var patientSTAISQ13 : Int? = null

    open var patientSTAISQ14 : Int? = null

    open var patientSTAISQ15 : Int? = null

    open var patientSTAISQ16 : Int? = null

    open var patientSTAISQ17 : Int? = null

    open var patientSTAISQ18 : Int? = null

    open var patientSTAISQ19 : Int? = null

    open var patientSTAISQ20 : Int? = null


    //trait

    open var patientSTAITQ21 : Int? = null


    open var patientSTAITQ22 : Int? = null


    open var patientSTAITQ23 : Int? = null


    open var patientSTAITQ24 : Int? = null


    open var patientSTAITQ25 : Int? = null


    open var patientSTAITQ26 : Int? = null


    open var patientSTAITQ27 : Int? = null


    open var patientSTAITQ28 : Int? = null


    open var patientSTAITQ29 : Int? = null


    open var patientSTAITQ30 : Int? = null


    open var patientSTAITQ31 : Int? = null


    open var patientSTAITQ32 : Int? = null

    open var patientSTAITQ33 : Int? = null

    open var patientSTAITQ34 : Int? = null

    open var patientSTAITQ35 : Int? = null

    open var patientSTAITQ36 : Int? = null

    open var patientSTAITQ37 : Int? = null

    open var patientSTAITQ38 : Int? = null

    open var patientSTAITQ39 : Int? = null

    open var patientSTAITQ40 : Int? = null

    open var patientSTAISScore : Int? = null
    open var patientSTAITScore : Int? = null

    //DASS Test

    open var patientDASSQ1 : Int? = null

    open var patientDASSQ2 : Int? = null

    open var patientDASSQ3 : Int? = null

    open var patientDASSQ4 : Int? = null

    open var patientDASSQ5 : Int? = null

    open var patientDASSQ6 : Int? = null

    open var patientDASSQ7 : Int? = null

    open var patientDASSQ8 : Int? = null

    open var patientDASSQ9 : Int? = null

    open var patientDASSQ10 : Int? = null

    open var patientDASSQ11 : Int? = null

    open var patientDASSQ12 : Int? = null

    open var patientDASSQ13 : Int? = null

    open var patientDASSQ14 : Int? = null

    open var patientDASSQ15 : Int? = null

    open var patientDASSQ16 : Int? = null

    open var patientDASSQ17 : Int? = null

    open var patientDASSQ18 : Int? = null

    open var patientDASSQ19 : Int? = null

    open var patientDASSQ20 : Int? = null

    open var patientDASSQ21 : Int? = null

    open var dassStressResult : Int? = null
    open var dassAnxietyResult : Int? = null
    open var dassDepressionResult : Int? = null


    //ZUNG Test

    open var patientZUNGQ1 : Int? = null

    open var patientZUNGQ2 : Int? = null

    open var patientZUNGQ3 : Int? = null

    open var patientZUNGQ4 : Int? = null

    open var patientZUNGQ5 : Int? = null

    open var patientZUNGQ6 : Int? = null

    open var patientZUNGQ7 : Int? = null

    open var patientZUNGQ8 : Int? = null

    open var patientZUNGQ9 : Int? = null

    open var patientZUNGQ10 : Int? = null

    open var patientZUNGQ11 : Int? = null

    open var patientZUNGQ12 : Int? = null

    open var patientZUNGQ13 : Int? = null

    open var patientZUNGQ14 : Int? = null

    open var patientZUNGQ15 : Int? = null

    open var patientZUNGQ16 : Int? = null

    open var patientZUNGQ17 : Int? = null

    open var patientZUNGQ18 : Int? = null

    open var patientZUNGQ19 : Int? = null

    open var patientZUNGQ20 : Int? = null

    open var zungTestReesult : Int? = null

}