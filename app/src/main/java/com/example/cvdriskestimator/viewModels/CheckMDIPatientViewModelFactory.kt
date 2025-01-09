package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckMDIPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckMDIPatientViewModel::class.java)) {
            return CheckMDIPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }
}