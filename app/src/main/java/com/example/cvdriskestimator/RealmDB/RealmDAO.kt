package com.example.cvdriskestimator.RealmDB

import androidx.lifecycle.MutableLiveData
import io.realm.Realm

class RealmDAO {

    private var realm = Realm.getDefaultInstance()
    private  var patientData = MutableLiveData<Patient>()

    //fetch the patient record of the Patient
    fun fetchPatientData(userName : String) : MutableLiveData<Patient>
    {
        patientData.value = realm.where(Patient::class.java).isNotNull("id").equalTo("userName" , userName).findFirst()
        return patientData
    }

}