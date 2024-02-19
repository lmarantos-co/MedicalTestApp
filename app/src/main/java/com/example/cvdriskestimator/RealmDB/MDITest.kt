package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class MDITest : RealmModel {

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
            test.testName = "MDIest"
        }

    //variables related with Major Depression Inventory Test

    var patientMDIQ1: Int?
        get() = test.patientMDIQ1
        set(value) {
            test.patientMDIQ1 = value
        }

    var patientMDIQ2: Int?
        get() = test.patientMDIQ2
        set(value) {
            test.patientMDIQ2 = value
        }

    var patientMDIQ3: Int?
        get() = test.patientMDIQ3
        set(value) {
            test.patientMDIQ3 = value
        }

    var patientMDIQ4: Int?
        get() = test.patientMDIQ4
        set(value) {
            test.patientMDIQ4 = value
        }

    var patientMDIQ5: Int?
        get() = test.patientMDIQ5
        set(value) {
            test.patientMDIQ5 = value
        }


    var patientMDIQ6: Int?
        get() = test.patientMDIQ6
        set(value) {
            test.patientMDIQ6 = value
        }

    var patientMDIQ7: Int?
        get() = test.patientMDIQ7
        set(value) {
            test.patientMDIQ7 = value
        }


    var patientMDIQ8: Int?
        get() = test.patientMDIQ8
        set(value) {
            test.patientMDIQ8 = value
        }

    var patientMDIQ9: Int?
        get() = test.patientMDIQ9
        set(value) {
            test.patientMDIQ9 = value
        }

    var patientMDIQ10: Int?
        get() = test.patientMDIQ10
        set(value) {
            test.patientMDIQ10 = value
        }

    var patientMDIQ11: Int?
        get() = test.patientMDIQ11
        set(value) {
            test.patientMDIQ11 = value
        }

    var patientMDIQ12: Int?
        get() = test.patientMDIQ12
        set(value) {
            test.patientMDIQ12 = value
        }

    var patientMDIQ13: Int?
        get() = test.patientMDIQ13
        set(value) {
            test.patientMDIQ13 = value
        }

    var patientMDITestResult: Int?
        get() = test.patientMDITestResult
        set(value) {
            test.patientMDITestResult = value
        }
}