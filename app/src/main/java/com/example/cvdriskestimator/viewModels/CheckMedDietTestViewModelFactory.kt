package com.example.cvdriskestimator.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckMedDietTestViewModelFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CheckMedDietTestViewModel::class.java)) {
                return CheckMedDietTestViewModel() as T
            }
            throw IllegalArgumentException("Unknown View Model CLass")
        }

}