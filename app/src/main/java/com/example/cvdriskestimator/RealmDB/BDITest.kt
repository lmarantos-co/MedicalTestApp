package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BDITest : RealmModel {

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
    open var testName : String? = "BDITest"


    //variables related with Beck Depression Inventory Test

    @Required
    open var patientBDIQ1: Int? = null

//    var patientBDIQ1: Int?
//        get() = test.patientBDIQ1
//        set(value) {
//            test.patientBDIQ1 = value
//        }

    @Required
    open var patientBDIQ2: Int? = null

//    var patientBDIQ2: Int?
//        get() = test.patientBDIQ2
//        set(value) {
//            test.patientBDIQ2 = value
//        }

    @Required
    open var patientBDIQ3: Int? = null

//    var patientBDIQ3: Int?
//        get() = test.patientBDIQ3
//        set(value) {
//            test.patientBDIQ3 = value
//        }

    @Required
    open var patientBDIQ4: Int? = null

//    var patientBDIQ4: Int?
//        get() = test.patientBDIQ4
//        set(value) {
//            test.patientBDIQ4 = value
//        }

    @Required
    open var patientBDIQ5: Int? = null

//    var patientBDIQ5: Int?
//        get() = test.patientBDIQ5
//        set(value) {
//            test.patientBDIQ5 = value
//        }

    @Required
    open var patientBDIQ6: Int? = null

//    var patientBDIQ6: Int?
//        get() = test.patientBDIQ6
//        set(value) {
//            test.patientBDIQ6 = value
//        }

    @Required
    open var patientBDIQ7: Int? = null
//    var patientBDIQ7: Int?
//        get() = test.patientBDIQ7
//        set(value) {
//            test.patientBDIQ7 = value
//        }

    @Required
    open var patientBDIQ8: Int? = null

//    var patientBDIQ8: Int?
//        get() = test.patientBDIQ8
//        set(value) {
//            test.patientBDIQ8 = value
//        }

    @Required
    open var patientBDIQ9: Int? = null
//    var patientBDIQ9: Int?
//        get() = test.patientBDIQ9
//        set(value) {
//            test.patientBDIQ9 = value
//        }

    @Required
    open var patientBDIQ10: Int? = null
//    var patientBDIQ10: Int?
//        get() = test.patientBDIQ10
//        set(value) {
//            test.patientBDIQ10 = value
//        }

    @Required
    open var patientBDIQ11: Int? = null
//    var patientBDIQ11: Int?
//        get() = test.patientBDIQ11
//        set(value) {
//            test.patientBDIQ11 = value
//        }

    @Required
    open var patientBDIQ12: Int? = null
//    var patientBDIQ12: Int?
//        get() = test.patientBDIQ12
//        set(value) {
//            test.patientBDIQ12 = value
//        }

    @Required
    open var patientBDIQ13: Int? = null
//    var patientBDIQ13: Int?
//        get() = test.patientBDIQ13
//        set(value) {
//            test.patientBDIQ13 = value
//        }

    @Required
    open var patientBDIQ14: Int? = null
//    var patientBDIQ14: Int?
//        get() = test.patientBDIQ14
//        set(value) {
//            test.patientBDIQ14 = value
//        }

    @Required
    open var patientBDIQ15: Int? = null
//    var patientBDIQ15: Int?
//        get() = test.patientBDIQ15
//        set(value) {
//            test.patientBDIQ15 = value
//        }

    @Required
    open var patientBDIQ16: Int? = null
//    var patientBDIQ16: Int?
//        get() = test.patientBDIQ16
//        set(value) {
//            test.patientBDIQ16 = value
//        }

    @Required
    open var patientBDIQ17: Int? = null

//    var patientBDIQ17: Int?
//        get() = test.patientBDIQ17
//        set(value) {
//            test.patientBDIQ17 = value
//        }

    @Required
    open var patientBDIQ18: Int? = null

//    var patientBDIQ18: Int?
//        get() = test.patientBDIQ18
//        set(value) {
//            test.patientBDIQ18 = value
//        }

    @Required
    open var patientBDIQ19: Int? = null

//    var patientBDIQ19: Int?
//        get() = test.patientBDIQ19
//        set(value) {
//            test.patientBDIQ19 = value
//        }

    @Required
    open var patientBDIQ20: Int? = null

//    var patientBDIQ20: Int?
//        get() = test.patientBDIQ20
//        set(value) {
//            test.patientBDIQ20 = value
//        }

    @Required
    open var patientBDIQ21: Int? = null

//    var patientBDIQ21: Int?
//        get() = test.patientBDIQ21
//        set(value) {
//            test.patientBDIQ21 = value
//        }

    @Required
    open var patientBDITestResult: Int? = null

//    var patientBDITestResult: Int?
//        get() = test.patientBDITestResult
//        set(value) {
//            test.patientBDITestResult = value
//        }


}