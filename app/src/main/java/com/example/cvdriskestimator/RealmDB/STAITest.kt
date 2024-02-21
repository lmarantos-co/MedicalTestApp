package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date


@RealmClass
open class STAITest : RealmModel {
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
    open var testName : String? = "STAITest"

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
//            test.testName = "STAITest"
//        }

    //Stai Test

    @Required
    open var patientSTAISQ1: Int? = null

//    var patientSTAISQ1: Int?
//        get() = test.patientSTAISQ1
//        set(value) {
//            test.patientSTAISQ1 = value
//        }


    @Required
    open var patientSTAISQ2: Int? = null

//    var patientSTAISQ2: Int?
//        get() = test.patientSTAISQ2
//        set(value) {
//            test.patientSTAISQ2 = value
//        }


    @Required
    open var patientSTAISQ3: Int? = null

//    var patientSTAISQ3: Int?
//        get() = test.patientSTAISQ3
//        set(value) {
//            test.patientSTAISQ3 = value
//        }


    @Required
    open var patientSTAISQ4: Int? = null

//    var patientSTAISQ4: Int?
//        get() = test.patientSTAISQ4
//        set(value) {
//            test.patientSTAISQ4 = value
//        }


    @Required
    open var patientSTAISQ5: Int? = null

//    var patientSTAISQ5: Int?
//        get() = test.patientSTAISQ5
//        set(value) {
//            test.patientSTAISQ5 = value
//        }

    @Required
    open var patientSTAISQ6: Int? = null

//    var patientSTAISQ6: Int?
//        get() = test.patientSTAISQ6
//        set(value) {
//            test.patientSTAISQ6 = value
//        }

    @Required
    open var patientSTAISQ7: Int? = null

//    var patientSTAISQ7: Int?
//        get() = test.patientSTAISQ7
//        set(value) {
//            test.patientSTAISQ7 = value
//        }


    @Required
    open var patientSTAISQ8: Int? = null

//    var patientSTAISQ8: Int?
//        get() = test.patientSTAISQ8
//        set(value) {
//            test.patientSTAISQ8 = value
//        }


    @Required
    open var patientSTAISQ9: Int? = null

//    var patientSTAISQ9: Int?
//        get() = test.patientSTAISQ9
//        set(value) {
//            test.patientSTAISQ9 = value
//        }


    @Required
    open var patientSTAISQ10: Int? = null

//    var patientSTAISQ10: Int?
//        get() = test.patientSTAISQ10
//        set(value) {
//            test.patientSTAISQ10 = value
//        }

    @Required
    open var patientSTAISQ11: Int? = null

//    var patientSTAISQ11: Int?
//        get() = test.patientSTAISQ11
//        set(value) {
//            test.patientSTAISQ11 = value
//        }

    @Required
    open var patientSTAISQ12: Int? = null

//    var patientSTAISQ12: Int?
//        get() = test.patientSTAISQ12
//        set(value) {
//            test.patientSTAISQ12 = value
//        }

    @Required
    open var patientSTAISQ13: Int? = null

//    var patientSTAISQ13: Int?
//        get() = test.patientSTAISQ12
//        set(value) {
//            test.patientSTAISQ13 = value
//        }

    @Required
    open var patientSTAISQ14: Int? = null

//    var patientSTAISQ14: Int?
//        get() = test.patientSTAISQ14
//        set(value) {
//            test.patientSTAISQ14 = value
//        }

    @Required
    open var patientSTAISQ15: Int? = null

//    var patientSTAISQ15: Int?
//        get() = test.patientSTAISQ15
//        set(value) {
//            test.patientSTAISQ15 = value
//        }

    @Required
    open var patientSTAISQ16: Int? = null

//    var patientSTAISQ16: Int?
//        get() = test.patientSTAISQ16
//        set(value) {
//            test.patientSTAISQ16 = value
//        }

    @Required
    open var patientSTAISQ17: Int? = null

//    var patientSTAISQ17: Int?
//        get() = test.patientSTAISQ17
//        set(value) {
//            test.patientSTAISQ17 = value
//        }

    @Required
    open var patientSTAISQ18: Int? = null

//    var patientSTAISQ18: Int?
//        get() = test.patientSTAISQ18
//        set(value) {
//            test.patientSTAISQ18 = value
//        }


    @Required
    open var patientSTAISQ19: Int? = null

//    var patientSTAISQ19: Int?
//        get() = test.patientSTAISQ19
//        set(value) {
//            test.patientSTAISQ19 = value
//        }


    @Required
    open var patientSTAISQ20: Int? = null

//    var patientSTAISQ20: Int?
//        get() = test.patientSTAISQ20
//        set(value) {
//            test.patientSTAISQ20 = value
//        }


