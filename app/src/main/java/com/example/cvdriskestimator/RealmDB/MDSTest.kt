package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class MDSTest : RealmModel {

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
    open var testName : String? = "MDSTest"

//    var testName: String
//        get() = test.testName
//        set(value) {
//            test.testName = "MDSTest"
//        }


    //variables related wth the Med Diet Score Test

    @Required
    open var patientMDSQ1: Int? = null

//    var patientMDSQ1: Int?
//        get() = test.patientMDSQ1
//        set(value) {
//            test.patientMDSQ1 = value
//        }


    @Required
    open var patientMDSQ2: Int? = null

//    var patientMDSQ2: Int?
//        get() = test.patientMDSQ2
//        set(value) {
//            test.patientMDSQ2 = value
//        }

    @Required
    open var patientMDSQ3: Int? = null

//    var patientMDSQ3: Int?
//        get() = test.patientMDSQ3
//        set(value) {
//            test.patientMDSQ3 = value
//        }


    @Required
    open var patientMDSQ4: Int? = null

//    var patientMDSQ4: Int?
//        get() = test.patientMDSQ4
//        set(value) {
//            test.patientMDSQ4 = value
//        }

    @Required
    open var patientMDSQ5: Int? = null

//    var patientMDSQ5: Int?
//        get() = test.patientMDSQ5
//        set(value) {
//            test.patientMDSQ5 = value
//        }

    @Required
    open var patientMDSQ6: Int? = null

//    var patientMDSQ6: Int?
//        get() = test.patientMDSQ6
//        set(value) {
//            test.patientMDSQ6 = value
//        }


    @Required
    open var patientMDSQ7: Int? = null

//    var patientMDSQ7: Int?
//        get() = test.patientMDSQ7
//        set(value) {
//            test.patientMDSQ7 = value
//        }


    @Required
    open var patientMDSQ8: Int? = null

//    var patientMDSQ8: Int?
//        get() = test.patientMDSQ8
//        set(value) {
//            test.patientMDSQ8 = value
//        }

    @Required
    open var patientMDSQ9: Int? = null

//    var patientMDSQ9: Int?
//        get() = test.patientMDSQ9
//        set(value) {
//            test.patientMDSQ9 = value
//        }


    @Required
    open var patientMDSQ10: Int? = null

//    var patientMDSQ10: Int?
//        get() = test.patientMDSQ10
//        set(value) {
//            test.patientMDSQ10 = value
//        }

    @Required
    open var patientMDSQ11: Int? = null

//    var patientMDSQ11: Int?
//        get() = test.patientMDSQ11
//        set(value) {
//            test.patientMDSQ11 = value
//        }


    @Required
    open var patientMDSTestResult: Int? = null

//    var patientMDSTestResult: Int?
//        get() = test.patientMDSTestResult
//        set(value) {
//            test.patientMDSTestResult = value
//        }

}
