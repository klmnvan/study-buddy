package com.example.studybuddy.view.generalcomponents.texts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
fun NumberTicketsViewer(number: Int?, ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_tickets),
            contentDescription = null,
            tint = StudyBuddyTheme.colors.secondary,
            modifier = Modifier.size(30.dp)
        )
        SpacerWidth(width = 4.dp)
        TextLight(text = ": ${number} билетов", fontSize = 16.sp, color = StudyBuddyTheme.colors.primary)
    }
}