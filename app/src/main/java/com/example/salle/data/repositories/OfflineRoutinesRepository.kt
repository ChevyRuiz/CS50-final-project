package com.example.salle.data.repositories

import com.example.salle.data.RoutineDao
import com.example.salle.data.model.Routine

class OfflineRoutinesRepository(private val routineDao: RoutineDao) : RoutinesRepository {
    override suspend fun insertRoutine(routine: Routine): Long = routineDao.insert(routine)
}