package com.example.studybuddy.view.components



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun TextTitle(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.regular, fontSize = fontSize, color = color)
}

@Composable
fun TextDesc(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.exstralight, fontSize = fontSize, color = color)
}
