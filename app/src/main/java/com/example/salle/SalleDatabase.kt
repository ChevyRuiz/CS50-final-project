package com.example.salle

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.salle.data.RoutineDao
import com.example.salle.data.model.Exercise
import com.example.salle.data.model.Routine

@Database(entities = [Routine::class, Exercise::class], version = 2, exportSchema = false)
abstract class SalleDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    companion object {
        @Volatile
        private var Instance: SalleDatabase? = null
        fun getDatabase(context: Context): SalleDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SalleDatabase::class.java, "salle_database")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}