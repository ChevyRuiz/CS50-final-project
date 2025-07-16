package com.example.salle.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class Routine (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)