package com.example.salle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.salle.data.model.Exercise
import com.example.salle.data.model.History
import com.example.salle.data.model.Routine
import com.example.salle.data.model.RoutineWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exercise: Exercise) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

    @Query("DELETE FROM routines WHERE id=:id")
    suspend fun deleteRoutine(id: Int)

    @Transaction
    @Query("SELECT * FROM routines")
    fun getRoutinesWithExercises(): Flow<List<RoutineWithExercises>>

    @Transaction
    @Query("SELECT * FROM routines WHERE id=:id")
    fun getRoutineWithExercises(id: Int): Flow<RoutineWithExercises>

    @Update
    suspend fun updateRoutine(routine: Routine)

    @Update
    suspend fun updateExercise(exercise: Exercise)
}