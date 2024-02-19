package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class ZUNGTest : RealmModel {

    private var test = Test()

    var testId: String
        get() = test.testId
        set(value) {
            test.testId = value
        }

    var patientId: String
        get() = test.patientId
        set(value) {
            test.patientId = value
        }

    var testDate: Date?
        get() = test.testDate
        set(value) {
            test.testDate = value
        }

    var testName: String
        get() = test.testName
        set(value) {
            test.testName = "ZUNGTest"
        }

    //ZUNG Test

    var patientZUNGQ1: Int?
        get() = test.patientZUNGQ1
        set(value) {
            test.patientZUNGQ1 = value
        }

    var patientZUNGQ2: Int?
        get() = test.patientZUNGQ2
        set(value) {
            test.patientZUNGQ2 = value
        }

    var patientZUNGQ3: Int?
        get() = test.patientZUNGQ3
        set(value) {
            test.patientZUNGQ3 = value
        }

    var patientZUNGQ4: Int?
        get() = test.patientZUNGQ4
        set(value) {
            test.patientZUNGQ4 = value
        }

    var patientZUNGQ5: Int?
        get() = test.patientZUNGQ5
        set(value) {
            test.patientZUNGQ5 = value
        }

    var patientZUNGQ6: Int?
        get() = test.patientZUNGQ6
        set(value) {
            test.patientZUNGQ6 = value
        }

    var patientZUNGQ7: Int?
        get() = test.patientZUNGQ7
        set(value) {
            test.patientZUNGQ7 = value
        }

    var patientZUNGQ8: Int?
        get() = test.patientZUNGQ8
        set(value) {
            test.patientZUNGQ8 = value
        }

    var patientZUNGQ9: Int?
        get() = test.patientZUNGQ9
        set(value) {
            test.patientZUNGQ9 = value
        }


    var patientZUNGQ10: Int?
        get() = test.patientZUNGQ10
        set(value) {
            test.patientZUNGQ10 = value
        }

    var patientZUNGQ11: Int?
        get() = test.patientZUNGQ11
        set(value) {
            test.patientZUNGQ11 = value
        }

    var patientZUNGQ12: Int?
        get() = test.patientZUNGQ12
        set(value) {
            test.patientZUNGQ12 = value
        }

    var patientZUNGQ13: Int?
        get() = test.patientZUNGQ13
        set(value) {
            test.patientZUNGQ13 = value
        }

    var patientZUNGQ14: Int?
        get() = test.patientZUNGQ14
        set(value) {
            test.patientZUNGQ14 = value
        }

    var patientZUNGQ15: Int?
        get() = test.patientZUNGQ15
        set(value) {
            test.patientZUNGQ15 = value
        }

    var patientZUNGQ16: Int?
        get() = test.patientZUNGQ16
        set(value) {
            test.patientZUNGQ16 = value
        }

    var patientZUNGQ17: Int?
        get() = test.patientZUNGQ17
        set(value) {
            test.patientZUNGQ17 = value
        }

    var patientZUNGQ18: Int?
        get() = test.patientZUNGQ18
        set(value) {
            test.patientZUNGQ18 = value
        }

    var patientZUNGQ19: Int?
        get() = test.patientZUNGQ19
        set(value) {
            test.patientZUNGQ19 = value
        }

    var patientZUNGQ20: Int?
        get() = test.patientZUNGQ20
        set(value) {
            test.patientZUNGQ20 = value
        }

    var zungTestReesult: Int?
        get() = test.zungTestReesult
        set(value) {
            test.zungTestReesult = value
        }

}