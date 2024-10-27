package com.example.studybuddy.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.studybuddy.domain.navigation.Navigation
import com.example.studybuddy.domain.CachedData
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CachedData.init(LocalContext.current)
            StudyBuddyTheme {
                Navigation()
            }
        }
    }
}
