package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class MDITest : RealmModel {

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
    open var testName : String? = "MDIest"

    //variables related with Major Depression Inventory Test

    @Required
    open var patientMDIQ1 : Int? = null

//    var patientMDIQ1: Int?
//        get() = test.patientMDIQ1
//        set(value) {
//            test.patientMDIQ1 = value
//        }

    @Required
    open var patientMDIQ2 : Int? = null

//    var patientMDIQ2: Int?
//        get() = test.patientMDIQ2
//        set(value) {
//            test.patientMDIQ2 = value
//        }

    @Required
    open var patientMDIQ3 : Int? = null


//    var patientMDIQ3: Int?
//        get() = test.patientMDIQ3
//        set(value) {
//            test.patientMDIQ3 = value
//        }

    @Required
    open var patientMDIQ4 : Int? = null

//    var patientMDIQ4: Int?
//        get() = test.patientMDIQ4
//        set(value) {
//            test.patientMDIQ4 = value
//        }

    @Required
    open var patientMDIQ5 : Int? = null

//    var patientMDIQ5: Int?
//        get() = test.patientMDIQ5
//        set(value) {
//            test.patientMDIQ5 = value
//        }


    @Required
    open var patientMDIQ6 : Int? = null

//    var patientMDIQ6: Int?
//        get() = test.patientMDIQ6
//        set(value) {
//            test.patientMDIQ6 = value
//        }

    @Required
    open var patientMDIQ7 : Int? = null

//    var patientMDIQ7: Int?
//        get() = test.patientMDIQ7
//        set(value) {
//            test.patientMDIQ7 = value
//        }

    @Required
    open var patientMDIQ8 : Int? = null

//    var patientMDIQ8: Int?
//        get() = test.patientMDIQ8
//        set(value) {
//            test.patientMDIQ8 = value
//        }

    @Required
    open var patientMDIQ9 : Int? = null

//    var patientMDIQ9: Int?
//        get() = test.patientMDIQ9
//        set(value) {
//            test.patientMDIQ9 = value
//        }

    @Required
    open var patientMDIQ10 : Int? = null

//    var patientMDIQ10: Int?
//        get() = test.patientMDIQ10
//        set(value) {
//            test.patientMDIQ10 = value
//        }


    @Required
    open var patientMDIQ11 : Int? = null

//    var patientMDIQ11: Int?
//        get() = test.patientMDIQ11
//        set(value) {
//            test.patientMDIQ11 = value
//        }


    @Required
    open var patientMDIQ12 : Int? = null

//    var patientMDIQ12: Int?
//        get() = test.patientMDIQ12
//        set(value) {
//            test.patientMDIQ12 = value
//        }

    @Required
    open var patientMDIQ13 : Int? = null

//    var patientMDIQ13: Int?
//        get() = test.patientMDIQ13
//        set(value) {
//            test.patientMDIQ13 = value
//        }

    @Required
    open var patientMDITestResult : Int? = null

//    var patientMDITestResult: Int?
//        get() = test.patientMDITestResult
//        set(value) {
//            test.patientMDITestResult = value
//        }
}