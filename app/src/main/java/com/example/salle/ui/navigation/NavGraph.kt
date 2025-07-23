package com.example.salle.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.salle.ui.activity.ActivityScreen
import com.example.salle.ui.routine.RoutineEditScreen
import com.example.salle.ui.routine.RoutineEntryScreen
import com.example.salle.ui.routine.RoutinePlayScreen
import com.example.salle.ui.routine.RoutineScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SalleNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RoutinesNestedGraph,
        modifier = modifier
    ) {
        navigation<RoutinesNestedGraph>(startDestination = RoutineHome) {
            composable<RoutineHome> {
                RoutineScreen(
                    onPlayClick = { id ->
                        navController.navigate(route = RoutinePlay(id))
                    },
                    onEditClick = { id ->
                        navController.navigate(route = RoutineEdit(id))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable<RoutineEntry> {
                RoutineEntryScreen(
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<RoutineEdit> {
                RoutineEditScreen(
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable<RoutinePlay> {
                RoutinePlayScreen(
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        navigation<ActivitiesNestedGraph>(startDestination = Activity){
            composable<Activity> {
                ActivityScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
