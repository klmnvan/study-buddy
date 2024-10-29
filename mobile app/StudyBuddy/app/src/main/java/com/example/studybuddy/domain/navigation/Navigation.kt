package com.example.studybuddy.domain.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studybuddy.view.screens.auth.Auth
import com.example.studybuddy.view.screens.login.Login
import com.example.studybuddy.view.screens.register.Register
import com.example.studybuddy.view.screens.splash.Splash
import com.example.studybuddy.view.screens.tasks.Tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(controller: NavHostController, barsIsVisible: MutableState<Boolean>, pullToRefreshState: PullToRefreshState) {
    NavHost(
        navController = controller,
        startDestination = NavigationRoutes.SPLASH,
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
            //Tasks(controller)
        }

        composable(NavigationRoutes.EXAMS) {
            //Tasks(controller)
        }

        composable(NavigationRoutes.SCHEDULES) {
            //Tasks(controller)
        }

    }
}