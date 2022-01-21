package com.example.cvdriskestimator.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_data_table")
data class Patient(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "patient_id")
    val id  : Int,

    @ColumnInfo(name = "patient_username")
    val name : String,

    @ColumnInfo(name = "patient_password")
    val password : String

)
