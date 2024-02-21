package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class DASSTest : RealmModel {

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
    open var testName : String? = "DASSTest"

    @Required
    open var patientDASSQ1: Int? = null

//    var patientDASSQ1: Int?
//        get() = test.patientDASSQ1
//        set(value) {
//            test.patientDASSQ1 = value
//        }

    @Required
    open var patientDASSQ2: Int? = null

//    var patientDASSQ2: Int?
//        get() = test.patientDASSQ2
//        set(value) {
//            test.patientDASSQ2 = value
//        }

    @Required
    open var patientDASSQ3: Int? = null

//    var patientDASSQ3: Int?
//        get() = test.patientDASSQ3
//        set(value) {
//            test.patientDASSQ3 = value
//        }

    @Required
    open var patientDASSQ4: Int? = null

//    var patientDASSQ4: Int?
//        get() = test.patientDASSQ4
//        set(value) {
//            test.patientDASSQ4 = value
//        }

    @Required
    open var patientDASSQ5: Int? = null

//    var patientDASSQ5: Int?
//        get() = test.patientDASSQ5
//        set(value) {
//            test.patientDASSQ5 = value
//        }

    @Required
    open var patientDASSQ6: Int? = null

//    var patientDASSQ6: Int?
//        get() = test.patientDASSQ6
//        set(value) {
//            test.patientDASSQ6 = value
//        }

    @Required
    open var patientDASSQ7: Int? = null

//    var patientDASSQ7: Int?
//        get() = test.patientDASSQ7
//        set(value) {
//            test.patientDASSQ7 = value
//        }

    @Required
    open var patientDASSQ8: Int? = null

//    var patientDASSQ8: Int?
//        get() = test.patientDASSQ8
//        set(value) {
//            test.patientDASSQ8 = value
//        }

    @Required
    open var patientDASSQ9: Int? = null

//    var patientDASSQ9: Int?
//        get() = test.patientDASSQ9
//        set(value) {
//            test.patientDASSQ9 = value
//        }

    @Required
    open var patientDASSQ10: Int? = null

//    var patientDASSQ10: Int?
//        get() = test.patientDASSQ10
//        set(value) {
//            test.patientDASSQ10 = value
//        }

    @Required
    open var patientDASSQ11: Int? = null

//    var patientDASSQ11: Int?
//        get() = test.patientDASSQ11
//        set(value) {
//            test.patientDASSQ11 = value
//        }

    @Required
    open var patientDASSQ12: Int? = null

//    var patientDASSQ12: Int?
//        get() = test.patientDASSQ12
//        set(value) {
//            test.patientDASSQ12 = value
//        }

    @Required
    open var patientDASSQ13: Int? = null

//    var patientDASSQ13: Int?
//        get() = test.patientDASSQ13
//        set(value) {
//            test.patientDASSQ13 = value
//        }

    @Required
    open var patientDASSQ14: Int? = null

//    var patientDASSQ14: Int?
//        get() = test.patientDASSQ14
//        set(value) {
//            test.patientDASSQ14 = value
//        }

    @Required
    open var patientDASSQ15: Int? = null

//    var patientDASSQ15: Int?
//        get() = test.patientDASSQ15
//        set(value) {
//            test.patientDASSQ15 = value
//        }

    @Required
    open var patientDASSQ16: Int? = null

//    var patientDASSQ16: Int?
//        get() = test.patientDASSQ16
//        set(value) {
//            test.patientDASSQ16 = value
//        }

    @Required
    open var patientDASSQ17: Int? = null

//    var patientDASSQ17: Int?
//        get() = test.patientDASSQ17
//        set(value) {
//            test.patientDASSQ17 = value
//        }

    @Required
    open var patientDASSQ18: Int? = null

//    var patientDASSQ18: Int?
//        get() = test.patientDASSQ18
//        set(value) {
//            test.patientDASSQ18 = value
//        }

    @Required
    open var patientDASSQ19: Int? = null

//    var patientDASSQ19: Int?
//        get() = test.patientDASSQ19
//        set(value) {
//            test.patientDASSQ19 = value
//        }

    @Required
    open var patientDASSQ20: Int? = null

//    var patientDASSQ20: Int?
//        get() = test.patientDASSQ20
//        set(value) {
//            test.patientDASSQ20 = value
//        }

    @Required
    open var patientDASSQ21: Int? = null

//    var patientDASSQ21: Int?
//        get() = test.patientDASSQ21
//        set(value) {
//            test.patientDASSQ21 = value
//        }
    //DASS Test

    @Required
    open var dassStressResult: Int? = null
//    var dassStressResult: Int?
//        get() = test.dassStressResult
//        set(value) {
//            test.dassStressResult = value
//        }

    @Required
    open var dassAnxietyResult: Int? = null
//    var dassAnxietyResult: Int?
//        get() = test.dassAnxietyResult
//        set(value) {
//            test.dassAnxietyResult = value
//        }

    @Required
    open var dassDepressionResult: Int? = null

//    var dassDepressionResult: Int?
//        get() = test.dassDepressionResult
//        set(value) {
//            test.dassDepressionResult = value
//        }

}