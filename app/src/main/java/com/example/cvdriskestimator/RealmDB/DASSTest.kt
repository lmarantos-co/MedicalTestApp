package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class DASSTest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "DASSTest"

    //DASS Test

    var patientDASSQ1: Int? = null

    var patientDASSQ2: Int? = null

    var patientDASSQ3: Int? = null

    var patientDASSQ4: Int? = null

    var patientDASSQ5: Int? = null

    var patientDASSQ6: Int? = null

    var patientDASSQ7: Int? = null

    var patientDASSQ8: Int? = null

    var patientDASSQ9: Int? = null

    var patientDASSQ10: Int? = null

    var patientDASSQ11: Int? = null

    var patientDASSQ12: Int? = null

    var patientDASSQ13: Int? = null

    var patientDASSQ14: Int? = null

    var patientDASSQ15: Int? = null

    var patientDASSQ16: Int? = null

    var patientDASSQ17: Int? = null

    var patientDASSQ18: Int? = null

    var patientDASSQ19: Int? = null

    var patientDASSQ20: Int? = null

    var patientDASSQ21: Int? = null

    var dassStressResult: Int? = null
    var dassAnxietyResult: Int? = null
    var dassDepressionResult: Int? = null

}