package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class ZUNGTest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "ZUNGTest"

    //ZUNG Test

    var patientZUNGQ1: Int? = null

    var patientZUNGQ2: Int? = null

    var patientZUNGQ3: Int? = null

    var patientZUNGQ4: Int? = null

    var patientZUNGQ5: Int? = null

    var patientZUNGQ6: Int? = null

    var patientZUNGQ7: Int? = null

    var patientZUNGQ8: Int? = null

    var patientZUNGQ9: Int? = null

    var patientZUNGQ10: Int? = null

    var patientZUNGQ11: Int? = null

    var patientZUNGQ12: Int? = null

    var patientZUNGQ13: Int? = null

    var patientZUNGQ14: Int? = null

    var patientZUNGQ15: Int? = null

    var patientZUNGQ16: Int? = null

    var patientZUNGQ17: Int? = null

    var patientZUNGQ18: Int? = null

    var patientZUNGQ19: Int? = null

    var patientZUNGQ20: Int? = null

    var zungTestReesult: Int? = null
}