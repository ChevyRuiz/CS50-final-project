package com.example.salle.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

enum class Pages(
    val title: String,
    val icon: ImageVector? = null,
    val showsBottomBar: Boolean
    ) {
    Routines(
        title = "Routines",
        icon = Icons.Default.Menu,
        showsBottomBar = true

    ),
    Activity(
        title = "Activity",
        icon = Icons.Default.Refresh,
        showsBottomBar = true
    ),
    AddRoutines(
        title = "Add Routines",
        showsBottomBar = false
    )
}

enum class NestedGraph() {
    Nested
}