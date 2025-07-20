package com.example.salle.data.repositories

import com.example.salle.data.RoutineDao
import com.example.salle.data.model.History

class OfflineHistoryRepository(private val routineDao: RoutineDao) : HistoryRepository {
    override suspend fun insertHistory(history: History) = routineDao.insert(history)
}