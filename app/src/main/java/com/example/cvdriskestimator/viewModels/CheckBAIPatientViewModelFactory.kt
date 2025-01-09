package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckBAIPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckBAIPatientViewModel::class.java)) {
            return CheckBAIPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}