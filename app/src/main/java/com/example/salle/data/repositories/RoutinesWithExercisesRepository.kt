package com.example.salle.data.repositories

import com.example.salle.data.model.RoutineWithExercises
import kotlinx.coroutines.flow.Flow

interface RoutinesWithExercisesRepository {
    fun getRoutinesWithExercises() : Flow<List<RoutineWithExercises>>
    fun getRoutineWithExercises(id: Int) : Flow<RoutineWithExercises>
}