package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class CVDTest : RealmModel
{

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
            test.testName = "CVDTest"
        }

    var patientAge: String
        get() = test.patientAge
        set(value) {
            test.patientAge = value
        }

    //CVD TEST

    var patientSex: String
        get() = test.patientSex
        set(value) {
            test.patientSex = value
        }

    var patientRace: String
        get() = test.patientRace
        set(value) {
            test.patientRace = value
        }

    var SSB: String
        get() = test.SSB
        set(value) {
            test.SSB = value
        }

    var TCH: String
        get() = test.TCH
        set(value) {
            test.TCH = value
        }

    var HDL: String
        get() = test.HDL
        set(value) {
            test.HDL = value
        }

    var smoker: String
        get() = test.smoker
        set(value) {
            test.smoker = value
        }

    var cvdTestResult: Int?
        get() = test.cvdTestResult
        set(value) {
            test.cvdTestResult = value
        }

    var treatment: String?
        get() = test.treatment
        set(value) {
            test.treatment = value!!
        }


}