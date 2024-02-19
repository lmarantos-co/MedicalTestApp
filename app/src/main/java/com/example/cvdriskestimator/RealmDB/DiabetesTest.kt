package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class DiabetesTest : RealmModel {

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
            test.testName = "DiabetesTest"
        }


    //DIABETES CHECK

    var patientSex: String?
        get() = test.patientSex
        set(value) {
            test.patientSex = value!!
        }

    var patientAge: String?
        get() = test.patientAge
        set(value) {
            test.patientAge = value!!
        }

    var treatment: String?
        get() = test.treatment
        set(value) {
            test.treatment = value!!
        }

    var smoker: String?
        get() = test.smoker
        set(value) {
            test.smoker = value!!
        }

    var patientPAM: String?
        get() = test.patientPAM
        set(value) {
            test.patientPAM = value!!
        }

    var patientSteroids: String?
        get() = test.patientSteroids
        set(value) {
            test.patientSteroids = value!!
        }

    var patientBMI: String?
        get() = test.patientBMI
        set(value) {
            test.patientBMI = value!!
        }

    var patientSiblings: String?
        get() = test.patientSiblings
        set(value) {
            test.patientSiblings = value!!
        }

    var diabetesTestResult: Double?
        get() = test.diabetesTestResult
        set(value) {
            test.diabetesTestResult = value!!
        }


}