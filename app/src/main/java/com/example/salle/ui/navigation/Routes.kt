package com.example.salle.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable


// Change everything that has to do with navigation to use serializable instead of enum

// Routes

@Serializable
object RoutineHome

@Serializable
object Activity

@Serializable
object RoutineEntry

@Serializable
data class RoutineEdit(val id: Int)

@Serializable
data class RoutinePlay(val id: Int)
// Nested Graph
@Serializable object RoutinesNestedGraph
@Serializable object ActivitiesNestedGraph