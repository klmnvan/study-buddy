package com.example.studybuddy.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.view.screens.auth.Auth
import com.example.studybuddy.view.screens.splash.Splash

@Composable
fun Navigation() {
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = NavigationRoutes.SPLASH) {

        composable(NavigationRoutes.SPLASH) {
            Splash(controller)
        }

        composable(NavigationRoutes.AUTH){
            Auth(controller)
        }
    }
}