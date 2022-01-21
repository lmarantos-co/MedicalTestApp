package com.example.cvdriskestimator.MySQLDatabase

import android.os.AsyncTask
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class UpdateDBAsyncTask(patient: SQLPatient) : AsyncTask<Void, Void, Boolean>(){

    private val URL = "jdbc:mysql://185.138.42.154:3306/estimator_db"
    val database : String = "estimator_db"
    val userName : String = "estimator_user"
    private val userPassword : String = "^Avsq288"
    private  var sqlPatient : SQLPatient = patient

    override fun doInBackground(vararg params: Void?): Boolean {

        var updateExecuted = false

        try {
            var connection : Connection = DriverManager.getConnection(URL, userName , userPassword)

            var updateQuery = "UPDATE patient_table SET userName = ${sqlPatient.userName}, password = ${sqlPatient.password} patient_age = ${sqlPatient.patientAge}, "+
                    "patient_sex = ${sqlPatient.patientSex}, patient_race = ${sqlPatient.patientRace}, patient_SBP = ${sqlPatient.SBP}, patient_TCH = ${sqlPatient.TCH}, " +
                    "patient_HDL = ${sqlPatient.HDL}, patient_smoking_status  ${sqlPatient.smoker}, patient_treatment_status = ${sqlPatient.treatment}" +
                    "WHERE userName = ${sqlPatient.userName}"

            val statement : PreparedStatement = connection.prepareStatement(updateQuery)
            var resultSet : ResultSet = statement.executeQuery()
            updateExecuted = true
        }
        catch(e : Exception){
            e.printStackTrace()
            updateExecuted = false
        }
        return updateExecuted
    }
}