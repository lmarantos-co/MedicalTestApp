package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class BPITest : RealmModel {

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
    open var testName : String? = "BPITest"


    @Required
    open var patientBPIQ1: Int? = null

//    var patientBPIQ1: Int?
//        get() = test.patientBPIQ1
//        set(value) {
//            test.patientBPIQ1 = value
//        }
    //variables related with the Brief Pain Inventory Index

    @Required
    open var patientBPIQ2: Int? = null

//    var patientBPIQ2: Int?
//        get() = test.patientBPIQ2
//        set(value) {
//            test.patientBPIQ2 = value
//        }


    @Required
    open var patientBPIQ3: Int? = null

//    var patientBPIQ3: Int?
//        get() = test.patientBPIQ3
//        set(value) {
//            test.patientBPIQ3 = value
//        }


    @Required
    open var patientBPIQ4: Int? = null

//    var patientBPIQ4: Int?
//        get() = test.patientBPIQ4
//        set(value) {
//            test.patientBPIQ4 = value
//        }

    @Required
    open var patientBPIQ5: Int? = null

//    var patientBPIQ5: Int?
//        get() = test.patientBPIQ5
//        set(value) {
//            test.patientBPIQ5 = value
//        }

    @Required
    open var patientBPIQ6: Int? = null

//    var patientBPIQ6: Int?
//        get() = test.patientBPIQ6
//        set(value) {
//            test.patientBPIQ6 = value
//        }

    @Required
    open var patientBPIQ7: Int? = null

//    var patientBPIQ7: Int?
//        get() = test.patientBPIQ7
//        set(value) {
//            test.patientBPIQ7 = value
//        }

    @Required
    open var patientBPIQ8: Int? = null

//    var patientBPIQ8: Int?
//        get() = test.patientBPIQ8
//        set(value) {
//            test.patientBPIQ8 = value
//        }

    @Required
    open var patientBPIQ9: Int? = null

//    var patientBPIQ9: Int?
//        get() = test.patientBPIQ9
//        set(value) {
//            test.patientBPIQ9 = value
//        }

    @Required
    open var patientBPIQ10: Int? = null

//    var patientBPIQ10: Int?
//        get() = test.patientBPIQ10
//        set(value) {
//            test.patientBPIQ10 = value
//        }

    @Required
    open var patientBPIQ11: Int? = null

//    var patientBPIQ11: Int?
//        get() = test.patientBPIQ11
//        set(value) {
//            test.patientBPIQ11 = value
//        }

    @Required
    open var patientBPIQ12: Int? = null

//    var patientBPIQ12: Int?
//        get() = test.patientBPIQ12
//        set(value) {
//            test.patientBPIQ12 = value
//        }

    @Required
    open var patientBPIcircleX: Float? = null

//    var patientBPIcircleX: Float?
//        get() = test.patientBPIcircleX
//        set(value) {
//            test.patientBPIcircleX = value
//        }

    @Required
    open var patientBPIcircleY: Float? = null

//    var patientBPIcircleY: Float?
//        get() = test.patientBPIcircleY
//        set(value) {
//            test.patientBPIcircleY = value
//        }

    @Required
    open var patientBPITestSeverityResult: Float? = null

//    var patientBPITestSeverityResult: Float?
//        get() = test.patientBPITestSeverityResult
//        set(value) {
//            test.patientBPITestSeverityResult = value
//        }

    @Required
    open var patientBPITestInterferenceResult: Float? = null
//    var patientBPITestInterferenceResult: Float?
//        get() = test.patientBPITestInterferenceResult
//        set(value) {
//            test.patientBPITestInterferenceResult = value
//        }


}