package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckSTAIPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckSTAIPatientViewModel::class.java)) {
            return CheckSTAIPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}