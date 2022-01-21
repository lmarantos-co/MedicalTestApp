package com.example.cvdriskestimator.SQLDatabase

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class SqlDatabaseConnection {

    val ip = "185.138.42.154"
    val port = "3306"
    val Classes = "net.sourceforge.jtds.jdbc.Driver"
    val database : String = "estimator_db"
    val userName : String = "estimator_user"
    val userPassword : String = "^Avsq288"
    val url : String = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databaseName=" + database + ";user=" + userName + ";password=" + userPassword +";";
    private var connection : Connection? = null

    init {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


    }

    fun getConnection() : Connection
    {        //try to establish the database connections
        try {
            Class.forName(Classes)
            connection = DriverManager.getConnection(url)
        }
        catch(e : Exception)
        {
            e.printStackTrace()
        }
        return connection!!
    }

    fun insertPatient(SQLPatient: SQLPatient, sqlConnection: Connection) : Boolean
    {
        var userExists : Boolean = false
        //check if user exists in database
        var query : String = "SELECT EXISTS (SELECT 1 FROM patient_table WHERE userName=" + SQLPatient.userName+")"
        var statement1 : Statement = sqlConnection!!.createStatement()
        var resultSet1 : ResultSet = statement1.executeQuery(query)
        if (resultSet1.next())
        {
            userExists = true
        }
        //if the user does not exist create him
        if (!userExists) {
            var query2: String =
                "INSERT INTO patient_table (userName, password, patient_age, patient_sex, patient_race, patient_SBP, " +
                        "patient_TCH, patient_HDL, patient_smoking_status, patient_treatment_status)" +
            "VALUES (${SQLPatient.userName}, ${SQLPatient.patientSex}, ${SQLPatient.patientAge}, ${SQLPatient.patientSex}, ${SQLPatient.patientRace}, "
            "${SQLPatient.SBP}, ${SQLPatient.TCH}, ${SQLPatient.HDL}, ${SQLPatient.smoker}, ${SQLPatient.treatment}"
            var statement2 : Statement = connection!!.createStatement()
            statement2.executeQuery(query2)
        }
        return userExists
    }

    fun updatePatient(SQLPatient : SQLPatient, sqlConnection: Connection)
    {
        var updateQuery = "UPDATE patient_table SET userName = ${SQLPatient.userName}, password = ${SQLPatient.password} patient_age = ${SQLPatient.patientAge}, "+
        "patient_sex = ${SQLPatient.patientSex}, patient_race = ${SQLPatient.patientRace}, patient_SBP = ${SQLPatient.SBP}, patient_TCH = ${SQLPatient.TCH}, " +
        "patient_HDL = ${SQLPatient.HDL}, patient_smoking_status  ${SQLPatient.smoker}, patient_treatment_status = ${SQLPatient.treatment}" +
        "WHERE userName = ${SQLPatient.userName}"

        var statement : Statement = sqlConnection!!.createStatement()
        statement.executeQuery(updateQuery)
    }

    fun retrievePatient(userName : String) : SQLPatient
    {
        var SQLPatient: SQLPatient? = null
        var retrieveQuery = "SELECT * FROM patient_table WHERE userName = userName"
        var statement : Statement = connection!!.createStatement()
        var resultSet : ResultSet = statement.executeQuery(retrieveQuery)
        while (resultSet.next())
        {
            SQLPatient!!.userName = resultSet.getString("userName")
            SQLPatient!!.password = resultSet.getString("password")
            SQLPatient!!.patientAge = resultSet.getString("patient_age")
            SQLPatient!!.patientSex = resultSet.getString("patient_sex")
            SQLPatient!!.patientRace = resultSet.getString("patient_race")
            SQLPatient!!.SBP = resultSet.getString("patient_SBP")
            SQLPatient!!.TCH = resultSet.getString("patient_TCH")
            SQLPatient!!.HDL = resultSet.getString("patient_HDL")
            SQLPatient!!.smoker = resultSet.getString("patient_smoking_status")
            SQLPatient!!.treatment = resultSet.getString("patient_treatment_status")
        }
        return SQLPatient!!
    }


}