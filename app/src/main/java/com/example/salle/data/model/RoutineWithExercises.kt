package com.example.salle.data.model

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

data class RoutineWithExercises(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "id",
        entityColumn = "routineId"
    )
    val exercises: List<Exercise>
)
