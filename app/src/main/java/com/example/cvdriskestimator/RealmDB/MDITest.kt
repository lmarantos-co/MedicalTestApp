package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.Date

@RealmClass
open class MDITest : RealmModel {
    @PrimaryKey
    var testId: String = ""

    @Required
    var patientId: String = ""

    var testDate: Date? = null

    @Required
    var testName: String = "MDIest"

    //variables related with Major Depression Inventory Test

    var patientMDIQ1: Int? = null


    var patientMDIQ2: Int? = null


    var patientMDIQ3: Int? = null


    var patientMDIQ4: Int? = null


    var patientMDIQ5: Int? = null


    var patientMDIQ6: Int? = null


    var patientMDIQ7: Int? = null


    var patientMDIQ8: Int? = null


    var patientMDIQ9: Int? = null


    var patientMDIQ10: Int? = null


    var patientMDIQ11: Int? = null


    var patientMDIQ12: Int? = null


    var patientMDIQ13: Int? = null

    var patientMDITestResult: Int? = null
}