package com.example.studybuddy.view.screens.exams

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
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.domain.converters.StringToLocalDate
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.fragments.ShowFragment
import com.example.studybuddy.view.generalcomponents.icons.ButtonAdd
import com.example.studybuddy.view.generalcomponents.icons.ButtonBack
import com.example.studybuddy.view.generalcomponents.icons.ButtonModify
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.disciplines.components.ModifyDiscItem
import com.example.studybuddy.view.screens.disciplines.components.ModifyTeacherItem
import com.example.studybuddy.view.screens.disciplines.components.ShowDiscItem
import com.example.studybuddy.view.screens.exams.components.ModifyExamItem
import com.example.studybuddy.view.screens.exams.components.PageSectionExam
import com.example.studybuddy.view.screens.exams.components.ShowExamItem
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import kotlinx.coroutines.delay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exams(controller: NavHostController, pullToRefreshState: PullToRefreshState, viewModel: ExamsViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    val show = remember { mutableStateOf(Pair(1, 0)) }
    val newExam = remember { mutableStateOf(ExamEnt()) }

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
        val expandedStates = remember { mutableStateListOf(true, true) }
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp).padding(top = 60.dp, bottom = 100.dp)) {
            when(show.value.first) {
                1 -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextTitle("Экзамены", 32.sp, StudyBuddyTheme.colors.textTitle)
                        Spacer(modifier = Modifier.weight(1f))
                        ButtonAdd {
                            show.value = Pair(2, 0)
                        }
                    }
                    SpacerHeight(8.dp)
                    PageSectionExam ("Предстоящие", state.value.exams.filter { StringToLocalDate(it.dateExam)!! > LocalDate.now() }, expandedStates[0],
                        { show.value = Pair(3, it.idExam) }) {
                        expandedStates[0] = it
                    }
                    PageSectionExam ("Прошедшие", state.value.exams.filter { StringToLocalDate(it.dateExam)!! < LocalDate.now() }, expandedStates[1],
                        { show.value = Pair(3, it.idExam) }) {
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
                            TextTitle(text = "Новый экзамен", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                        }
                        SpacerHeight(20.dp)
                        ModifyExamItem(newExam, state)
                        SpacerHeight(height = 24.dp)
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)) {
                            ButtonFillMaxWidth("СОЗДАТЬ", StudyBuddyTheme.colors.secondary, true) {
                                viewModel.createExam(newExam.value) {
                                    if(it) show.value = Pair(1, 0)
                                    newExam.value = ExamEnt()
                                }
                            }
                            SpacerHeight(12.dp)
                        }
                    }
                }
                3 -> {
                    SpacerHeight(height = 16.dp)
                    Column {
                        val exam = state.value.exams.first { it.idExam == show.value.second }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonBack {
                                viewModel.stateValue = state.value.copy(notesByExam = listOf())
                                show.value = Pair(1, 0)
                            }
                            SpacerWidth(width = 12.dp)
                            Text(modifier = Modifier.horizontalScroll(rememberScrollState()),
                                text = exam.title, style = StudyBuddyTheme.typography.regular,
                                fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle,
                                maxLines = 1
                            )
                        }
                        SpacerHeight(20.dp)
                        viewModel.stateValue = state.value.copy(notesByExam = state.value.notes.filter { it.idExam == exam.idExam })
                        ShowExamItem(exam, state, viewModel )
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
                                    ShowFragment("Подтвердите", "Вы точно хотите удалить этот экзамен без возможности возвращения?") {
                                        showDialog = false
                                        if(it) {
                                            viewModel.deleteExam(state.value.exams.first { it.idExam == exam.idExam }) {
                                                viewModel.stateValue = state.value.copy(notesByExam = listOf())
                                                if(it) show.value = Pair(1, 0)
                                            }
                                        }
                                    }
                                }
                            }
                            SpacerWidth(12.dp)
                            ButtonModify {
                                show.value = Pair(4, show.value.second)
                            }
                        }
                    }
                }
                4 -> {
                    Column {
                        val updateExam = remember {
                            mutableStateOf(state.value.exams.first { it.idExam == show.value.second })
                        }
                        SpacerHeight(height = 16.dp)
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                ButtonBack {
                                    show.value = Pair(3, show.value.second)
                                }
                                SpacerWidth(width = 12.dp)
                                TextTitle(text = "Редактирование", fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
                            }
                            SpacerHeight(20.dp)
                            ModifyExamItem(updateExam, state)
                            SpacerHeight(height = 24.dp)
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)) {
                                ButtonFillMaxWidth("СОХРАНИТЬ", StudyBuddyTheme.colors.secondary, true) {
                                    viewModel.updateExam(updateExam.value) {
                                        if(it) show.value = Pair(3, show.value.second)
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