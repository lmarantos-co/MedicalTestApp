package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckBDIViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckBDIPatientViewModel::class.java)) {
            return CheckBDIPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}