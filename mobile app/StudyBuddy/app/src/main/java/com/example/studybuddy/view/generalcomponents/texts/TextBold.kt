package com.example.studybuddy.view.generalcomponents.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun TextBold(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.bold, fontSize = fontSize, color = color)
}