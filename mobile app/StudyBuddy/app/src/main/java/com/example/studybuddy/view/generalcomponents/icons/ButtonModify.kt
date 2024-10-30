package com.example.studybuddy.view.generalcomponents.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
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
fun ButtonModify(onClick: () -> Unit) {
    Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_modify),
        contentDescription = null,
        tint = StudyBuddyTheme.colors.secondary,
        modifier = Modifier.fillMaxHeight(1f).aspectRatio(1f)
            .background(StudyBuddyTheme.colors.containerSecondary, RoundedCornerShape(10))
            .padding(12.dp).clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        }
    )
}