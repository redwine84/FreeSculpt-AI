package com.freesculpt.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FreeSculptApp() {
    val navController = rememberNavController()
    AppNavHost(navController)
}
