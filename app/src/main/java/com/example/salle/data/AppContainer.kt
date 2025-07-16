package com.example.salle.data

import android.content.Context
import com.example.salle.SalleDatabase
import com.example.salle.data.repositories.ExercisesRepository
import com.example.salle.data.repositories.OfflineExercisesRepository
import com.example.salle.data.repositories.OfflineRoutinesRepository
import com.example.salle.data.repositories.RoutinesRepository

interface AppContainer{
    val routinesRepository: RoutinesRepository
    val exercisesRepository: ExercisesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val routinesRepository: RoutinesRepository by lazy {
        OfflineRoutinesRepository(SalleDatabase.getDatabase(context).routineDao())
    }

    override val exercisesRepository: ExercisesRepository by lazy {
        OfflineExercisesRepository(SalleDatabase.getDatabase(context).routineDao())
    }
}