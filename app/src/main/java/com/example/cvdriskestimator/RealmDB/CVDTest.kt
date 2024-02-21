package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class CVDTest : RealmModel
{

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
    open var testName : String? = "CVDTest"

    @Required
    open var patientAge: String? = null

//    var patientAge: String
//        get() = test.patientAge
//        set(value) {
//            test.patientAge = value
//        }

    //CVD TEST

    @Required
    open var patientSex: String? = null

//    var patientSex: String
//        get() = test.patientSex
//        set(value) {
//            test.patientSex = value
//        }

    @Required
    open var patientRace: String? = null

//    var patientRace: String
//        get() = test.patientRace
//        set(value) {
//            test.patientRace = value
//        }

    @Required
    open var SSB: String? = null

//    var SSB: String
//        get() = test.SSB
//        set(value) {
//            test.SSB = value
//        }

    @Required
    open var TCH: String? = null

//    var TCH: String
//        get() = test.TCH
//        set(value) {
//            test.TCH = value
//        }

    @Required
    open var HDL: String? = null

//    var HDL: String
//        get() = test.HDL
//        set(value) {
//            test.HDL = value
//        }

    @Required
    open var smoker: String? = null

//    var smoker: String
//        get() = test.smoker
//        set(value) {
//            test.smoker = value
//        }

    @Required
    open var cvdTestResult: Int? = null

//    var cvdTestResult: Int?
//        get() = test.cvdTestResult
//        set(value) {
//            test.cvdTestResult = value
//        }

    @Required
    open var treatment: String? = null

//    var treatment: String?
//        get() = test.treatment
//        set(value) {
//            test.treatment = value!!
//        }


}