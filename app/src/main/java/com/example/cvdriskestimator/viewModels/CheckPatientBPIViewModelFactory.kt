package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckPatientBPIViewModelFactory {

    class CheckBPIPatientViewModelFactory: ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CheckBPIPatientViewModel::class.java)) {
                return CheckBPIPatientViewModel() as T
            }
            throw IllegalArgumentException("Unknown View Model CLass")
        }

    }
}