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

    //variables related with Beck Anxiety Inventory Test

    var patientBAIQ1 : Int? = null


    var patientBAIQ2 : Int? = null


    var patientBAIQ3 : Int? = null


    var patientBAIQ4 : Int? = null


    var patientBAIQ5 : Int? = null


    var patientBAIQ6 : Int? = null


    var patientBAIQ7 : Int? = null


    var patientBAIQ8 : Int? = null


    var patientBAIQ9 : Int? = null


    var patientBAIQ10 : Int? = null


    var patientBAIQ11 : Int? = null


    var patientBAIQ12 : Int? = null


    var patientBAIQ13 : Int? = null

    var patientBAIQ14 : Int? = null


    var patientBAIQ15 : Int? = null


    var patientBAIQ16 : Int? = null


    var patientBAIQ17 : Int? = null


    var patientBAIQ18 : Int? = null


    var patientBAIQ19 : Int? = null


    var patientBAIQ20 : Int? = null


    var patientBAIQ21 : Int? = null

    //variables related wth the Med Diet Score Test

    var patientMDSQ1 : Int? = null

    var patientMDSQ2 : Int? = null

    var patientMDSQ3 : Int? = null

    var patientMDSQ4: Int? = null

    var patientMDSQ5 : Int? = null

    var patientMDSQ6 : Int? = null

    var patientMDSQ7 : Int? = null

    var patientMDSQ8 : Int? = null

    var patientMDSQ9 : Int? = null

    var patientMDSQ10 : Int? = null
}