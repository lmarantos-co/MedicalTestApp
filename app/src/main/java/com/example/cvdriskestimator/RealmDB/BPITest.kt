package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BPITest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "BPITest"

    //variables related with the Brief Pain Inventory Index

    var patientBPIQ1: Int? = null

    var patientBPIQ2: Int? = null

    var patientBPIQ3: Int? = null

    var patientBPIQ4: Int? = null

    var patientBPIQ5: Int? = null

    var patientBPIQ6: Int? = null

    var patientBPIQ7: Int? = null

    var patientBPIQ8: Int? = null

    var patientBPIQ9: Int? = null

    var patientBPIQ10: Int? = null

    var patientBPIQ11: Int? = null

    var patientBPIQ12: Int? = null

    var patientBPIcircleX: Float? = null

    var patientBPIcircleY: Float? = null

    var patientBPITestSeverityResult: Float? = null

    var patientBPITestInterferenceResult: Float? = null

}