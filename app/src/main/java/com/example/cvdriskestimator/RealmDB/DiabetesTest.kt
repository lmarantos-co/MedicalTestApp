package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class DiabetesTest : RealmModel {

    private var test = Test()

    @PrimaryKey
    open var testId: String = ""

    @Required
    open var patientId : String = ""

//    var patientId: String
//        get() = test.patientId
//        set(value) {
//            test.patientId = value
//        }

    @Required
    open var testDate : Date? = null

//    var testDate: Date?
//        get() = test.testDate
//        set(value) {
//            test.testDate = value
//        }

    @Required
    open var testName : String? = "DiabetesTest"

//    var testName: String
//        get() = test.testName
//        set(value) {
//            test.testName = "DiabetesTest"
//        }


    //DIABETES CHECK

    @Required
    open var patientSex: String? = null
//    var patientSex: String?
//        get() = test.patientSex
//        set(value) {
//            test.patientSex = value!!
//        }

    @Required
    open var patientAge: String? = null

//    var patientAge: String?
//        get() = test.patientAge
//        set(value) {
//            test.patientAge = value!!
//        }

    @Required
    open var treatment: String? = null

//    var treatment: String?
//        get() = test.treatment
//        set(value) {
//            test.treatment = value!!
//        }

    @Required
    open var smoker: String? = null

//    var smoker: String?
//        get() = test.smoker
//        set(value) {
//            test.smoker = value!!
//        }

    @Required
    open var patientPAM: String? = null

//    var patientPAM: String?
//        get() = test.patientPAM
//        set(value) {
//            test.patientPAM = value!!
//        }

    @Required
    open var patientSteroids: String? = null

//    var patientSteroids: String?
//        get() = test.patientSteroids
//        set(value) {
//            test.patientSteroids = value!!
//        }

    @Required
    open var patientBMI: String? = null

//    var patientBMI: String?
//        get() = test.patientBMI
//        set(value) {
//            test.patientBMI = value!!
//        }

    @Required
    open var patientSiblings: String? = null

//    var patientSiblings: String?
//        get() = test.patientSiblings
//        set(value) {
//            test.patientSiblings = value!!
//        }

    @Required
    open var diabetesTestResult: Int? = null

//    var diabetesTestResult: Double?
//        get() = test.diabetesTestResult
//        set(value) {
//            test.diabetesTestResult = value!!
//        }


}