package com.example.studybuddy.view.screens.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.states.TasksSt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.textfields.PrimaryTextField
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ModifyTaskItem(updatedTask: MutableState<TaskEnt>, state: State<TasksSt>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, shape = RoundedCornerShape(5), spotColor = Color(
                    Color.Black.value
                )
            )
            .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(5.dp))
            .padding(top = 10.dp)
            .background(
                StudyBuddyTheme.colors.containerDefault,
                RoundedCornerShape(0.dp, 0.dp, 5.dp, 5.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, top = 10.dp)
                .padding(horizontal = 20.dp)
        ) {
            TextTitle(
                text = "Заголовок",
                fontSize = 18.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(height = 4.dp)
            PrimaryTextField(value = updatedTask.value.title, placeholder = "Название задачи", 1) {
                updatedTask.value = updatedTask.value.copy(title = it)
            }
            SpacerHeight(height = 12.dp)
            DropDownMenuDisc(
                state.value.disciplines.find { it.idDiscipline == updatedTask.value.idDiscipline }?.title
                    ?: "Не выбрано", state.value.disciplines.toMutableList(), "Предмет"
            ) {
                updatedTask.value = updatedTask.value.copy(idDiscipline = it)
                if (it == 0) updatedTask.value = updatedTask.value.copy(idDiscipline = null)
            }
            SpacerHeight(height = 12.dp)
            TextTitle(text = "Описание", fontSize = 18.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(height = 4.dp)
            PrimaryTextField(
                value = updatedTask.value.description,
                placeholder = "Описание задачи",
                6
            ) {
                updatedTask.value = updatedTask.value.copy(description = it)
            }
            SpacerHeight(height = 8.dp)
        }
    }
}