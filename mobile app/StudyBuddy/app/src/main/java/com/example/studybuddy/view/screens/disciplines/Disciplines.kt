package com.example.studybuddy.view.screens.disciplines

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.icons.ButtonAdd
import com.example.studybuddy.view.generalcomponents.icons.ButtonBack
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.navigation.NavigationRoutes
import com.example.studybuddy.view.screens.disciplines.components.DiscItem
import com.example.studybuddy.view.screens.disciplines.components.ModifyDiscItem
import com.example.studybuddy.view.screens.disciplines.components.ModifyTeacherItem
import com.example.studybuddy.view.screens.tasks.components.ModifyTaskItem
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Disciplines(controller: NavHostController, pullToRefreshState: PullToRefreshState, viewModel: DisciplinesViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    val showDisc = remember {
        mutableStateOf(Pair(1, 0))
    }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.fetch()
            delay(1000)
            pullToRefreshState.endRefresh()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.launch()
    }

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background).nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp).padding(top = 60.dp, bottom = 100.dp)) {
            when(showDisc.value.first) {
                1 -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextTitle("Предметы", 32.sp, StudyBuddyTheme.colors.textTitle)
                        Spacer(modifier = Modifier.weight(1f))
                        ButtonAdd {
                            showDisc.value = Pair(2, 0)
                        }
                    }
                    SpacerHeight(32.dp)
                    if(state.value.disciplines.isNotEmpty()){
                        state.value.disciplines.forEach { item ->
                            DiscItem(item)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                2 -> {
                    val newDisc = remember {
                        mutableStateOf(DisciplineEnt())
                    }
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                showDisc.value = Pair(1, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            TextTitle(text = "Новый предмет", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ModifyDiscItem(newDisc, state)
                        SpacerHeight(height = 24.dp)
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)) {
                            ButtonFillMaxWidth("СОЗДАТЬ", StudyBuddyTheme.colors.secondary, true) {
                                viewModel.createDisc(newDisc.value) {
                                    if(it) showDisc.value = Pair(1, 0)
                                }
                            }
                            SpacerHeight(12.dp)
                            ButtonFillMaxWidth("СПИСОК ПРЕПОДАВАТЕЛЕЙ", StudyBuddyTheme.colors.primary, true) {
                                showDisc.value = Pair(3, 0)
                            }
                        }
                    }
                }
                3 -> {
                    val newDisc = remember {
                        mutableStateOf(DisciplineEnt())
                    }
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                showDisc.value = Pair(1, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            TextTitle(text = "Список преподавателей", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ModifyTeacherItem(state, viewModel)
                        SpacerHeight(height = 24.dp)
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)) {
                            ButtonFillMaxWidth("СОЗДАТЬ", StudyBuddyTheme.colors.secondary, true) {
                                viewModel.createDisc(newDisc.value) {
                                    if(it) showDisc.value = Pair(1, 0)
                                }
                            }
                            SpacerHeight(12.dp)
                            ButtonFillMaxWidth("ВЕРНУТЬСЯ К ПРЕДМЕТУ", StudyBuddyTheme.colors.primary, true) {
                                showDisc.value = Pair(2, 0)
                            }
                        }
                    }
                }
            }
        }
    }

}