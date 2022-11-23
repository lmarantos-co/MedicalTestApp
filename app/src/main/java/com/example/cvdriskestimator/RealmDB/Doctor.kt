package com.example.cvdriskestimator.RealmDB

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class Doctor() : RealmModel {

    @PrimaryKey
    var id : String = ""

    @Required
    var doctorUserName : String = ""

    @Required
    var doctorPassword : String = ""

    @Required
    lateinit var doctorCustomers : ArrayList<String>
}