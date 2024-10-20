package com.example.studybuddy.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studybuddy.domain.navigation.Navigation
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyBuddyTheme {
                Navigation()
            }
        }
    }
}
