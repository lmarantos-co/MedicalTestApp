package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginPatientViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginPatientViewModel::class.java)) {
            return LoginPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}