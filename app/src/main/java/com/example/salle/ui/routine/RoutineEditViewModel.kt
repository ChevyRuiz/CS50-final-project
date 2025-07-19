package com.example.salle.ui.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.salle.data.model.RoutineWithExercises
import com.example.salle.data.repositories.ExercisesRepository
import com.example.salle.data.repositories.RoutinesRepository
import com.example.salle.data.repositories.RoutinesWithExercisesRepository
import com.example.salle.ui.navigation.RoutineEdit
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RoutineEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val routinesWithExercisesRepository: RoutinesWithExercisesRepository,
    private val routinesRepository: RoutinesRepository,
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val selectedRoutine : RoutineEdit = savedStateHandle.toRoute()
    private val selectedRoutineId : Int = selectedRoutine.id

    var routineUiState by mutableStateOf(RoutineUiState())
        private set

    init {
        viewModelScope.launch {
            routineUiState = routinesWithExercisesRepository.getRoutineWithExercises(selectedRoutineId)
                .filterNotNull()
                .first()
                .toRoutineUiState(true)
        }
    }

    companion object {
        const val MIN_EXERCISES = 1
        const val MAX_EXERCISES = 6
    }

    fun updateUiState(routineName: String){
        routineUiState = routineUiState.copy(routineName = routineName)
        routineUiState = routineUiState.copy(isEntryValid = validateInput())
    }

    fun updateUiState(exerciseInfo: ExerciseInfo){
        val index = routineUiState.exercises.indexOfFirst { it.id == exerciseInfo.id }
        if (index != -1) {
            val updatedList = routineUiState.exercises.toMutableList()
            updatedList[index] = exerciseInfo
            routineUiState = routineUiState.copy(
                exercises = updatedList,
            )
        }
        routineUiState = routineUiState.copy(isEntryValid = validateInput())
    }

    fun updateUiStateAddExercice(){
        if (routineUiState.exercises.size < MAX_EXERCISES) {
            val lastElementId = routineUiState.exercises.last().id
            routineUiState = routineUiState.copy(
                exercises = routineUiState.exercises + ExerciseInfo(id = lastElementId + 1),
                canAddExercises = true,
                canDeleteExercises = true,
            )
            routineUiState = routineUiState.copy(isEntryValid = validateInput())
        } else {
            routineUiState = routineUiState.copy(
                canAddExercises = false,
                canDeleteExercises = true,
            )
            routineUiState = routineUiState.copy(isEntryValid = validateInput())
        }
    }

    fun updateUiStateDeleteExercice(){
        if (routineUiState.exercises.size > MIN_EXERCISES){
            routineUiState = routineUiState.copy(
                exercises = routineUiState.exercises.dropLast(1),
                canDeleteExercises = true,
                canAddExercises = true,
            )
            routineUiState = routineUiState.copy(isEntryValid = validateInput())
        } else {
            routineUiState = routineUiState.copy(
                canDeleteExercises = false,
                canAddExercises = true,
            )
            routineUiState = routineUiState.copy(isEntryValid = validateInput())
        }
    }

    private fun validateInput(uiState: RoutineUiState = routineUiState) : Boolean{
        return with(uiState) {
            routineName.isNotBlank() && routineName.length <= 25 && exercises.all {
                it.name.isNotBlank() && it.name.length <= 25 && it.sets.isNotBlank() && it.sets.isDigitsOnly() && it.sets.length < 3 &&
                        it.reps.isNotBlank() && it.reps.isDigitsOnly() && it.reps.length < 3 &&
                        it.weight.isNotBlank() && it.weight.isDigitsOnly() && it.weight.length < 3
            }

        }
    }

    suspend fun saveRoutineAndExercises() {
        if(validateInput()){
            routinesRepository.deleteRoutine(selectedRoutineId)
            val routineId = routinesRepository.insertRoutine(routineUiState.toRoutine())

            routineUiState.exercises.forEach {
                exercisesRepository.insertExercise(it.toExercise(routineId = routineId))
            }
        }
    }

}

fun RoutineWithExercises.toRoutineUiState(isEntryValid: Boolean = false): RoutineUiState = RoutineUiState(
    routineName = routine.name,
    exercises = exercises.map { it.toExerciseInfo() }.sortedBy { it.id },
    isEntryValid = isEntryValid,
    canAddExercises = exercises.size < 6,
    canDeleteExercises = exercises.size > 1
)