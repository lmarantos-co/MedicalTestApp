package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class Patient() : RealmModel {

    @PrimaryKey
    var id : String = ""

    @Required
    var userName : String = ""

    @Required
    var password : String = ""

    //patient data

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

    @Required
    var treatment : String = ""

    @Required
    var patientPAM : String = ""

    @Required
    var patientSteroids : String = ""

    @Required
    var patientBMI : String = ""

    @Required
    var patientSiblings : String = ""


    //variables related with Major Depression Inventory Test

    var patientMDIQ1 : Int? = null


    var patientMDIQ2 : Int? = null


    var patientMDIQ3 : Int? = null


    var patientMDIQ4 : Int? = null


    var patientMDIQ5 : Int? = null


    var patientMDIQ6 : Int? = null


    var patientMDIQ7 : Int? = null


    var patientMDIQ8 : Int? = null


    var patientMDIQ9 : Int? = null


    var patientMDIQ10 : Int? = null


    var patientMDIQ11 : Int? = null


    var patientMDIQ12 : Int? = null


    var patientMDIQ13 : Int? = null
}