package com.example.studybuddy.view.screens.tasks

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.domain.navigation.NavigationRoutes
import com.example.studybuddy.view.generalcomponents.icons.ButtonAdd
import com.example.studybuddy.view.generalcomponents.icons.ButtonBack
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.tasks.components.PageSectionTask
import com.example.studybuddy.view.screens.tasks.components.ShowTaskItem
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasks(controller: NavHostController, pullToRefreshState: PullToRefreshState, viewModel: TasksViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    val showTask = remember {
        mutableStateOf(Pair(false, TaskEnt(0, "","","","",null, false)))
    }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.fetchTasks()
            delay(1000)
            pullToRefreshState.endRefresh()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.launch()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(StudyBuddyTheme.colors.background)
        .nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp, bottom = 100.dp)) {
            Log.d("view", showTask.value.first.toString())
            if(!showTask.value.first) {
                val expandedStates = remember { mutableStateListOf(true, true) }
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
                PageSectionTask("Не готовы", state.value.tasks.filter { !it.isCompleted }, viewModel, expandedStates[0], state.value.disciplines, {
                    showTask.value = Pair(true, it)
                }) {
                    expandedStates[0] = it
                }
                PageSectionTask("Готовы", state.value.tasks.filter { it.isCompleted }, viewModel, expandedStates[1], state.value.disciplines, {
                    showTask.value = Pair(true, it)
                }) {
                    expandedStates[1] = it
                }
            }
            else {
                SpacerHeight(height = 16.dp)
                Column {
                    Row {
                        ButtonBack {
                            showTask.value = Pair(false, state.value.tasks.first())
                        }
                        SpacerWidth(width = 12.dp)
                        TextTitle(text = "Детали", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                    }
                    SpacerHeight(20.dp)
                    ShowTaskItem(el = showTask.value.second)
                }

            }
        }
    }
}
