package com.example.cvdriskestimator.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckGASPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckGASViewModel::class.java)) {
            return CheckGASViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }
}