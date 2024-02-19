package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BPITest : RealmModel {

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
            test.testName = "BPITest"
        }


    var patientBPIQ1: Int?
        get() = test.patientBPIQ1
        set(value) {
            test.patientBPIQ1 = value
        }
    //variables related with the Brief Pain Inventory Index

    var patientBPIQ2: Int?
        get() = test.patientBPIQ2
        set(value) {
            test.patientBPIQ2 = value
        }


    var patientBPIQ3: Int?
        get() = test.patientBPIQ3
        set(value) {
            test.patientBPIQ3 = value
        }


    var patientBPIQ4: Int?
        get() = test.patientBPIQ4
        set(value) {
            test.patientBPIQ4 = value
        }

    var patientBPIQ5: Int?
        get() = test.patientBPIQ5
        set(value) {
            test.patientBPIQ5 = value
        }

    var patientBPIQ6: Int?
        get() = test.patientBPIQ6
        set(value) {
            test.patientBPIQ6 = value
        }

    var patientBPIQ7: Int?
        get() = test.patientBPIQ7
        set(value) {
            test.patientBPIQ7 = value
        }

    var patientBPIQ8: Int?
        get() = test.patientBPIQ8
        set(value) {
            test.patientBPIQ8 = value
        }

    var patientBPIQ9: Int?
        get() = test.patientBPIQ9
        set(value) {
            test.patientBPIQ9 = value
        }

    var patientBPIQ10: Int?
        get() = test.patientBPIQ10
        set(value) {
            test.patientBPIQ10 = value
        }

    var patientBPIQ11: Int?
        get() = test.patientBPIQ11
        set(value) {
            test.patientBPIQ11 = value
        }

    var patientBPIQ12: Int?
        get() = test.patientBPIQ12
        set(value) {
            test.patientBPIQ12 = value
        }

    var patientBPIcircleX: Float?
        get() = test.patientBPIcircleX
        set(value) {
            test.patientBPIcircleX = value
        }

    var patientBPIcircleY: Float?
        get() = test.patientBPIcircleY
        set(value) {
            test.patientBPIcircleY = value
        }

    var patientBPITestSeverityResult: Float?
        get() = test.patientBPITestSeverityResult
        set(value) {
            test.patientBPITestSeverityResult = value
        }

    var patientBPITestInterferenceResult: Float?
        get() = test.patientBPITestInterferenceResult
        set(value) {
            test.patientBPITestInterferenceResult = value
        }


}