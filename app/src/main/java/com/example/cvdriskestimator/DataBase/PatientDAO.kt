package com.example.cvdriskestimator.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PatientDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPatient(patient : Patient)
    {

    }

    @Update
    suspend fun updatePatient(patient : Patient)
    {

    }

    @Delete
    suspend fun deletePatient(patient : Patient)
    {

    }

    @Query("DELETE from patient_data_table")
    suspend fun deleteAll() {

    }

    @Query("SELECT * FROM patient_data_table")
    fun getAllPatients() : LiveData<List<Patient>>

    @Query("SELECT EXISTS (SELECT * FROM patient_data_table WHERE patient_username=:username)")
    fun userExists(username : String) : Boolean


    @Query("SELECT COUNT(*) FROM patient_data_table")
    fun getCount(): Int
}