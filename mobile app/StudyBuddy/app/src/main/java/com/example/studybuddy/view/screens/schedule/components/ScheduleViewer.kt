package com.example.studybuddy.view.screens.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.studybuddy.data.states.ScheduleSt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ScheduleViewer(state: State<ScheduleSt>) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .weight(0.4f)
                .background(StudyBuddyTheme.colors.containerSecondary, RoundedCornerShape(5.dp))
                .fillMaxHeight()
                .padding(vertical = 15.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Пара", style = StudyBuddyTheme.typography.bold,
                fontSize = 2.5.em,
                color = StudyBuddyTheme.colors.secondary, textAlign = TextAlign.Center
            )
        }
        SpacerWidth(8.dp)
        Box(
            modifier = Modifier
                .weight(0.5f)
                .background(StudyBuddyTheme.colors.containerSecondary, RoundedCornerShape(5.dp))
                .fillMaxHeight()
                .padding(vertical = 15.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Основное расписание", style = StudyBuddyTheme.typography.bold,
                fontSize = 2.5.em,
                color = StudyBuddyTheme.colors.secondary, textAlign = TextAlign.Center
            )
        }
        SpacerWidth(8.dp)
        Box(
            modifier = Modifier
                .weight(0.5f)
                .background(StudyBuddyTheme.colors.containerSecondary, RoundedCornerShape(5.dp))
                .fillMaxHeight()
                .padding(vertical = 15.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Изменения", style = StudyBuddyTheme.typography.bold,
                fontSize = 2.5.em,
                color = StudyBuddyTheme.colors.secondary, textAlign = TextAlign.Center
            )
        }
    }
    SpacerHeight(8.dp)
    with(state.value.valuesSchedule.result) {
        bell.forEach { itemBell ->
            val listMain = main.filter { it.para == itemBell.para }
            val listChange = change.filter { it.para == itemBell.para }
            ItemRowSchedule(
                time = "${itemBell.para}\n${itemBell.start_time}-${itemBell.end_time}",
                listMain,
                listChange
            )
            SpacerHeight(8.dp)
        }
    }
}