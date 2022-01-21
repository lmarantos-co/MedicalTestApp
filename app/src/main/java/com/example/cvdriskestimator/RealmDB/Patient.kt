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



}