    //trait

    @Required
    open var patientSTAITQ21: Int? = null

//    var patientSTAITQ21: Int?
//        get() = test.patientSTAITQ21
//        set(value) {
//            test.patientSTAITQ21 = value
//            }


    @Required
    open var patientSTAITQ22: Int? = null

//    var patientSTAITQ22: Int?
//        get() = test.patientSTAITQ22
//        set(value) {
//            test.patientSTAITQ22 = value
//        }


    @Required
    open var patientSTAITQ23: Int? = null

//    var patientSTAITQ23: Int?
//        get() = test.patientSTAITQ23
//        set(value) {
//            test.patientSTAITQ23 = value
//        }


    @Required
    open var patientSTAITQ24: Int? = null

//    var patientSTAITQ24: Int?
//        get() = test.patientSTAITQ24
//        set(value) {
//            test.patientSTAITQ24 = value
//        }

    @Required
    open var patientSTAITQ25: Int? = null

//    var patientSTAITQ25: Int?
//        get() = test.patientSTAITQ25
//        set(value) {
//            test.patientSTAITQ25 = value
//        }


    @Required
    open var patientSTAITQ26: Int? = null
//    var patientSTAITQ26: Int?
//        get() = test.patientSTAITQ26
//        set(value) {
//            test.patientSTAITQ26 = value
//        }

    open var patientSTAITQ27: Int? = null

//    var patientSTAITQ27: Int?
//        get() = test.patientSTAITQ27
//        set(value) {
//            test.patientSTAITQ27 = value
//        }

    open var patientSTAITQ28: Int? = null

//    var patientSTAITQ28: Int?
//        get() = test.patientSTAITQ28
//        set(value) {
//            test.patientSTAITQ28 = value
//        }

    open var patientSTAITQ29: Int? = null
//    var patientSTAITQ29: Int?
//        get() = test.patientSTAITQ29
//        set(value) {
//            test.patientSTAITQ29 = value
//        }

    open var patientSTAITQ30: Int? = null
//    var patientSTAITQ30: Int?
//        get() = test.patientSTAITQ30
//        set(value) {
//            test.patientSTAITQ30 = value
//        }

    open var patientSTAITQ31: Int? = null
//    var patientSTAITQ31: Int?
//        get() = test.patientSTAITQ31
//        set(value) {
//            test.patientSTAITQ31 = value
//        }

    open var patientSTAITQ32: Int? = null
//    var patientSTAITQ32: Int?
//        get() = test.patientSTAITQ32
//        set(value) {
//            test.patientSTAITQ32 = value
//        }

    open var patientSTAITQ33: Int? = null
//    var patientSTAITQ33: Int?
//        get() = test.patientSTAITQ33
//        set(value) {
//            test.patientSTAITQ33 = value
//        }

    open var patientSTAITQ34: Int? = null
//    var patientSTAITQ34: Int?
//        get() = test.patientSTAITQ34
//        set(value) {
//            test.patientSTAITQ34 = value
//        }

    open var patientSTAITQ35: Int? = null
//    var patientSTAITQ35: Int?
//        get() = test.patientSTAITQ35
//        set(value) {
//            test.patientSTAITQ35 = value
//        }


    open var patientSTAITQ36: Int? = null
//    var patientSTAITQ36: Int?
//        get() = test.patientSTAITQ36
//        set(value) {
//            test.patientSTAITQ36 = value
//        }


    open var patientSTAITQ37: Int? = null
//    var patientSTAITQ37: Int?
//        get() = test.patientSTAITQ37
//        set(value) {
//            test.patientSTAITQ37 = value
//        }

    open var patientSTAITQ38: Int? = null

//    var patientSTAITQ38: Int?
//        get() = test.patientSTAITQ38
//        set(value) {
//            test.patientSTAITQ38 = value
//        }

    open var patientSTAITQ39: Int? = null

//    var patientSTAITQ39: Int?
//        get() = test.patientSTAITQ39
//        set(value) {
//            test.patientSTAITQ39 = value
//        }

    open var patientSTAITQ40: Int? = null

//    var patientSTAITQ40: Int?
//        get() = test.patientSTAITQ40
//        set(value) {
//            test.patientSTAITQ40 = value
//        }

    open var patientSTAISScore: Int? = null
//    var patientSTAISScore: Int?
//        get() = test.patientSTAISScore
//        set(value) {
//            test.patientSTAISScore = value
//        }

    open var patientSTAITScore: Int? = null

//    var patientSTAITScore: Int?
//        get() = test.patientSTAITScore
//        set(value) {
//            test.patientSTAITScore = value
//        }


}