package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RegisterDoctorViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterDoctorViewModel::class.java)) {
            return RegisterDoctorViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}