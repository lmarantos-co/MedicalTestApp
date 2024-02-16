package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BDITest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "BDITest"

    //variables related with Beck Depression Inventory Test

    var patientBDIQ1: Int? = null


    var patientBDIQ2: Int? = null


    var patientBDIQ3: Int? = null


    var patientBDIQ4: Int? = null


    var patientBDIQ5: Int? = null


    var patientBDIQ6: Int? = null


    var patientBDIQ7: Int? = null


    var patientBDIQ8: Int? = null


    var patientBDIQ9: Int? = null


    var patientBDIQ10: Int? = null


    var patientBDIQ11: Int? = null


    var patientBDIQ12: Int? = null


    var patientBDIQ13: Int? = null

    var patientBDIQ14: Int? = null


    var patientBDIQ15: Int? = null


    var patientBDIQ16: Int? = null


    var patientBDIQ17: Int? = null


    var patientBDIQ18: Int? = null


    var patientBDIQ19: Int? = null


    var patientBDIQ20: Int? = null


    var patientBDIQ21: Int? = null

    var patientBDITestResult: Int? = null

}