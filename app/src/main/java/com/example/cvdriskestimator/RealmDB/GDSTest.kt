package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class GDSTest : RealmModel {

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
    open var testName : String? = "GDSTest"

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
//            test.testName = "GDSTest"
//        }

    //Stai Test

    @Required
    open var patientGDSQ1 : Int? = null

//    var patientGDSQ1: Int?
//        get() = test.patientGDSQ1
//        set(value) {
//            test.patientGDSQ1 = value
//        }

    @Required
    open var patientGDSQ2 : Int? = null

//    var patientGDSQ2: Int?
//        get() = test.patientGDSQ2
//        set(value) {
//            test.patientGDSQ2 = value
//        }

    @Required
    open var patientGDSQ3 : Int? = null

//    var patientGDSQ3: Int?
//        get() = test.patientGDSQ3
//        set(value) {
//            test.patientGDSQ3 = value
//        }

    @Required
    open var patientGDSQ4 : Int? = null

//    var patientGDSQ4: Int?
//        get() = test.patientGDSQ4
//        set(value) {
//            test.patientGDSQ4 = value
//        }

    @Required
    open var patientGDSQ5 : Int? = null

//    var patientGDSQ5: Int?
//        get() = test.patientGDSQ5
//        set(value) {
//            test.patientGDSQ5 = value
//        }


    @Required
    open var patientGDSQ6 : Int? = null

//    var patientGDSQ6: Int?
//        get() = test.patientGDSQ6
//        set(value) {
//            test.patientGDSQ6 = value
//        }


    @Required
    open var patientGDSQ7 : Int? = null

//    var patientGDSQ7: Int?
//        get() = test.patientGDSQ7
//        set(value) {
//            test.patientGDSQ7 = value
//        }

    @Required
    open var patientGDSQ8 : Int? = null

//    var patientGDSQ8: Int?
//        get() = test.patientGDSQ8
//        set(value) {
//            test.patientGDSQ8 = value
//        }


    @Required
    open var patientGDSQ9 : Int? = null

//    var patientGDSQ9: Int?
//        get() = test.patientGDSQ9
//        set(value) {
//            test.patientGDSQ9 = value
//        }


    @Required
    open var patientGDSQ10 : Int? = null

//    var patientGDSQ10: Int?
//        get() = test.patientGDSQ10
//        set(value) {
//            test.patientGDSQ10 = value
//        }


    @Required
    open var patientGDSQ11 : Int? = null

//    var patientGDSQ11: Int?
//        get() = test.patientGDSQ11
//        set(value) {
//            test.patientGDSQ11 = value
//        }

    @Required
    open var patientGDSQ12 : Int? = null

//    var patientGDSQ12: Int?
//        get() = test.patientGDSQ12
//        set(value) {
//            test.patientGDSQ12 = value
//        }


    @Required
    open var patientGDSQ13 : Int? = null

//    var patientGDSQ13: Int?
//        get() = test.patientGDSQ13
//        set(value) {
//            test.patientGDSQ13 = value
//        }


    @Required
    open var patientGDSQ14 : Int? = null

//    var patientGDSQ14: Int?
//        get() = test.patientGDSQ14
//        set(value) {
//            test.patientGDSQ14 = value
//        }

    @Required
    open var patientGDSQ15 : Int? = null

//    var patientGDSQ15: Int?
//        get() = test.patientGDSQ15
//        set(value) {
//            test.patientGDSQ15 = value
//        }


    @Required
    open var patientGDSTestResult : Int? = null

//    var patientGDSTestResult: Int?
//        get() = test.patientGDSTestResult
//        set(value) {
//            test.patientGDSTestResult = value
//        }

    //variables related with GDS Test


}