package com.freesculpt.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Workbench : Screen("workbench")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onOpenWorkspace = { navController.navigate(Screen.Workbench.route) }
            )
        }

        composable(Screen.Workbench.route) {
            WorkbenchScreen(
                onBackHome = { navController.popBackStack() }
            )
        }
    }
}
