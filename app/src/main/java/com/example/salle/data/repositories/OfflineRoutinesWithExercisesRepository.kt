package com.example.salle.data.repositories

import com.example.salle.data.RoutineDao
import com.example.salle.data.model.RoutineWithExercises
import kotlinx.coroutines.flow.Flow

class OfflineRoutinesWithExercisesRepository(private val routineDao: RoutineDao) : RoutinesWithExercisesRepository {
    override fun getRoutinesWithExercises(): Flow<List<RoutineWithExercises>> = routineDao.getRoutinesWithExercises()
}