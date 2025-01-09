package com.example.cvdriskestimator.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckHAMDPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckHAMDPatientViewModel::class.java)) {
            return CheckHAMDPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}