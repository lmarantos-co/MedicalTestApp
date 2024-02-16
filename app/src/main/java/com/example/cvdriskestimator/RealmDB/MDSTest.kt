package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class MDSTest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "MDSTest"

    //variables related wth the Med Diet Score Test

    var patientMDSQ1: Int? = null

    var patientMDSQ2: Int? = null

    var patientMDSQ3: Int? = null

    var patientMDSQ4: Int? = null

    var patientMDSQ5: Int? = null

    var patientMDSQ6: Int? = null

    var patientMDSQ7: Int? = null

    var patientMDSQ8: Int? = null

    var patientMDSQ9: Int? = null

    var patientMDSQ10: Int? = null

    var patientMDSQ11: Int? = null

    var patientMDSTestResult: Int? = null

}
