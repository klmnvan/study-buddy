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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.data.states.DisciplinesSt
import com.example.studybuddy.data.states.TasksSt
import com.example.studybuddy.view.generalcomponents.buttons.ButtonDatePicker
import com.example.studybuddy.view.generalcomponents.fragments.ShowFragment
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.textfields.PrimaryTextField
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.disciplines.DisciplinesViewModel
import com.example.studybuddy.view.screens.tasks.components.DropDownMenuDisc
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

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
            TextTitle(text = "Добавить преподавателя", fontSize = 18.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(16.dp)
            val newTeacher = remember {
                mutableStateOf(CreateTeacherDto())
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    PrimaryTextField(
                        value = newTeacher.value.fullName,
                        placeholder = "ФИО преподавателя",
                        lineCount = 1
                    ) {
                        if (it == "") newTeacher.value = newTeacher.value.copy(fullName = "")
                        val regex = Regex("^[a-zA-Zа-яА-ЯёЁ\\s]+\$")
                        if (regex.matches(it))
                            newTeacher.value = newTeacher.value.copy(fullName = it)
                    }
                    SpacerHeight(4.dp)
                    PrimaryTextField(
                        value = if (newTeacher.value.officeNumber != null) newTeacher.value.officeNumber.toString() else "",
                        placeholder = "Номер кабинета",
                        lineCount = 1
                    ) {
                        if (it == "") newTeacher.value = newTeacher.value.copy(officeNumber = null)
                        val regex = Regex("[1-9]\\d*")
                        if (regex.matches(it) && it.length < 10) newTeacher.value =
                            newTeacher.value.copy(officeNumber = it.toInt())
                    }
                }
                SpacerWidth(8.dp)
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_plus),
                    contentDescription = null,
                    tint = StudyBuddyTheme.colors.textButton,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(20))
                        .padding(10.dp)
                        .size(20.dp).clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            vm.createTeacher(newTeacher.value) {
                                if(it) {
                                    newTeacher.value = CreateTeacherDto()
                                }
                            }
                        }
                )
            }
            SpacerHeight(20.dp)
            TextTitle(text = "Преподаватели", fontSize = 18.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(16.dp)
            Log.e("teachers", state.value.teachers.toString())
            state.value.teachers.forEach { teacher ->
                val teacherValues = remember { mutableStateOf(CreateTeacherDto(teacher.fullName, teacher.officeNumber)) }
                LaunchedEffect(teacher) {
                    teacherValues.value = CreateTeacherDto(teacher.fullName, teacher.officeNumber)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        PrimaryTextField(
                            value = teacherValues.value.fullName,
                            placeholder = "ФИО преподавателя",
                            lineCount = 1
                        ) {
                            val regex = Regex("^[a-zA-Zа-яА-ЯёЁ\\s]+\$")
                            if (regex.matches(it))
                                teacherValues.value = teacherValues.value.copy(fullName = it)
                        }
                        SpacerHeight(4.dp)
                        PrimaryTextField(
                            value = if (teacherValues.value.officeNumber != null) teacherValues.value.officeNumber.toString() else "",
                            placeholder = "Номер кабинета",
                            lineCount = 1
                        ) {
                            if (it == "") teacherValues.value = teacherValues.value.copy(officeNumber = null)
                            val regex = Regex("[1-9]\\d*")
                            if (regex.matches(it) && it.length < 10) teacherValues.value = teacherValues.value.copy(officeNumber = it.toInt())
                        }
                    }
                    SpacerWidth(8.dp)
                    var showDialog by remember { mutableStateOf(false) }
                    Column(modifier = Modifier.fillMaxHeight()) {
                        if(teacherValues.value.fullName != teacher.fullName || teacherValues.value.officeNumber != teacher.officeNumber) {
                            Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_save),
                                contentDescription = null,
                                tint = StudyBuddyTheme.colors.textButton,
                                modifier = Modifier.weight(1f)
                                    .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(10, 10 , 0 ,0))
                                    .padding(10.dp)
                                    .size(20.dp).clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        vm.updateTeacher(TeacherEnt(teacher.idTeacher, teacher.idUser, teacherValues.value.fullName, teacherValues.value.officeNumber)) {

                                        }
                                    }
                            )
                            Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_krestik),
                                contentDescription = null,
                                tint = StudyBuddyTheme.colors.textButton,
                                modifier = Modifier.weight(1f)
                                    .background(StudyBuddyTheme.colors.secondary, RoundedCornerShape(0, 0 , 10 ,10))
                                    .padding(10.dp)
                                    .size(20.dp).clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        teacherValues.value = CreateTeacherDto(teacher.fullName, teacher.officeNumber)
                                    }
                            )
                        }
                        else {
                            Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_delete),
                                contentDescription = null,
                                tint = StudyBuddyTheme.colors.textButton,
                                modifier = Modifier.weight(1f)
                                    .background(StudyBuddyTheme.colors.secondary, RoundedCornerShape(10))
                                    .padding(10.dp)
                                    .size(20.dp).clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        showDialog = true
                                    }
                            )
                        }
                    }
                    if (showDialog) {
                        ShowFragment("Подтвердите", "Вы точно хотите удалить этого преподавателя без возможности возвращаения?") {
                            showDialog = false
                            if(it) {
                                vm.deleteTeacher(teacher)
                            }
                        }
                    }
                }
                SpacerHeight(16.dp)
            }
        }
    }
}

