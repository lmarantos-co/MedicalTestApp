package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BAITest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "BAITest"

    //variables related with Beck Anxiety Inventory Test

    var patientBAIQ1: Int? = null


    var patientBAIQ2: Int? = null


    var patientBAIQ3: Int? = null


    var patientBAIQ4: Int? = null


    var patientBAIQ5: Int? = null


    var patientBAIQ6: Int? = null


    var patientBAIQ7: Int? = null


    var patientBAIQ8: Int? = null


    var patientBAIQ9: Int? = null


    var patientBAIQ10: Int? = null


    var patientBAIQ11: Int? = null


    var patientBAIQ12: Int? = null


    var patientBAIQ13: Int? = null

    var patientBAIQ14: Int? = null


    var patientBAIQ15: Int? = null


    var patientBAIQ16: Int? = null


    var patientBAIQ17: Int? = null


    var patientBAIQ18: Int? = null


    var patientBAIQ19: Int? = null


    var patientBAIQ20: Int? = null


    var patientBAIQ21: Int? = null

    var patientBAITestResult: Int? = null

}

