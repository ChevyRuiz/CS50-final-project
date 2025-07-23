package com.example.salle.data.repositories

import com.example.salle.data.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun insertHistory(history: History)
    fun getRoutinesWithDates() : Flow<List<History>>
    fun getTenRoutinesWithDates() : Flow<List<History>>
}