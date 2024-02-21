package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class HAMTest : RealmModel {
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
    open var testName : String? = "HAMTest"

//    var testId: String
//        get() = test.testId
//        set(value) {
//            test.testId = value
//        }
//
//    var patientId: String
//        get() = test.patientId
//        set(value) {
//            test.patientId = value
//        }
//
//    var testDate: Date?
//        get() = test.testDate
//        set(value) {
//            test.testDate = value
//        }
//
//    var testName: String
//        get() = test.testName
//        set(value) {
//            test.testName = "HAMTest"
//        }


    //variables related with hamilton Depression

    @Required
    open var patientHAMDQ1: Int? = null

//    var patientHAMDQ1: Int?
//        get() = test.patientHAMDQ1
//        set(value) {
//            test.patientHAMDQ1 = value
//        }

    @Required
    open var patientHAMDQ2: Int? = null

//    var patientHAMDQ2: Int?
//        get() = test.patientHAMDQ2
//        set(value) {
//            test.patientHAMDQ2 = value
//        }

    @Required
    open var patientHAMDQ3: Int? = null

//    var patientHAMDQ3: Int?
//        get() = test.patientHAMDQ3
//        set(value) {
//            test.patientHAMDQ3 = value
//        }

    @Required
    open var patientHAMDQ4: Int? = null

//    var patientHAMDQ4: Int?
//        get() = test.patientHAMDQ4
//        set(value) {
//            test.patientHAMDQ4 = value
//        }

    @Required
    open var patientHAMDQ5: Int? = null

//    var patientHAMDQ5: Int?
//        get() = test.patientHAMDQ5
//        set(value) {
//            test.patientHAMDQ5 = value
//        }

    @Required
    open var patientHAMDQ6: Int? = null

//    var patientHAMDQ6: Int?
//        get() = test.patientHAMDQ6
//        set(value) {
//            test.patientHAMDQ6 = value
//        }

    @Required
    open var patientHAMDQ7: Int? = null
//    var patientHAMDQ7: Int?
//        get() = test.patientHAMDQ7
//        set(value) {
//            test.patientHAMDQ7 = value
//        }

    @Required
    open var patientHAMDQ8: Int? = null
//    var patientHAMDQ8: Int?
//        get() = test.patientHAMDQ8
//        set(value) {
//            test.patientHAMDQ8 = value
//        }

    @Required
    open var patientHAMDQ9: Int? = null

//    var patientHAMDQ9: Int?
//        get() = test.patientHAMDQ9
//        set(value) {
//            test.patientHAMDQ9= value
//        }


    @Required
    open var patientHAMDQ10: Int? = null
//    var patientHAMDQ10: Int?
//        get() = test.patientHAMDQ10
//        set(value) {
//            test.patientHAMDQ10 = value
//        }

    @Required
    open var patientHAMDQ11: Int? = null

//    var patientHAMDQ11: Int?
//        get() = test.patientHAMDQ11
//        set(value) {
//            test.patientHAMDQ11 = value
//        }

    @Required
    open var patientHAMDQ12: Int? = null

//    var patientHAMDQ12: Int?
//        get() = test.patientHAMDQ12
//        set(value) {
//            test.patientHAMDQ12 = value
//        }

    @Required
    open var patientHAMDQ13: Int? = null
//    var patientHAMDQ13: Int?
//        get() = test.patientHAMDQ13
//        set(value) {
//            test.patientHAMDQ13 = value
//        }

    @Required
    open var patientHAMDQ14: Int? = null

//    var patientHAMDQ14: Int?
//        get() = test.patientHAMDQ14
//        set(value) {
//            test.patientHAMDQ14 = value
//        }

    @Required
    open var patientHAMDQ15: Int? = null

//    var patientHAMDQ15: Int?
//        get() = test.patientHAMDQ15
//        set(value) {
//            test.patientHAMDQ15 = value
//        }

    @Required
    open var patientHAMDQ16: Int? = null

//    var patientHAMDQ16: Int?
//        get() = test.patientHAMDQ16
//        set(value) {
//            test.patientHAMDQ16 = value
//        }


    @Required
    open var patientHAMDQ17: Int? = null

//    var patientHAMDQ17: Int?
//        get() = test.patientHAMDQ17
//        set(value) {
//            test.patientHAMDQ17 = value
//        }

    @Required
    open var patientHAMDTestResult: Int? = null
//    var patientHAMDTestResult: Int?
//        get() = test.patientHAMDTestResult
//        set(value) {
//            test.patientHAMDTestResult = value
//        }

}