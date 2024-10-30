package com.example.studybuddy.view.generalcomponents.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.studybuddy.R
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ButtonMore(onClick: () -> Unit) {
    Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_elipsis),
        contentDescription = null,
        tint = StudyBuddyTheme.colors.textButton,
        modifier = Modifier.background(StudyBuddyTheme.colors.secondary, RoundedCornerShape(20))
            .padding(6.dp).size(12.dp).clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        }
    )
}