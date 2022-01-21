package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.DataBase.PatientRepository
import java.lang.IllegalArgumentException

class RegisterPatientViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterPatientViewModel::class.java)) {
            return RegisterPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}