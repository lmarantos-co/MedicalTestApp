package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class GDSTest : RealmModel {

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
            test.testName = "GDSTest"
        }

    //Stai Test
    var patientGDSQ1: Int?
        get() = test.patientGDSQ1
        set(value) {
            test.patientGDSQ1 = value
        }

    var patientGDSQ2: Int?
        get() = test.patientGDSQ2
        set(value) {
            test.patientGDSQ2 = value
        }

    var patientGDSQ3: Int?
        get() = test.patientGDSQ3
        set(value) {
            test.patientGDSQ3 = value
        }

    var patientGDSQ4: Int?
        get() = test.patientGDSQ4
        set(value) {
            test.patientGDSQ4 = value
        }

    var patientGDSQ5: Int?
        get() = test.patientGDSQ5
        set(value) {
            test.patientGDSQ5 = value
        }

    var patientGDSQ6: Int?
        get() = test.patientGDSQ6
        set(value) {
            test.patientGDSQ6 = value
        }

    var patientGDSQ7: Int?
        get() = test.patientGDSQ7
        set(value) {
            test.patientGDSQ7 = value
        }

    var patientGDSQ8: Int?
        get() = test.patientGDSQ8
        set(value) {
            test.patientGDSQ8 = value
        }

    var patientGDSQ9: Int?
        get() = test.patientGDSQ9
        set(value) {
            test.patientGDSQ9 = value
        }


    var patientGDSQ10: Int?
        get() = test.patientGDSQ10
        set(value) {
            test.patientGDSQ10 = value
        }

    var patientGDSQ11: Int?
        get() = test.patientGDSQ11
        set(value) {
            test.patientGDSQ11 = value
        }

    var patientGDSQ12: Int?
        get() = test.patientGDSQ12
        set(value) {
            test.patientGDSQ12 = value
        }

    var patientGDSQ13: Int?
        get() = test.patientGDSQ13
        set(value) {
            test.patientGDSQ13 = value
        }

    var patientGDSQ14: Int?
        get() = test.patientGDSQ14
        set(value) {
            test.patientGDSQ14 = value
        }

    var patientGDSQ15: Int?
        get() = test.patientGDSQ15
        set(value) {
            test.patientGDSQ15 = value
        }

    var patientGDSTestResult: Int?
        get() = test.patientGDSTestResult
        set(value) {
            test.patientGDSTestResult = value
        }

    //variables related with GDS Test


}