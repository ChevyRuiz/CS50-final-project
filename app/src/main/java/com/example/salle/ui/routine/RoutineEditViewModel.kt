package com.example.salle.ui.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.salle.ui.navigation.RoutineEdit

class RoutineEditViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedRoutine : RoutineEdit = savedStateHandle.toRoute()
    var routineUiState by mutableStateOf(RoutineUiState())
        private set

    fun showRoutineId() : String{
        return selectedRoutine.id.toString()
    }
}