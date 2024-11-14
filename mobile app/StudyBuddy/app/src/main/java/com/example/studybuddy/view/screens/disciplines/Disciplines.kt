package com.example.studybuddy.view.screens.disciplines

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.fragments.ShowFragment
import com.example.studybuddy.view.generalcomponents.icons.ButtonAdd
import com.example.studybuddy.view.generalcomponents.icons.ButtonBack
import com.example.studybuddy.view.generalcomponents.icons.ButtonModify
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.disciplines.components.DiscItem
import com.example.studybuddy.view.screens.disciplines.components.ModifyDiscItem
import com.example.studybuddy.view.screens.disciplines.components.ModifyTeacherItem
import com.example.studybuddy.view.screens.disciplines.components.ShowDiscItem
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Disciplines(controller: NavHostController, pullToRefreshState: PullToRefreshState, viewModel: DisciplinesViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    val show = remember { mutableStateOf(Pair(1, 0)) }
    val newDisc = remember { mutableStateOf(DisciplineEnt()) }

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
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp).padding(top = 65.dp, bottom = 100.dp)) {
            when(show.value.first) {
                1 -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextTitle("Предметы", 32.sp, StudyBuddyTheme.colors.textTitle)
                        Spacer(modifier = Modifier.weight(1f))
                        ButtonAdd {
                            show.value = Pair(2, 0)
                        }
                    }
                    SpacerHeight(32.dp)
                    state.value.disciplines.forEach { item ->
                        DiscItem(item) {
                            show.value = Pair(4, it)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                2 -> {
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                show.value = Pair(1, 0)
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
                                    if(it) show.value = Pair(1, 0)
                                    newDisc.value = DisciplineEnt()
                                }
                            }
                            SpacerHeight(12.dp)
                            ButtonFillMaxWidth("СПИСОК ПРЕПОДАВАТЕЛЕЙ", StudyBuddyTheme.colors.primary, true) {
                                show.value = Pair(3, 0)
                            }
                        }
                    }
                }
                3 -> {
                    SpacerHeight(height = 16.dp)
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                if(show.value.second != 0) show.value = Pair(5, show.value.second)
                                else show.value = Pair(2, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            TextTitle(text = "Список преподавателей", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ModifyTeacherItem(state, viewModel)
                        SpacerHeight(height = 24.dp)
                        ButtonFillMaxWidth("ВЕРНУТЬСЯ К ПРЕДМЕТУ", StudyBuddyTheme.colors.primary, true) {
                            if(show.value.second != 0) show.value = Pair(5, show.value.second)
                            else show.value = Pair(2, 0)
                        }
                    }
                }
                4 -> {
                    SpacerHeight(height = 16.dp)
                    Column {
                        val disc = state.value.disciplines.first { it.idDiscipline == show.value.second }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                show.value = Pair(1, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            Text(modifier = Modifier.horizontalScroll(rememberScrollState()),
                                text = disc.title, style = StudyBuddyTheme.typography.regular,
                                fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle,
                                maxLines = 1
                            )
                        }
                        SpacerHeight(20.dp)
                        viewModel.stateValue = state.value.copy(requirementsByDisc = state.value.requirements.filter { it.idDiscipline == disc.idDiscipline })
                        ShowDiscItem(disc, state.value.teachers.firstOrNull { it.idTeacher == disc.idTeacher }, state, viewModel )
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
                                    ShowFragment("Подтвердите", "Вы точно хотите удалить этот предмет без возможности возвращения?") {
                                        showDialog = false
                                        if(it) {
                                            viewModel.deleteDiscs(state.value.disciplines.first { it.idDiscipline == disc.idDiscipline }) {
                                                if(it) show.value = Pair(1, 0)
                                            }
                                        }
                                    }
                                }
                            }
                            SpacerWidth(12.dp)
                            ButtonModify {
                                show.value = Pair(5, show.value.second)
                            }
                        }
                    }
                }
                5 -> {
                    Column {
                        val updateDisc = remember {
                            mutableStateOf(state.value.disciplines.first { it.idDiscipline == show.value.second })
                        }
                        SpacerHeight(height = 16.dp)
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                ButtonBack {
                                    show.value = Pair(4, show.value.second)
                                }
                                SpacerWidth(width = 12.dp)
                                TextTitle(text = "Редактирование", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                            }
                            SpacerHeight(20.dp)
                            ModifyDiscItem(updateDisc, state)
                            SpacerHeight(height = 24.dp)
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)) {
                                ButtonFillMaxWidth("СОХРАНИТЬ", StudyBuddyTheme.colors.secondary, true) {
                                    viewModel.updateDisc(updateDisc.value) {
                                        if(it) show.value = Pair(4, show.value.second)
                                    }
                                }
                                SpacerHeight(12.dp)
                                ButtonFillMaxWidth("СПИСОК ПРЕПОДАВАТЕЛЕЙ", StudyBuddyTheme.colors.primary, true) {
                                    show.value = Pair(3, show.value.second)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}