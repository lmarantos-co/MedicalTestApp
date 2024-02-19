package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BAITest : RealmModel  {

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
            test.testName = "BAITest"
        }



    //variables related with Beck Anxiety Inventory Test

    var patientBAIQ1: Int?
        get() = test.patientBAIQ1
        set(value) {
            test.patientBAIQ1 = value
        }

    var patientBAIQ2: Int?
        get() = test.patientBAIQ2
        set(value) {
            test.patientBAIQ2 = value
        }

    var patientBAIQ3: Int?
        get() = test.patientBAIQ3
        set(value) {
            test.patientBAIQ3 = value
        }

    var patientBAIQ4: Int?
        get() = test.patientBAIQ4
        set(value) {
            test.patientBAIQ4 = value
        }

    var patientBAIQ5: Int?
        get() = test.patientBAIQ5
        set(value) {
            test.patientBAIQ5 = value
        }

    var patientBAIQ6: Int?
        get() = test.patientBAIQ6
        set(value) {
            test.patientBAIQ6 = value
        }

    var patientBAIQ7: Int?
        get() = test.patientBAIQ7
        set(value) {
            test.patientBAIQ7 = value
        }

    var patientBAIQ8: Int?
        get() = test.patientBAIQ8
        set(value) {
            test.patientBAIQ8 = value
        }

    var patientBAIQ9: Int?
        get() = test.patientBAIQ9
        set(value) {
            test.patientBAIQ9 = value
        }

    var patientBAIQ10: Int?
        get() = test.patientBAIQ10
        set(value) {
            test.patientBAIQ10 = value
        }

    var patientBAIQ11: Int?
        get() = test.patientBAIQ11
        set(value) {
            test.patientBAIQ11 = value
        }

    var patientBAIQ12: Int?
        get() = test.patientBAIQ12
        set(value) {
            test.patientBAIQ12 = value
        }

    var patientBAIQ13: Int?
        get() = test.patientBAIQ13
        set(value) {
            test.patientBAIQ13 = value
        }

    var patientBAIQ14: Int?
        get() = test.patientBAIQ14
        set(value) {
            test.patientBAIQ14 = value
        }

    var patientBAIQ15: Int?
        get() = test.patientBAIQ15
        set(value) {
            test.patientBAIQ15 = value
        }

    var patientBAIQ16: Int?
        get() = test.patientBAIQ16
        set(value) {
            test.patientBAIQ16 = value
        }

    var patientBAIQ17: Int?
        get() = test.patientBAIQ17
        set(value) {
            test.patientBAIQ17 = value
        }

    var patientBAIQ18: Int?
        get() = test.patientBAIQ18
        set(value) {
            test.patientBAIQ18 = value
        }

    var patientBAIQ19: Int?
        get() = test.patientBAIQ19
        set(value) {
            test.patientBAIQ19 = value
        }

    var patientBAIQ20: Int?
        get() = test.patientBAIQ20
        set(value) {
            test.patientBAIQ20 = value
        }

    var patientBAIQ21: Int?
        get() = test.patientBAIQ21
        set(value) {
            test.patientBAIQ21 = value
        }

    var patientBAITestResult: Int?
        get() = test.patientBAITestResult
        set(value) {
            test.patientBAITestResult = value
        }

}

