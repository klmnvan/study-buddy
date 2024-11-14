package com.example.studybuddy.view.generalcomponents.texts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun PrimaryTextViewer(text: String) {
    Box(modifier = Modifier
        .background(StudyBuddyTheme.colors.containerPrimary)
        .padding(12.dp)) {
        TextThin(text, 12.sp, StudyBuddyTheme.colors.textTitle)
    }
}

