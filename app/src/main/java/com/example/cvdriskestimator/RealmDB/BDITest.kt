package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BDITest : RealmModel {

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

    var testName: String?
        get() = test.testName
        set(value) {
            test.testName = "BDITest"
        }


    //variables related with Beck Depression Inventory Test

    var patientBDIQ1: Int?
        get() = test.patientBDIQ1
        set(value) {
            test.patientBDIQ1 = value
        }

    var patientBDIQ2: Int?
        get() = test.patientBDIQ2
        set(value) {
            test.patientBDIQ2 = value
        }

    var patientBDIQ3: Int?
        get() = test.patientBDIQ3
        set(value) {
            test.patientBDIQ3 = value
        }

    var patientBDIQ4: Int?
        get() = test.patientBDIQ4
        set(value) {
            test.patientBDIQ4 = value
        }

    var patientBDIQ5: Int?
        get() = test.patientBDIQ5
        set(value) {
            test.patientBDIQ5 = value
        }

    var patientBDIQ6: Int?
        get() = test.patientBDIQ6
        set(value) {
            test.patientBDIQ6 = value
        }

    var patientBDIQ7: Int?
        get() = test.patientBDIQ7
        set(value) {
            test.patientBDIQ7 = value
        }

    var patientBDIQ8: Int?
        get() = test.patientBDIQ8
        set(value) {
            test.patientBDIQ8 = value
        }

    var patientBDIQ9: Int?
        get() = test.patientBDIQ9
        set(value) {
            test.patientBDIQ9 = value
        }

    var patientBDIQ10: Int?
        get() = test.patientBDIQ10
        set(value) {
            test.patientBDIQ10 = value
        }

    var patientBDIQ11: Int?
        get() = test.patientBDIQ11
        set(value) {
            test.patientBDIQ11 = value
        }

    var patientBDIQ12: Int?
        get() = test.patientBDIQ12
        set(value) {
            test.patientBDIQ12 = value
        }

    var patientBDIQ13: Int?
        get() = test.patientBDIQ13
        set(value) {
            test.patientBDIQ13 = value
        }

    var patientBDIQ14: Int?
        get() = test.patientBDIQ14
        set(value) {
            test.patientBDIQ14 = value
        }

    var patientBDIQ15: Int?
        get() = test.patientBDIQ15
        set(value) {
            test.patientBDIQ15 = value
        }

    var patientBDIQ16: Int?
        get() = test.patientBDIQ16
        set(value) {
            test.patientBDIQ16 = value
        }

    var patientBDIQ17: Int?
        get() = test.patientBDIQ17
        set(value) {
            test.patientBDIQ17 = value
        }

    var patientBDIQ18: Int?
        get() = test.patientBDIQ18
        set(value) {
            test.patientBDIQ18 = value
        }

    var patientBDIQ19: Int?
        get() = test.patientBDIQ19
        set(value) {
            test.patientBDIQ19 = value
        }

    var patientBDIQ20: Int?
        get() = test.patientBDIQ20
        set(value) {
            test.patientBDIQ20 = value
        }

    var patientBDIQ21: Int?
        get() = test.patientBDIQ21
        set(value) {
            test.patientBDIQ21 = value
        }

    var patientBDITestResult: Int?
        get() = test.patientBDITestResult
        set(value) {
            test.patientBDITestResult = value
        }


}