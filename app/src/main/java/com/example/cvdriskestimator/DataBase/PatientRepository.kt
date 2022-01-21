package com.example.cvdriskestimator.DataBase

import androidx.lifecycle.LiveData




class PatientRepository(private val dao : PatientDAO) {

    val patients = dao.getAllPatients()

    suspend fun insert(patient : Patient)
    {
        dao.insertPatient(patient)
    }

    suspend fun update(patient : Patient)
    {
        dao.updatePatient(patient)
    }

    suspend fun delete(patient : Patient)
    {
        dao.deletePatient(patient)
    }

    suspend fun deleteAll()
    {
        dao.deleteAll()
    }

    fun getCount(): Int {
        return dao.getCount()
    }

    fun userExists(name : String) : Boolean
    {
        return dao.userExists(name)
    }

}