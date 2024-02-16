package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class HAMTest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "HAMTest"

    //variables related with hamilton Depression

    var patientHAMDQ1: Int? = null


    var patientHAMDQ2: Int? = null


    var patientHAMDQ3: Int? = null


    var patientHAMDQ4: Int? = null


    var patientHAMDQ5: Int? = null


    var patientHAMDQ6: Int? = null


    var patientHAMDQ7: Int? = null


    var patientHAMDQ8: Int? = null


    var patientHAMDQ9: Int? = null


    var patientHAMDQ10: Int? = null


    var patientHAMDQ11: Int? = null


    var patientHAMDQ12: Int? = null


    var patientHAMDQ13: Int? = null

    var patientHAMDQ14: Int? = null


    var patientHAMDQ15: Int? = null


    var patientHAMDQ16: Int? = null


    var patientHAMDQ17: Int? = null


    var patientHAMDTestResult: Int? = null

}