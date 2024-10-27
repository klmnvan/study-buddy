package com.example.studybuddy.view.screens.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.studybuddy.domain.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context) : ViewModel() {

    fun launch(navController: NavHostController) {
        viewModelScope.launch {
            delay(2000L)
            navController.navigate(NavigationRoutes.AUTH) {
                popUpTo(NavigationRoutes.SPLASH) {
                    inclusive = true
                }
            }
        }
    }

}
