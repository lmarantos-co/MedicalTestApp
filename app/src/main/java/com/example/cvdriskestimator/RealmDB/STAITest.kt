package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date


@RealmClass
open class STAITest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "STAITest"

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

}