package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class DASSTest : RealmModel {

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
            test.testName = "DASSTest"
        }

    var patientDASSQ1: Int?
        get() = test.patientDASSQ1
        set(value) {
            test.patientDASSQ1 = value
        }

    var patientDASSQ2: Int?
        get() = test.patientDASSQ2
        set(value) {
            test.patientDASSQ2 = value
        }

    var patientDASSQ3: Int?
        get() = test.patientDASSQ3
        set(value) {
            test.patientDASSQ3 = value
        }

    var patientDASSQ4: Int?
        get() = test.patientDASSQ4
        set(value) {
            test.patientDASSQ4 = value
        }

    var patientDASSQ5: Int?
        get() = test.patientDASSQ5
        set(value) {
            test.patientDASSQ5 = value
        }

    var patientDASSQ6: Int?
        get() = test.patientDASSQ6
        set(value) {
            test.patientDASSQ6 = value
        }

    var patientDASSQ7: Int?
        get() = test.patientDASSQ7
        set(value) {
            test.patientDASSQ7 = value
        }

    var patientDASSQ8: Int?
        get() = test.patientDASSQ8
        set(value) {
            test.patientDASSQ8 = value
        }

    var patientDASSQ9: Int?
        get() = test.patientDASSQ9
        set(value) {
            test.patientDASSQ9 = value
        }

    var patientDASSQ10: Int?
        get() = test.patientDASSQ10
        set(value) {
            test.patientDASSQ10 = value
        }

    var patientDASSQ11: Int?
        get() = test.patientDASSQ11
        set(value) {
            test.patientDASSQ11 = value
        }

    var patientDASSQ12: Int?
        get() = test.patientDASSQ12
        set(value) {
            test.patientDASSQ12 = value
        }

    var patientDASSQ13: Int?
        get() = test.patientDASSQ13
        set(value) {
            test.patientDASSQ13 = value
        }

    var patientDASSQ14: Int?
        get() = test.patientDASSQ14
        set(value) {
            test.patientDASSQ14 = value
        }

    var patientDASSQ15: Int?
        get() = test.patientDASSQ15
        set(value) {
            test.patientDASSQ15 = value
        }

    var patientDASSQ16: Int?
        get() = test.patientDASSQ16
        set(value) {
            test.patientDASSQ16 = value
        }

    var patientDASSQ17: Int?
        get() = test.patientDASSQ17
        set(value) {
            test.patientDASSQ17 = value
        }

    var patientDASSQ18: Int?
        get() = test.patientDASSQ18
        set(value) {
            test.patientDASSQ18 = value
        }

    var patientDASSQ19: Int?
        get() = test.patientDASSQ19
        set(value) {
            test.patientDASSQ19 = value
        }

    var patientDASSQ20: Int?
        get() = test.patientDASSQ20
        set(value) {
            test.patientDASSQ20 = value
        }

    var patientDASSQ21: Int?
        get() = test.patientDASSQ21
        set(value) {
            test.patientDASSQ21 = value
        }
    //DASS Test
    var dassStressResult: Int?
        get() = test.dassStressResult
        set(value) {
            test.dassStressResult = value
        }

    var dassAnxietyResult: Int?
        get() = test.dassAnxietyResult
        set(value) {
            test.dassAnxietyResult = value
        }

    var dassDepressionResult: Int?
        get() = test.dassDepressionResult
        set(value) {
            test.dassDepressionResult = value
        }

}