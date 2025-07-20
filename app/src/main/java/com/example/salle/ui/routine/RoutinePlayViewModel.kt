package com.example.salle.ui.routine

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.salle.data.model.History
import com.example.salle.data.repositories.HistoryRepository
import com.example.salle.data.repositories.RoutinesWithExercisesRepository
import com.example.salle.ui.navigation.RoutineEdit
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RoutinePlayViewModel(
    savedStateHandle: SavedStateHandle,
    private val routinesWithExercisesRepository: RoutinesWithExercisesRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val selectedRoutine : RoutineEdit = savedStateHandle.toRoute()
    private val selectedRoutineId : Int = selectedRoutine.id

    var routineUiState by mutableStateOf(RoutineUiState())
        private set

    var routinePlayUiState by mutableStateOf(RoutinePlayUiState())
        private set

    init {
        viewModelScope.launch {
            routineUiState = routinesWithExercisesRepository.getRoutineWithExercises(selectedRoutineId)
                .filterNotNull()
                .first()
                .toRoutineUiState(true)

            val checkboxes: MutableList<Boolean> = mutableListOf()
            routineUiState.exercises.forEach{
                repeat(it.sets.toInt()) {
                    checkboxes.add(false)
                }
            }
            routinePlayUiState = RoutinePlayUiState(checkboxes = checkboxes.toList())
        }
    }

    fun updateUiState(index: Int, checked: Boolean) {
        val updatedCheckboxes: MutableList<Boolean> = routinePlayUiState.checkboxes.toMutableList()
        updatedCheckboxes[index] = checked
        routinePlayUiState = routinePlayUiState.copy(checkboxes = updatedCheckboxes.toList())
        routinePlayUiState = routinePlayUiState.copy(isRoutineCompleted = validateInput())
    }

    private fun validateInput() : Boolean {
        return routinePlayUiState.checkboxes.all { it }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveHistory() {
        viewModelScope.launch {
            if (validateInput()) {
                historyRepository.insertHistory(
                    History(
                        routineName = routineUiState.routineName,
                        timeStamp = LocalDateTime.now()
                    )
                )
            }
        }
    }
}

data class RoutinePlayUiState(
    val checkboxes: List<Boolean> = listOf(),
    val isRoutineCompleted: Boolean = false,
)