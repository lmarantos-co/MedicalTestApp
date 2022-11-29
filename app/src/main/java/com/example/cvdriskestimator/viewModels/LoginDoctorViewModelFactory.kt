package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginDoctorViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginDoctorViewModel::class.java)) {
            return LoginDoctorViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}