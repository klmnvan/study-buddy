package com.example.studybuddy.view.screens.tasks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.view.navigation.NavigationRoutes
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.fragments.ShowFragment
import com.example.studybuddy.view.generalcomponents.icons.ButtonAdd
import com.example.studybuddy.view.generalcomponents.icons.ButtonBack
import com.example.studybuddy.view.generalcomponents.icons.ButtonModify
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.tasks.components.ModifyTaskItem
import com.example.studybuddy.view.screens.tasks.components.PageSectionTask
import com.example.studybuddy.view.screens.tasks.components.ShowTaskItem
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasks(controller: NavHostController, pullToRefreshState: PullToRefreshState, viewModel: TasksViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    val show = remember {
        mutableStateOf(Pair(1, 0))
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
            .padding(top = 65.dp, bottom = 100.dp)) {
            Log.d("view", show.value.first.toString())
            when(show.value.first) {
                1 -> {
                    val expandedStates = remember { mutableStateListOf(true, true) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextTitle("Задания", 32.sp, StudyBuddyTheme.colors.textTitle)
                        Spacer(modifier = Modifier.weight(1f))
                        ButtonAdd {
                            show.value = Pair(4, 0)
                        }
                    }
                    SpacerHeight(8.dp)
                    PageSectionTask("Не готовы", state.value.tasks.filter { !it.isCompleted }, viewModel, expandedStates[0], state.value.disciplines, {
                        show.value = Pair(2, it.idTask)
                    }) {
                        expandedStates[0] = it
                    }
                    PageSectionTask("Готовы", state.value.tasks.filter { it.isCompleted }, viewModel, expandedStates[1], state.value.disciplines, {
                        show.value = Pair(2, it.idTask)
                    }) {
                        expandedStates[1] = it
                    }
                }
                2 -> {
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                show.value = Pair(1, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            TextTitle(text = "Детали", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ShowTaskItem(el = state.value.tasks.first { it.idTask == show.value.second })
                        SpacerHeight(height = 24.dp)
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)) {
                            Row(modifier = Modifier.weight(1f)) {
                                var showDialog by remember { mutableStateOf(false) }
                                ButtonFillMaxWidth("УДАЛИТЬ", StudyBuddyTheme.colors.primary, true) {
                                    showDialog = true
                                }
                                if (showDialog) {
                                    ShowFragment("Подтвердите", "Вы точно хотите удалить эту задачу без возможности возвращаения?") {
                                        showDialog = false
                                        if(it) {
                                            viewModel.deleteTask(state.value.tasks.first { it.idTask == show.value.second }) {
                                                if(it) show.value = Pair(1, 0)
                                            }
                                        }
                                    }
                                }
                            }
                            SpacerWidth(12.dp)
                            ButtonModify {
                                show.value = Pair(3,  show.value.second)
                            }
                        }
                    }
                }
                3 -> {
                    val updatedTask = remember {
                        mutableStateOf(state.value.tasks.first { it.idTask == show.value.second })
                    }
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                show.value = Pair(2, show.value.second)
                            }
                            SpacerWidth(width = 12.dp)
                            TextTitle(text = "Редактирование", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ModifyTaskItem(updatedTask, state)
                        SpacerHeight(height = 24.dp)
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)) {
                            ButtonFillMaxWidth("СОХРАНИТЬ", StudyBuddyTheme.colors.secondary, true) {
                                viewModel.updateTask(updatedTask.value) {
                                    if(it) show.value = Pair(1, 0)
                                }
                            }
                            SpacerHeight(12.dp)
                            ButtonFillMaxWidth("СПИСОК ПРЕДМЕТОВ", StudyBuddyTheme.colors.primary, true) {
                                controller.navigate(NavigationRoutes.DISCIPLINES) {
                                    popUpTo(NavigationRoutes.TASKS) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
                4 -> {
                    val newTask = remember {
                        mutableStateOf(TaskEnt())
                    }
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                show.value = Pair(1, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            TextTitle(text = "Новое задание", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ModifyTaskItem(newTask, state)
                        SpacerHeight(height = 24.dp)
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)) {
                            ButtonFillMaxWidth("СОЗДАТЬ", StudyBuddyTheme.colors.secondary, true) {
                                viewModel.createTask(newTask.value) {
                                    if(it) show.value = Pair(1, 0)
                                }
                            }
                            SpacerHeight(12.dp)
                            ButtonFillMaxWidth("СПИСОК ПРЕДМЕТОВ", StudyBuddyTheme.colors.primary, true) {
                                controller.navigate(NavigationRoutes.DISCIPLINES) {
                                    popUpTo(NavigationRoutes.TASKS) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
