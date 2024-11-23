package com.example.studybuddy.view.generalcomponents.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.studybuddy.R
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ButtonFilter(filterIsOpen: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier.background(StudyBuddyTheme.colors.third, RoundedCornerShape(10))
        .padding(16.dp).size(20.dp), contentAlignment = Alignment.Center) {
        Icon(imageVector = if(!filterIsOpen) ImageVector.vectorResource(R.drawable.icon_filter)
        else ImageVector.vectorResource(R.drawable.icon_krest),
            contentDescription = null,
            tint = StudyBuddyTheme.colors.textButton,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
        )
    }
}