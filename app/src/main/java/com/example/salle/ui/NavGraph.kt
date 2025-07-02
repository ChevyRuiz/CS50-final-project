package com.example.salle.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salle.data.Pages

@Composable
fun RoutinesNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Pages.Routines.name
    ){
        composable(route = Pages.Routines.name){
            RoutineScreen(
                onButtonClick = { navController.navigate(Pages.AddRoutines.name) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = Pages.AddRoutines.name){
            AddRoutineScreen(
                onBackClick = { navController.navigateUp() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}