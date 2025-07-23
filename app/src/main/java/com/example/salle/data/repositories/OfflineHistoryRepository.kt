package com.example.salle.data.repositories

import com.example.salle.data.RoutineDao
import com.example.salle.data.model.History
import kotlinx.coroutines.flow.Flow

class OfflineHistoryRepository(private val routineDao: RoutineDao) : HistoryRepository {
    override suspend fun insertHistory(history: History) = routineDao.insert(history)
    override fun getRoutinesWithDates(): Flow<List<History>> = routineDao.getRoutinesWithDates()
    override fun getTenRoutinesWithDates(): Flow<List<History>> = routineDao.getTenRoutinesWithDates()
}