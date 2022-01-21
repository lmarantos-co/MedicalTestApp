package com.example.cvdriskestimator.MySQLDatabase

import android.os.AsyncTask
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class InsertDBAsyncTask(patient : SQLPatient) : AsyncTask<Void, Void, Boolean>() {

    private val URL = "jdbc:mysql://185.138.42.15:3306/estimator_db"
    val database : String = "estimator_db"
    val userName : String = "estimator_user"
    val userPassword : String = "^Avsq288"
    private  var sqlPatient : SQLPatient

    init {
        sqlPatient = patient
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        var userExists = false
        try
        {
            var connection : Connection = DriverManager.getConnection(URL, userName , userPassword)

            var query1 : String = "SELECT EXISTS (SELECT 1 FROM patient_table WHERE userName=${sqlPatient.userName}"

            val statement: PreparedStatement = connection.prepareStatement(query1)
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next())
                userExists = true
            if (!userExists)
            {
                var query2: String =
                    "INSERT INTO patient_table (userName, password, patient_age, patient_sex, patient_race, patient_SBP, " +
                            "patient_TCH, patient_HDL, patient_smoking_status, patient_treatment_status)" +
                            "VALUES (${sqlPatient.userName}, ${sqlPatient.patientSex}, ${sqlPatient.patientAge}, ${sqlPatient.patientSex}, ${sqlPatient.patientRace}, "
                "${sqlPatient.SBP}, ${sqlPatient.TCH}, ${sqlPatient.HDL}, ${sqlPatient.smoker}, ${sqlPatient.treatment}"

                val statement2 :  PreparedStatement = connection.prepareStatement(query2)
                var resultSet2 : ResultSet = statement2.executeQuery()
            }
            return userExists
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
        return userExists
    }


}