package com.example.salle.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salle.data.model.RoutineWithExercises
import com.example.salle.data.repositories.RoutinesWithExercisesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoutineHomeScreenViewModel(routinesWithExercisesRepository: RoutinesWithExercisesRepository) : ViewModel() {

    val routineHomeScreenUiState: StateFlow<RoutineHomeScreenUiState> =
        routinesWithExercisesRepository.getRoutinesWithExercises().map { RoutineHomeScreenUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RoutineHomeScreenUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class RoutineHomeScreenUiState(val routinesWithExercisesList: List<RoutineWithExercises> = listOf())