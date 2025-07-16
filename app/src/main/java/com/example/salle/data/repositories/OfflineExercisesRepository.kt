package com.example.salle.data.repositories

import com.example.salle.data.RoutineDao
import com.example.salle.data.model.Exercise

class OfflineExercisesRepository(private val routineDao: RoutineDao) : ExercisesRepository {
    override suspend fun insertExercise(exercise: Exercise) = routineDao.insert(exercise)
}