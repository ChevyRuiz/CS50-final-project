package com.example.salle.data.repositories

import com.example.salle.data.model.Exercise
import com.example.salle.data.model.Routine

interface ExercisesRepository {
    suspend fun insertExercise(exercise: Exercise) : Long
}