package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class CVDTest : RealmModel
{
    @PrimaryKey
    var testId : String = ""

    @Required
    var patientId : String = ""

    var testDate : Date? = null

    @Required
    var testName : String = "CVDTest"

    //CVD TEST

    @Required
    var patientAge : String = ""

    @Required
    var patientSex : String = ""

    @Required
    var patientRace : String = ""

    @Required
    var SSB : String = ""

    @Required
    var TCH : String = ""

    @Required
    var HDL : String = ""

    @Required
    var smoker : String = ""

    var cvdTestResult : Int? = null

}