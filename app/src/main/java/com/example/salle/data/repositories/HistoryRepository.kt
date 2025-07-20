package com.example.salle.data.repositories

import com.example.salle.data.model.History

interface HistoryRepository {
    suspend fun insertHistory(history: History)
}