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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.salle.ui.navigation.Activity
import com.example.salle.ui.navigation.RoutineEntry
import com.example.salle.ui.navigation.RoutineHome
import com.example.salle.ui.navigation.SalleNavGraph
import com.example.salle.ui.navigation.topLevelRoutes

@Composable
fun SalleApp(
    navController: NavHostController = rememberNavController()
) {
    val layoutDirection = LocalLayoutDirection.current
    val entry by navController.currentBackStackEntryAsState()
    val currentDestination = entry?.destination
    Scaffold(
        bottomBar = {
                BottomBar(
                    navController = navController
                )
        },
        floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(RoutineEntry) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Routine"
                    )
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

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets, modifier = modifier
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        topLevelRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                label = { Text(topLevelRoute.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }

}


//    if(currentScreen.showsBottomBar){
//        NavigationBar(
//            windowInsets = NavigationBarDefaults.windowInsets,
//            modifier = modifier
//        ) {
//            Routes.entries.forEachIndexed { index, destination ->
//
//                if(destination.icon != null) {
//                    NavigationBarItem(
//                        selected = currentScreen.name == destination.name,
//                        onClick = {
//                            navController.navigate(route = destination.name)
//                        },
//                        icon = {
//                            Icon(
//                                destination.icon,
//                                contentDescription = destination.title
//                            )
//                        },
//                        label = { Text(destination.title) }
//                    )
//                }
//            }
//        }
//    }




