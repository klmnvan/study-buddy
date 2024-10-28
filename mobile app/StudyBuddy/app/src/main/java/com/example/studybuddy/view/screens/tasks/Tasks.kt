package com.example.studybuddy.view.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachReversed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.view.components.ButtonAdd
import com.example.studybuddy.view.components.SpacerHeight
import com.example.studybuddy.view.components.TextTitle
import com.example.studybuddy.view.screens.tasks.components.PageSection
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun Tasks(controller: NavHostController, viewModel: TasksViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.launch()
    }

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background)) {
        val expandedStates = remember { mutableStateListOf(true, true) }
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
            SpacerHeight(20.dp)
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
            PageSection("Не готовы", state.value.tasks.filter { !it.isCompleted }, viewModel, expandedStates[0], state.value.disciplines) {
                expandedStates[0] = it
            }
            PageSection("Готовы", state.value.tasks.filter { it.isCompleted }, viewModel, expandedStates[1], state.value.disciplines) {
                expandedStates[1] = it
            }
        }
    }
}
