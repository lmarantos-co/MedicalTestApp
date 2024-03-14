package com.example.cvdriskestimator.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckSIDASPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckSIDASPatientViewModel::class.java)) {
            return CheckSIDASPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }
}