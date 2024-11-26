package com.example.studybuddy.view.screens.exams.components

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
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.states.ExamsSt
import com.example.studybuddy.view.generalcomponents.buttons.ButtonDatePicker
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.textfields.PrimaryTextField
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ModifyExamItem(updatedExam: MutableState<ExamEnt>, state: State<ExamsSt>) {
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
            PrimaryTextField(value = updatedExam.value.title, placeholder = "1 модуль", 1) {
                updatedExam.value = updatedExam.value.copy(title = it)
            }
            SpacerHeight(height = 12.dp)
            TextTitle(
                text = "Длительность",
                fontSize = 18.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(height = 4.dp)
            PrimaryTextField(value = updatedExam.value.duration, placeholder = "3 часа", 1) {
                updatedExam.value = updatedExam.value.copy(duration = it)
            }
            SpacerHeight(height = 12.dp)
            TextTitle(
                text = "Количество билетов",
                fontSize = 18.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(height = 4.dp)
            PrimaryTextField(
                value = if (updatedExam.value.numberTickets != 0) updatedExam.value.numberTickets.toString() else "",
                placeholder = "0",
                1
            ) {
                val regex = Regex("[1-9]\\d*")
                if (it == "" || it == "0") updatedExam.value = updatedExam.value.copy(numberTickets = 0)
                else if (regex.matches(it) && it.length < 10)
                    updatedExam.value = updatedExam.value.copy(numberTickets = it.toInt())
            }
            SpacerHeight(height = 12.dp)
            ButtonDatePicker("Дата экзамена: ", updatedExam.value.dateExam) {
                updatedExam.value = updatedExam.value.copy(dateExam = it)
            }
            SpacerHeight(height = 8.dp)
        }
    }
}