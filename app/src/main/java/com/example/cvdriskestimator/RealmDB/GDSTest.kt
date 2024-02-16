package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class GDSTest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "GDSTest"

    //variables related with GDS Test

    var patientGDSQ1: Int? = null

    var patientGDSQ2: Int? = null

    var patientGDSQ3: Int? = null

    var patientGDSQ4: Int? = null

    var patientGDSQ5: Int? = null

    var patientGDSQ6: Int? = null

    var patientGDSQ7: Int? = null

    var patientGDSQ8: Int? = null

    var patientGDSQ9: Int? = null

    var patientGDSQ10: Int? = null

    var patientGDSQ11: Int? = null

    var patientGDSQ12: Int? = null

    var patientGDSQ13: Int? = null

    var patientGDSQ14: Int? = null

    var patientGDSQ15: Int? = null

    var patientGDSTestResult: Int? = null

}