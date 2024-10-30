package com.example.studybuddy.view.screens.tasks.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextLight
import com.example.studybuddy.view.screens.tasks.TasksViewModel
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun PageSectionTask(title: String, listItem: List<TaskEnt>, viewModel: TasksViewModel, expandedValue: Boolean, disciplines: List<DisciplineEnt>, onClickItem: (el: TaskEnt) -> Unit, expanded: (Boolean) -> Unit) {
    Column {
        Row(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded(!expandedValue)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                TextLight(title, 20.sp, StudyBuddyTheme.colors.primary)
            }
            SpacerWidth(12.dp)
            Icon(
                modifier = Modifier
                    .size(15.dp)
                    .rotate(if (expandedValue) 180f else 0F),
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.textTitle
            )
        }
        AnimatedVisibility(
            visible = expandedValue,
            enter = expandVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
            exit = shrinkVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh))
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                listItem.forEach { task ->
                    TaskListItem(task, viewModel, disciplines, onClickItem)
                }
            }
        }
    }
}

