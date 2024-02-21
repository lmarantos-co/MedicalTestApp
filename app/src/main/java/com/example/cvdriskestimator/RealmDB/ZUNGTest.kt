package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class ZUNGTest : RealmModel {

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
    open var testName : String? = "ZUNGTest"

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
//            test.testName = "ZUNGTest"
//        }

    //ZUNG Test

    @Required
    open var patientZUNGQ1: Int? = null
//    var patientZUNGQ1: Int?
//        get() = test.patientZUNGQ1
//        set(value) {
//            test.patientZUNGQ1 = value
//        }

    @Required
    open var patientZUNGQ2: Int? = null
//    var patientZUNGQ2: Int?
//        get() = test.patientZUNGQ2
//        set(value) {
//            test.patientZUNGQ2 = value
//        }

    @Required
    open var patientZUNGQ3: Int? = null
//    var patientZUNGQ3: Int?
//        get() = test.patientZUNGQ3
//        set(value) {
//            test.patientZUNGQ3 = value
//        }

    @Required
    open var patientZUNGQ4: Int? = null
//    var patientZUNGQ4: Int?
//        get() = test.patientZUNGQ4
//        set(value) {
//            test.patientZUNGQ4 = value
//        }

    @Required
    open var patientZUNGQ5: Int? = null
//    var patientZUNGQ5: Int?
//        get() = test.patientZUNGQ5
//        set(value) {
//            test.patientZUNGQ5 = value
//        }

    @Required
    open var patientZUNGQ6: Int? = null
//    var patientZUNGQ6: Int?
//        get() = test.patientZUNGQ6
//        set(value) {
//            test.patientZUNGQ6 = value
//        }

    @Required
    open var patientZUNGQ7: Int? = null
//    var patientZUNGQ7: Int?
//        get() = test.patientZUNGQ7
//        set(value) {
//            test.patientZUNGQ7 = value
//        }

    @Required
    open var patientZUNGQ8: Int? = null
//    var patientZUNGQ8: Int?
//        get() = test.patientZUNGQ8
//        set(value) {
//            test.patientZUNGQ8 = value
//        }

    @Required
    open var patientZUNGQ9: Int? = null

//    var patientZUNGQ9: Int?
//        get() = test.patientZUNGQ9
//        set(value) {
//            test.patientZUNGQ9 = value
//        }


    @Required
    open var patientZUNGQ10: Int? = null
//    var patientZUNGQ10: Int?
//        get() = test.patientZUNGQ10
//        set(value) {
//            test.patientZUNGQ10 = value
//        }

    @Required
    open var patientZUNGQ11: Int? = null
//    var patientZUNGQ11: Int?
//        get() = test.patientZUNGQ11
//        set(value) {
//            test.patientZUNGQ11 = value
//        }

    @Required
    open var patientZUNGQ12: Int? = null
//    var patientZUNGQ12: Int?
//        get() = test.patientZUNGQ12
//        set(value) {
//            test.patientZUNGQ12 = value
//        }

    @Required
    open var patientZUNGQ13: Int? = null
//    var patientZUNGQ13: Int?
//        get() = test.patientZUNGQ13
//        set(value) {
//            test.patientZUNGQ13 = value
//        }

    @Required
    open var patientZUNGQ14: Int? = null
//    var patientZUNGQ14: Int?
//        get() = test.patientZUNGQ14
//        set(value) {
//            test.patientZUNGQ14 = value
//        }

    @Required
    open var patientZUNGQ15: Int? = null
//    var patientZUNGQ15: Int?
//        get() = test.patientZUNGQ15
//        set(value) {
//            test.patientZUNGQ15 = value
//        }

    @Required
    open var patientZUNGQ16: Int? = null
//    var patientZUNGQ16: Int?
//        get() = test.patientZUNGQ16
//        set(value) {
//            test.patientZUNGQ16 = value
//        }

    @Required
    open var patientZUNGQ17: Int? = null
//    var patientZUNGQ17: Int?
//        get() = test.patientZUNGQ17
//        set(value) {
//            test.patientZUNGQ17 = value
//        }

    @Required
    open var patientZUNGQ18: Int? = null
//    var patientZUNGQ18: Int?
//        get() = test.patientZUNGQ18
//        set(value) {
//            test.patientZUNGQ18 = value
//        }

    @Required
    open var patientZUNGQ19: Int? = null
//    var patientZUNGQ19: Int?
//        get() = test.patientZUNGQ19
//        set(value) {
//            test.patientZUNGQ19 = value
//        }

    @Required
    open var patientZUNGQ20: Int? = null
//    var patientZUNGQ20: Int?
//        get() = test.patientZUNGQ20
//        set(value) {
//            test.patientZUNGQ20 = value
//        }

    @Required
    open var zungTestReesult: Int? = null
//    var zungTestReesult: Int?
//        get() = test.zungTestReesult
//        set(value) {
//            test.zungTestReesult = value
//        }

}