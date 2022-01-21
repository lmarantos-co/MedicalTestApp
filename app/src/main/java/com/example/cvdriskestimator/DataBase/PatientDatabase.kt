package com.example.cvdriskestimator.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Patient::class] , version = 1 , exportSchema = false)
abstract class PatientDatabase : RoomDatabase(){

    abstract val patientDAO : PatientDAO

    companion object
    {
        @Volatile
        private var INSTANCE : PatientDatabase?= null

        fun getInstance(context : Context) : PatientDatabase
        {
            synchronized(this)
            {
                var instance : PatientDatabase? = INSTANCE
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context.applicationContext , PatientDatabase::class.java , "patient_data_base").build()
                }
                return instance
            }
        }
    }

}