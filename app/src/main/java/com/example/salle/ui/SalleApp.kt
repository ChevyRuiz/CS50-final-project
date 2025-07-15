package com.example.salle.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.salle.data.local.Pages
import com.example.salle.ui.navigation.SalleNavGraph

@Composable
fun SalleApp(
    // viewModel: SalleViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // val salleUiState by viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        bottomBar = {
                BottomBar(
                    navController = navController
                )
        },
        floatingActionButton = {
            if ((navController.currentBackStackEntryAsState().value?.destination?.route
                    ?: Pages.Routines.name) == Pages.Routines.name
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(Pages.AddRoutines.name) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Routine"
                    )
                }
            }
        },
        modifier = Modifier
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ) {
        innerpadding ->
        SalleNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerpadding)
        )
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Pages.valueOf(
        backStackEntry?.destination?.route ?: Pages.Routines.name)


    if(currentScreen.showsBottomBar){
        NavigationBar(
            windowInsets = NavigationBarDefaults.windowInsets,
            modifier = modifier
        ) {
            Pages.entries.forEachIndexed { index, destination ->

                if(destination.icon != null) {
                    NavigationBarItem(
                        selected = currentScreen.name == destination.name,
                        onClick = {
                            navController.navigate(route = destination.name)
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.title
                            )
                        },
                        label = { Text(destination.title) }
                    )
                }
            }
        }
    }

}


