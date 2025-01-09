package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckGPDQPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PDQCheckViewModel::class.java)) {
            return PDQCheckViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }
}