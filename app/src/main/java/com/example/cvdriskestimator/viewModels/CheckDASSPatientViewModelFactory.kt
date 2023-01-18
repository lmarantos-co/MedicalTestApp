package com.example.cvdriskestimator.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckDASSPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckDASSPatientViewModel::class.java)) {
            return CheckDASSPatientViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}