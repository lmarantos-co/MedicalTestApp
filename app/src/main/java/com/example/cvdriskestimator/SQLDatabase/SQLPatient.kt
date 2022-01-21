package com.example.cvdriskestimator.SQLDatabase

import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

data class SQLPatient(
    var userName : String,

    var password : String,

    //patient data

    var patientAge : String?,

    var patientSex : String?,

    var patientRace : String?,

    var SBP : String?,

    var TCH : String?,

    var HDL : String?,

    var smoker : String?,

    var treatment : String?,
)
