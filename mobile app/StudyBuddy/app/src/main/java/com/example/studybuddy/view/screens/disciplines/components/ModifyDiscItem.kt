package com.example.studybuddy.view.screens.disciplines.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.dto.CreateTeacherDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.data.states.DisciplinesSt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.textfields.PrimaryTextField
import com.example.studybuddy.view.generalcomponents.texts.PrimaryTextViewer
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.disciplines.DisciplinesViewModel
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ModifyDiscItem(updatedTask: MutableState<DisciplineEnt>, state: State<DisciplinesSt>) {
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
                text = "Название предмета",
                fontSize = 18.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(height = 4.dp)
            PrimaryTextField(value = updatedTask.value.title, placeholder = "Название", 1) {
                updatedTask.value = updatedTask.value.copy(title = it)
            }
            SpacerHeight(height = 12.dp)
            DropDownMenuTeacher(
                state.value.teachers.find { it.idTeacher == updatedTask.value.idTeacher }?.fullName
                    ?: "Не выбрано", state.value.teachers.toMutableList(), "Преподаватель"
            ) {
                updatedTask.value = updatedTask.value.copy(idTeacher = it)
                if (it == 0) updatedTask.value = updatedTask.value.copy(idTeacher = null)
            }
        }
    }
}

@Composable
fun ModifyTeacherItem(state: State<DisciplinesSt>, vm: DisciplinesViewModel) {
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
                text = "Преподаватели",
                fontSize = 18.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(20.dp)
            val newTeacher = remember {
                mutableStateOf(CreateTeacherDto())
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)) {
                Column(modifier = Modifier.weight(1f)) {
                    PrimaryTextField(value = newTeacher.value.fullName, placeholder = "ФИО преподавателя", lineCount = 1) {
                        newTeacher.value = newTeacher.value.copy(fullName = it)
                    }
                    SpacerHeight(8.dp)
                    PrimaryTextField(value = if(newTeacher.value.officeNumber != null) newTeacher.value.officeNumber.toString() else "", placeholder = "Номер кабинета", lineCount = 1) {
                        if(it == "") newTeacher.value = newTeacher.value.copy(officeNumber = null)
                        val regex = Regex("[1-9]\\d*")
                        if(regex.matches(it) && it.length < 10)newTeacher.value = newTeacher.value.copy(officeNumber = it.toInt())
                    }
                }
                SpacerWidth(8.dp)
                Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_plus),
                    contentDescription = null,
                    tint = StudyBuddyTheme.colors.textButton,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(20))
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
            SpacerHeight(16.dp)
            state.value.teachers.forEach { teacher ->
                val teacherFIO = remember {
                    mutableStateOf(teacher.fullName)
                }
                val teacherOffice = remember {
                    mutableStateOf(teacher.officeNumber)
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)) {
                    Column(modifier = Modifier.weight(1f)) {
                        PrimaryTextField(value = teacherFIO.value, placeholder = "ФИО преподавателя", lineCount = 1) {
                            teacherFIO.value = it
                        }
                        SpacerHeight(8.dp)
                        PrimaryTextField(value = if(teacherOffice.value != null) teacherOffice.value.toString() else "", placeholder = "Номер кабинета", lineCount = 1) {
                            if(it == "") teacherOffice.value = null
                            val regex = Regex("[1-9]\\d*")
                            if(regex.matches(it) && it.length < 10)teacherOffice.value = it.toInt()
                        }
                    }
                    SpacerWidth(8.dp)
                    val colorPrimary = StudyBuddyTheme.colors.primary
                    val colorSecondary = StudyBuddyTheme.colors.secondary
                    val icon = remember {
                        mutableStateOf(Pair(R.drawable.icon_delete, colorSecondary))
                    }
                    if(teacherFIO.value != teacher.fullName || teacherOffice.value != teacher.officeNumber ) icon.value = Pair(R.drawable.icon_save, colorPrimary)
                    else icon.value = Pair(R.drawable.icon_delete, colorSecondary)
                    Icon(imageVector = ImageVector.vectorResource(icon.value.first),
                        contentDescription = null,
                        tint = StudyBuddyTheme.colors.textButton,
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(icon.value.second, RoundedCornerShape(20))
                            .padding(10.dp)
                            .size(20.dp).clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if(icon.value.second == colorPrimary) {
                                    /*vm.updateTeacher(TeacherEnt(teacher.idTeacher, teacher.idUser, teacherFIO.value, teacherOffice.value)) {
                                        if(it) {
                                            icon.value = Pair(R.drawable.icon_delete, colorSecondary)
                                        }
                                    }*/
                                }
                            }
                    )
                }
                SpacerHeight(8.dp)
            }

        }
    }
}