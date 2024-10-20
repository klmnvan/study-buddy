package com.example.studybuddy.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = NavigationRoutes.SPLASH) {

        composable(NavigationRoutes.SPLASH){
            //Splash(controller)
        }
    }
}