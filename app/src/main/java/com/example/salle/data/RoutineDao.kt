package com.example.salle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.salle.data.model.Exercise
import com.example.salle.data.model.Routine

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exercise: Exercise) : Long
}