package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class MDSTest : RealmModel {

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
            test.testName = "MDSTest"
        }


    //variables related wth the Med Diet Score Test
    var patientMDSQ1: Int?
        get() = test.patientMDSQ1
        set(value) {
            test.patientMDSQ1 = value
        }

    var patientMDSQ2: Int?
        get() = test.patientMDSQ2
        set(value) {
            test.patientMDSQ2 = value
        }

    var patientMDSQ3: Int?
        get() = test.patientMDSQ3
        set(value) {
            test.patientMDSQ3 = value
        }

    var patientMDSQ4: Int?
        get() = test.patientMDSQ4
        set(value) {
            test.patientMDSQ4 = value
        }

    var patientMDSQ5: Int?
        get() = test.patientMDSQ5
        set(value) {
            test.patientMDSQ5 = value
        }

    var patientMDSQ6: Int?
        get() = test.patientMDSQ6
        set(value) {
            test.patientMDSQ6 = value
        }

    var patientMDSQ7: Int?
        get() = test.patientMDSQ7
        set(value) {
            test.patientMDSQ7 = value
        }

    var patientMDSQ8: Int?
        get() = test.patientMDSQ8
        set(value) {
            test.patientMDSQ8 = value
        }

    var patientMDSQ9: Int?
        get() = test.patientMDSQ9
        set(value) {
            test.patientMDSQ9 = value
        }

    var patientMDSQ10: Int?
        get() = test.patientMDSQ10
        set(value) {
            test.patientMDSQ10 = value
        }

    var patientMDSQ11: Int?
        get() = test.patientMDSQ11
        set(value) {
            test.patientMDSQ11 = value
        }

    var patientMDSTestResult: Int?
        get() = test.patientMDSTestResult
        set(value) {
            test.patientMDSTestResult = value
        }

}
