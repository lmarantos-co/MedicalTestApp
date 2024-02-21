package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BAITest : RealmModel  {

    private var test = Test()

//    var testId: String
//        get() = test.testId
//        set(value) {
//            test.testId = value
//        }

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
    open var testName : String? = "BAITest"

//    var testName: String?
//        get() = test.testName
//        set(value) {
//            test.testName = "BAITest"
//        }



    //variables related with Beck Anxiety Inventory Test

    @Required
    open var patientBAIQ1: Int? = null

//    var patientBAIQ1: Int?
//        get() = test.patientBAIQ1
//        set(value) {
//            test.patientBAIQ1 = value
//        }

    @Required
    open var patientBAIQ2: Int? = null

//    var patientBAIQ2: Int?
//        get() = test.patientBAIQ2
//        set(value) {
//            test.patientBAIQ2 = value
//        }

    @Required
    open var patientBAIQ3: Int? = null

//    var patientBAIQ3: Int?
//        get() = test.patientBAIQ3
//        set(value) {
//            test.patientBAIQ3 = value
//        }

    open var patientBAIQ4: Int? = null

//    var patientBAIQ4: Int?
//        get() = test.patientBAIQ4
//        set(value) {
//            test.patientBAIQ4 = value
//        }

    open var patientBAIQ5: Int? = null

//    var patientBAIQ5: Int?
//        get() = test.patientBAIQ5
//        set(value) {
//            test.patientBAIQ5 = value
//        }

    open var patientBAIQ6: Int? = null

//    var patientBAIQ6: Int?
//        get() = test.patientBAIQ6
//        set(value) {
//            test.patientBAIQ6 = value
//        }

    open var patientBAIQ7: Int? = null

//    var patientBAIQ7: Int?
//        get() = test.patientBAIQ7
//        set(value) {
//            test.patientBAIQ7 = value
//        }

    open var patientBAIQ8: Int? = null

//    var patientBAIQ8: Int?
//        get() = test.patientBAIQ8
//        set(value) {
//            test.patientBAIQ8 = value
//        }

    open var patientBAIQ9: Int? = null

//    var patientBAIQ9: Int?
//        get() = test.patientBAIQ9
//        set(value) {
//            test.patientBAIQ9 = value
//        }

    open var patientBAIQ10: Int? = null

//    var patientBAIQ10: Int?
//        get() = test.patientBAIQ10
//        set(value) {
//            test.patientBAIQ10 = value
//        }

    open var patientBAIQ11: Int? = null

//    var patientBAIQ11: Int?
//        get() = test.patientBAIQ11
//        set(value) {
//            test.patientBAIQ11 = value
//        }

    open var patientBAIQ12: Int? = null

//    var patientBAIQ12: Int?
//        get() = test.patientBAIQ12
//        set(value) {
//            test.patientBAIQ12 = value
//        }

    open var patientBAIQ13: Int? = null

//    var patientBAIQ13: Int?
//        get() = test.patientBAIQ13
//        set(value) {
//            test.patientBAIQ13 = value
//        }

    open var patientBAIQ14: Int? = null

//    var patientBAIQ14: Int?
//        get() = test.patientBAIQ14
//        set(value) {
//            test.patientBAIQ14 = value
//        }

    open var patientBAIQ15: Int? = null

//    var patientBAIQ15: Int?
//        get() = test.patientBAIQ15
//        set(value) {
//            test.patientBAIQ15 = value
//        }

    open var patientBAIQ16: Int? = null

//    var patientBAIQ16: Int?
//        get() = test.patientBAIQ16
//        set(value) {
//            test.patientBAIQ16 = value
//        }

    open var patientBAIQ17: Int? = null
//    var patientBAIQ17: Int?
//        get() = test.patientBAIQ17
//        set(value) {
//            test.patientBAIQ17 = value
//        }

    open var patientBAIQ18: Int? = null

//    var patientBAIQ18: Int?
//        get() = test.patientBAIQ18
//        set(value) {
//            test.patientBAIQ18 = value
//        }

    open var patientBAIQ19: Int? = null

//    var patientBAIQ19: Int?
//        get() = test.patientBAIQ19
//        set(value) {
//            test.patientBAIQ19 = value
//        }

    open var patientBAIQ20: Int? = null

//    var patientBAIQ20: Int?
//        get() = test.patientBAIQ20
//        set(value) {
//            test.patientBAIQ20 = value
//        }

    open var patientBAIQ21: Int? = null

//    var patientBAIQ21: Int?
//        get() = test.patientBAIQ21
//        set(value) {
//            test.patientBAIQ21 = value
//        }

    open var patientBAITestResult: Int? = null

//    var patientBAITestResult: Int?
//        get() = test.patientBAITestResult
//        set(value) {
//            test.patientBAITestResult = value
//        }

}

