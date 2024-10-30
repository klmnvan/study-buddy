package com.example.studybuddy.view.generalcomponents.texts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun PrimaryTextViewer(text: String) {
    Box(modifier = Modifier
        .background(StudyBuddyTheme.colors.containerPrimary)
        .padding(12.dp)) {
        TextThin(text, 12.sp, StudyBuddyTheme.colors.textTitle)
    }
}

@Composable
fun DateTextViewer(date: String) {
    Box(modifier = Modifier
        .background(StudyBuddyTheme.colors.containerSecondary, RoundedCornerShape(5.dp))
        .padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_clock),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.secondary,
                modifier = Modifier.size(20.dp)
            )
            SpacerWidth(width = 8.dp)
            TextBold(date, 12.sp, StudyBuddyTheme.colors.secondary)
        }
    }
}