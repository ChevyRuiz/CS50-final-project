package com.example.salle.ui.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.salle.data.model.Exercise
import com.example.salle.data.model.Routine
import com.example.salle.data.repositories.ExercisesRepository
import com.example.salle.data.repositories.RoutinesRepository

class AddRoutineViewModel(
    private val routinesRepository: RoutinesRepository,
    private val exercisesRepository: ExercisesRepository,
) : ViewModel() {

    var routineUiState by mutableStateOf(RoutineUiState())
        private set

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
            val routineId = routinesRepository.insertRoutine(routineUiState.toRoutine())

            routineUiState.exercises.forEach {
                exercisesRepository.insertExercise(it.toExercise(routineId = routineId))
            }
        }
    }


}

data class RoutineUiState(
    val routineName: String = "",
    val exercises: List<ExerciseInfo> = listOf(ExerciseInfo()),
    val canAddExercises: Boolean = true,
    val canDeleteExercises: Boolean = false,
    val isEntryValid: Boolean = false,
)

data class ExerciseInfo(
    val id: Int = 0,
    val name: String = "",
    val sets: String = "",
    val reps: String = "",
    val weight: String = "",
    val routineId: Int = 0,
)

fun RoutineUiState.toRoutine(): Routine = Routine(
    name = routineName
)

fun ExerciseInfo.toExercise(routineId: Long): Exercise = Exercise(
    name = name,
    sets = sets.toIntOrNull() ?: 0,
    reps = reps.toIntOrNull() ?: 0,
    weight = weight.toIntOrNull() ?: 0,
    routineId = routineId
)

fun Exercise.toExerciseInfo(): ExerciseInfo = ExerciseInfo(
    id = id,
    name = name,
    sets = sets.toString(),
    reps = reps.toString(),
    weight = weight.toString(),
    routineId = routineId.toInt()
)