package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckGDSPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckGDSViewModel::class.java)) {
            return CheckGDSViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }
}