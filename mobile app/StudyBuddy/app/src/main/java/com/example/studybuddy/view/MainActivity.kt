package com.example.studybuddy.view

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.R
import com.example.studybuddy.view.navigation.Navigation
import com.example.studybuddy.domain.repository.UserRepository
import com.example.studybuddy.view.generalcomponents.pullrefresh.CustomPullToRefreshContainer
import com.example.studybuddy.view.navigation.NavigationRoutes
import com.example.studybuddy.view.panels.bottombar.BottomBar
import com.example.studybuddy.view.panels.topbar.TopBar
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserRepository.init(LocalContext.current)
            val controller = rememberNavController()
            val barsIsVisible = remember { mutableStateOf(false) }
            val pullToRefreshState = rememberPullToRefreshState()
            val currentThemeMode = remember { mutableStateOf(UserRepository.themes.first { it.title == UserRepository.theme }) }
            StudyBuddyTheme(themeMode = currentThemeMode.value) {
                Scaffold(
                    topBar = {
                        if (barsIsVisible.value) {
                            TopBar(controller, currentThemeMode)
                        }
                    },
                    bottomBar = {
                        if (barsIsVisible.value) {
                            BottomBar(controller)
                        }
                    },
                    floatingActionButton = {
                        CustomPullToRefreshContainer(pullToRefreshState)
                    }
                ) {
                    Navigation(controller, barsIsVisible, pullToRefreshState, NavigationRoutes.SPLASH)
                }
            }
        }
    }
}
