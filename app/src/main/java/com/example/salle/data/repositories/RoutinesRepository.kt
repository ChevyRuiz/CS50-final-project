package com.example.salle.data.repositories

import com.example.salle.data.model.Routine

interface RoutinesRepository {
    suspend fun insertRoutine(routine: Routine) : Long
    suspend fun deleteRoutine(id: Int)
}