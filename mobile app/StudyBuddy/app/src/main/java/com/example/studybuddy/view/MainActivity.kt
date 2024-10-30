package com.example.studybuddy.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.domain.navigation.Navigation
import com.example.studybuddy.domain.UserRepository
import com.example.studybuddy.view.generalcomponents.pullrefresh.CustomPullToRefreshContainer
import com.example.studybuddy.view.panels.bottombar.BottomBar
import com.example.studybuddy.view.panels.topbar.TopBar
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                    Navigation(controller, barsIsVisible, pullToRefreshState)
                }
            }
        }
    }
}
