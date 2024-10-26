package com.example.studybuddy.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.view.screens.auth.Auth
import com.example.studybuddy.view.screens.login.Login
import com.example.studybuddy.view.screens.register.Register
import com.example.studybuddy.view.screens.splash.Splash
import com.example.studybuddy.view.screens.tasks.Tasks

@Composable
fun Navigation() {
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = NavigationRoutes.TASKS) {

        composable(NavigationRoutes.SPLASH) {
            Splash(controller)
        }

        composable(NavigationRoutes.AUTH){
            Auth(controller)
        }

        composable(NavigationRoutes.LOGIN){
            Login(controller)
        }

        composable(NavigationRoutes.REGIST){
            Register(controller)
        }

        composable(NavigationRoutes.TASKS){
            Tasks(controller)
        }

    }
}