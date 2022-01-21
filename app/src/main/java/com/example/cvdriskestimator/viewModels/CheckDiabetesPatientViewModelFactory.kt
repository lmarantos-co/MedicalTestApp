package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckDiabetesPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckDiabetesPatientViewModel::class.java)) {
            return CheckDiabetesPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}