package com.example.salle.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.salle.data.NestedGraph
import com.example.salle.data.Pages


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
                    onButtonClick = { navController.navigate(Pages.AddRoutines.name)},
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Pages.AddRoutines.name){
                AddRoutineScreen(
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
