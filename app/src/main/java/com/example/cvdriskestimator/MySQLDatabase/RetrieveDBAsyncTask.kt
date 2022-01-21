package com.example.cvdriskestimator.MySQLDatabase

import android.os.AsyncTask
import java.sql.*

class RetrieveDBAsyncTask(user : String) : AsyncTask<Void, Void, SQLPatient>() {

    private val URL = "jdbc:mysql://185.138.42.15:3306/estimator_db"
    val database : String = "estimator_db"
    val userName : String = "estimator_user"
    private val userPassword : String = "^Avsq288"
    private  var name : String = user
    private lateinit var sqlPatient: SQLPatient

    override fun doInBackground(vararg params: Void?): SQLPatient {

        var retrieveDone = false
        try {
            var connection : Connection = DriverManager.getConnection(URL, userName , userPassword)
            var query = "SELECT * FROM patient_table WHERE username=${name}"
            var statement : PreparedStatement = connection.prepareStatement(query)
            var resultSet : ResultSet = statement.executeQuery()

            while (resultSet.next())
            {
                sqlPatient!!.userName = resultSet.getString("userName")
                sqlPatient!!.password = resultSet.getString("password")
                sqlPatient!!.patientAge = resultSet.getString("patient_age")
                sqlPatient!!.patientSex = resultSet.getString("patient_sex")
                sqlPatient!!.patientRace = resultSet.getString("patient_race")
                sqlPatient!!.SBP = resultSet.getString("patient_SBP")
                sqlPatient!!.TCH = resultSet.getString("patient_TCH")
                sqlPatient!!.HDL = resultSet.getString("patient_HDL")
                sqlPatient!!.smoker = resultSet.getString("patient_smoking_status")
                sqlPatient!!.treatment = resultSet.getString("patient_treatment_status")
            }
            retrieveDone = true
            return sqlPatient!!
        }
        catch(e :  Exception)
        {
            e.printStackTrace()
            retrieveDone = false
        }
        return sqlPatient
    }
}