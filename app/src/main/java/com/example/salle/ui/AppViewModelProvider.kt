package com.example.salle.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.salle.SalleApplication
import com.example.salle.ui.routine.AddRoutineViewModel
import com.example.salle.ui.routine.RoutineEditViewModel
import com.example.salle.ui.routine.RoutineHomeScreenViewModel
import com.example.salle.ui.routine.RoutinePlayViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AddRoutineViewModel(
                routinesRepository = salleApplication().container.routinesRepository,
                exercisesRepository = salleApplication().container.exercisesRepository
            )
        }

        initializer {
            RoutineHomeScreenViewModel(
                routinesRepository = salleApplication().container.routinesRepository,
                routinesWithExercisesRepository = salleApplication().container.routinesWithExercisesRepository
            )
        }

        initializer {
            RoutineEditViewModel(
                this.createSavedStateHandle(),
                routinesWithExercisesRepository = salleApplication().container.routinesWithExercisesRepository,
                routinesRepository = salleApplication().container.routinesRepository,
                exercisesRepository = salleApplication().container.exercisesRepository
            )
        }

        initializer {
            RoutinePlayViewModel(
                this.createSavedStateHandle(),
                routinesWithExercisesRepository = salleApplication().container.routinesWithExercisesRepository,
                historyRepository = salleApplication().container.historyRepository
            )
        }
    }
}

fun CreationExtras.salleApplication(): SalleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as SalleApplication)