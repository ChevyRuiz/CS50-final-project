package com.example.salle.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.salle.data.local.NestedGraph
import com.example.salle.data.local.Pages
import com.example.salle.ui.activity.ActivityScreen
import com.example.salle.ui.routine.RoutineEntryScreen
import com.example.salle.ui.routine.RoutineScreen


@Composable
fun SalleNavGraph(
    navController: NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = NestedGraph.Nested.name,
        modifier = modifier
    ){
        navigation(
            route = NestedGraph.Nested.name,
            startDestination = Pages.Routines.name
        ){
            composable(route = Pages.Routines.name){
                RoutineScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Pages.AddRoutines.name){
                RoutineEntryScreen(
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        composable(route = Pages.Activity.name) {
            ActivityScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
