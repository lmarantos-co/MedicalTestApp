package com.example.cvdriskestimator.SQLDatabase

class SQLRepository {

    val sqlDatabaseConnection = SqlDatabaseConnection()

    //function to insert user
    fun insertPatient(SQLPatient : SQLPatient) : Boolean
    {
        var sqlConnection = sqlDatabaseConnection.getConnection()
        return sqlDatabaseConnection.insertPatient(SQLPatient, sqlConnection)
    }

    //function to update user
    fun updatePatient(SQLPatient : SQLPatient)
    {
        var sqlConnection = sqlDatabaseConnection.getConnection()
        sqlDatabaseConnection.updatePatient(SQLPatient, sqlConnection)
    }

    //function to retrieve patient
    fun retrievePatient(userName : String) : SQLPatient
    {
        var sqlConnection = sqlDatabaseConnection.getConnection()
        return sqlDatabaseConnection.retrievePatient(userName)
    }

}