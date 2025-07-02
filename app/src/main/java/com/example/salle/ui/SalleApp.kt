package com.example.salle.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.salle.data.Pages

@Composable
fun SalleApp(
    viewModel: SalleViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val salleUiState by viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        bottomBar = {
                BottomBar(
                    navController = navController
                )
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
    val startDestination = Pages.Routines
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

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
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.name)
                            selectedDestination = index
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


