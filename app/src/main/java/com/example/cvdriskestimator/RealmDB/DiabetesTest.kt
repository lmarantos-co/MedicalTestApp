package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class DiabetesTest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "DiabetesTest"


    //DIABETES CHECK

    @Required
    var treatment: String = ""

    @Required
    var patientPAM: String = ""

    @Required
    var patientSteroids: String = ""

    @Required
    var patientBMI: String = ""

    @Required
    var patientSiblings: String = ""

    var diabetesTestResult: Double? = null

}