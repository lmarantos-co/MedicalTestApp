package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckOPQOLPatientViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckOPQOLViewModel::class.java)) {
            return CheckOPQOLViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model CLass")
    }

}