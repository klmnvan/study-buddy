package com.example.studybuddy.view.screens.disciplines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.studybuddy.data.dto.CreateReqDto
import com.example.studybuddy.data.dto.CreateTeacherDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.data.states.DisciplinesSt
import com.example.studybuddy.view.generalcomponents.fragments.ShowFragment
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.textfields.PrimaryTextField
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.screens.disciplines.DisciplinesViewModel
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ShowDiscItem(el: DisciplineEnt, teacher: TeacherEnt?, state: State<DisciplinesSt>, vm: DisciplinesViewModel) {
    val newReq = remember { mutableStateOf(CreateReqDto(idDiscipline = el.idDiscipline)) }
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
            TextTitle(text = "Преподаватель", fontSize = 18.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(height = 8.dp)
            TextExtraLight(
                text = teacher?.fullName ?: "Не указан",
                fontSize = 12.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(height = 12.dp)
            TextTitle(text = "Кабинет преподавателя", fontSize = 18.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(height = 8.dp)
            TextExtraLight(
                text = if(teacher?.officeNumber != null) teacher.officeNumber.toString() else "Не указан",
                fontSize = 12.sp,
                color = StudyBuddyTheme.colors.textTitle
            )
            SpacerHeight(height = 24.dp)
            TextTitle("Требования", 24.sp, StudyBuddyTheme.colors.textTitle)
            SpacerHeight(8.dp)
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Column(modifier = Modifier.weight(1f)) {
                    PrimaryTextField(
                        value = newReq.value.content,
                        placeholder = "Новое требование",
                        lineCount = 1
                    ) {
                        newReq.value = newReq.value.copy(content = it)
                    }
                }
                SpacerWidth(8.dp)
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_plus),
                    contentDescription = null,
                    tint = StudyBuddyTheme.colors.textButton,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(10))
                        .padding(15.dp)
                        .size(20.dp).clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            vm.createReq(newReq.value) {
                                if(it) {
                                    newReq.value = CreateReqDto(idDiscipline = el.idDiscipline)
                                }
                            }
                        }
                )
            }
            SpacerHeight(8.dp)
            state.value.requirementsByDisc.forEach { req ->
                val reqValue = remember { mutableStateOf(req.content) }
                LaunchedEffect(req) {
                    reqValue.value = req.content
                }
                Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    Column(modifier = Modifier.weight(1f)) {
                        PrimaryTextField(
                            value = reqValue.value,
                            placeholder = "Новое требование",
                            lineCount = 1
                        ) {
                            reqValue.value = it
                        }
                    }
                    SpacerWidth(8.dp)
                    var showDialog by remember { mutableStateOf(false) }
                    if (reqValue.value == req.content) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_delete),
                            contentDescription = null,
                            tint = StudyBuddyTheme.colors.textButton,
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(StudyBuddyTheme.colors.secondary, RoundedCornerShape(10))
                                .padding(15.dp)
                                .size(20.dp).clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    showDialog = true
                                }
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_save),
                            contentDescription = null,
                            tint = StudyBuddyTheme.colors.textButton,
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(10))
                                .padding(15.dp)
                                .size(20.dp).clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    vm.updateReq(req.copy(content = reqValue.value)) {

                                    }
                                }
                        )
                    }
                    if (showDialog) {
                        ShowFragment(
                            "Подтвердите",
                            "Вы точно хотите удалить это требование без возможности возвращаения?"
                        ) {
                            showDialog = false
                            if (it) {
                                vm.deleteReq(req)
                            }
                        }
                    }
                }
                SpacerHeight(8.dp)
            }
            SpacerHeight(8.dp)
        }
    }
}