package com.example.salle.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

val navigationItems = listOf(
    NavigationItem(
        title = "Routines",
        icon = Icons.Default.Home,
        route = Pages.Routines.name
    ),
    NavigationItem(
        title = "Activity",
        icon = Icons.Default.Person,
        route = Pages.Activity.name
    ),
)