package com.example.studybuddy.view.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.data.modelsitreshalo.Group
import com.example.studybuddy.view.generalcomponents.buttons.ButtonDatePicker
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.disciplines.components.DropDownMenuTeacher
import com.example.studybuddy.view.screens.schedule.components.DropDownMenuGroup
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Schedule(controller: NavHostController, pullToRefreshState: PullToRefreshState, viewModel: ScheduleViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSchedule()
    }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getValues()
            delay(1000)
            pullToRefreshState.endRefresh()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background)
        .nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp).padding(top = 60.dp, bottom = 100.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextTitle("Расписание", 32.sp, StudyBuddyTheme.colors.textTitle)
            }
            SpacerHeight(24.dp)
            DropDownMenuGroup (
                state.value.selGroup.name
                    ?: "Не выбрано", state.value.groups.toMutableList(), "Группа"
            ) {
                viewModel.stateValue = viewModel.stateValue.copy(selGroup = it)
                if (it.id == 0) viewModel.stateValue = viewModel.stateValue.copy(selGroup = Group(id = 0, name = "Не выбрано"))
            }
            SpacerHeight(12.dp)
            val date = remember { mutableStateOf("") }
            ButtonDatePicker("Дата: ", date.value) {
                date.value = it
            }
            SpacerHeight(18.dp)
            ButtonFillMaxWidth(text = "показать", color = StudyBuddyTheme.colors.primary) {
                
            }
        }
    }

}