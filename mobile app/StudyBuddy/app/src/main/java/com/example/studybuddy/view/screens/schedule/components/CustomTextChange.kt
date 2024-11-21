package com.example.studybuddy.view.screens.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun RowScope.CustomTextChange(text: String, colorText: Color, back: Color){
    Box(
        modifier = Modifier
            .weight(0.5f)
            .background(back, RoundedCornerShape(5.dp))
            .fillMaxHeight()
            .padding(vertical = 15.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, style = StudyBuddyTheme.typography.bold,
            fontSize = 2.5.em,
            color = colorText, textAlign = TextAlign.Center
        )
    }
}