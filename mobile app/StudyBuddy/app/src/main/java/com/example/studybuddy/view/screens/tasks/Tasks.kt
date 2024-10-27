package com.example.studybuddy.view.screens.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.view.components.ButtonAdd
import com.example.studybuddy.view.components.SpacerHeight
import com.example.studybuddy.view.components.SpacerWidth
import com.example.studybuddy.view.components.TextDesc
import com.example.studybuddy.view.components.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun Tasks(controller: NavHostController, viewModel: TasksViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background)) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp, horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitle("Задания", 32.sp, StudyBuddyTheme.colors.textTitle)
                Spacer(modifier = Modifier.weight(1f))
                ButtonAdd {

                }
            }
            SpacerHeight(8.dp)
            PageSection("Не готовы", state.value.tasks.filter { !it.isCompleted }.toList())
            SpacerHeight(16.dp)
            PageSection("Готовы", state.value.tasks.filter { it.isCompleted }.toList())
        }
    }
}

@Composable
fun PageSection(title: String, listItem: List<TaskEnt>) {
    var expanded by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            expanded = !expanded
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            TextDesc(title, 20.sp, StudyBuddyTheme.colors.primary)
        }
        SpacerWidth(12.dp)
        Icon(
            modifier = Modifier
                .size(15.dp)
                .rotate(if (expanded) 180f else 0F),
            imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
            contentDescription = null,
            tint = StudyBuddyTheme.colors.textTitle
        )
    }
    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
        exit = shrinkVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh))
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(listItem.toString())
        }
    }
}
