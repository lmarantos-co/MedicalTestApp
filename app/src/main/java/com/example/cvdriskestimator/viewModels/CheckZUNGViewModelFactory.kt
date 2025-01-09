package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckZUNGPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckZUNGPatientViewModel::class.java)) {
            return CheckZUNGPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}