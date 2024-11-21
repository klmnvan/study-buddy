package com.example.studybuddy.view.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studybuddy.view.screens.auth.Auth
import com.example.studybuddy.view.screens.disciplines.Disciplines
import com.example.studybuddy.view.screens.exams.Exams
import com.example.studybuddy.view.screens.login.Login
import com.example.studybuddy.view.screens.register.Register
import com.example.studybuddy.view.screens.schedule.Schedule
import com.example.studybuddy.view.screens.splash.Splash
import com.example.studybuddy.view.screens.tasks.Tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(controller: NavHostController, barsIsVisible: MutableState<Boolean>, pullToRefreshState: PullToRefreshState) {
    NavHost(
        navController = controller,
        startDestination = NavigationRoutes.TASKS,
        enterTransition = { fadeIn(animationSpec = tween(durationMillis = 0)) } ) {

        composable(NavigationRoutes.SPLASH) {
            Splash(controller)
        }

        composable(NavigationRoutes.AUTH) {
            barsIsVisible.value = false
            Auth(controller)
        }

        composable(NavigationRoutes.LOGIN) {
            Login(controller)
        }

        composable(NavigationRoutes.REGIST) {
            Register(controller)
        }

        composable(NavigationRoutes.TASKS) {
            Tasks(controller, pullToRefreshState)
            barsIsVisible.value = true
        }

        composable(NavigationRoutes.DISCIPLINES) {
            Disciplines(controller, pullToRefreshState)
            barsIsVisible.value = true
        }

        composable(NavigationRoutes.EXAMS) {
            Exams(controller, pullToRefreshState)
            barsIsVisible.value = true
        }

        composable(NavigationRoutes.SCHEDULE) {
            Schedule(pullToRefreshState)
            barsIsVisible.value = true
        }

    }
}