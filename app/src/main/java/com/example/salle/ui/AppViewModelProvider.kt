package com.example.salle.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.salle.SalleApplication
import com.example.salle.ui.routine.AddRoutineViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AddRoutineViewModel()
        }
    }
}

fun CreationExtras.salleApplication(): SalleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as SalleApplication)