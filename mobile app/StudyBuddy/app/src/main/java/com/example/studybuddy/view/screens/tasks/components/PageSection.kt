package com.example.studybuddy.view.screens.tasks.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.domain.converters.ConvertDate
import com.example.studybuddy.view.components.ButtonSmall
import com.example.studybuddy.view.components.SpacerHeight
import com.example.studybuddy.view.components.SpacerWidth
import com.example.studybuddy.view.components.TextLight
import com.example.studybuddy.view.components.TextExtraLight
import com.example.studybuddy.view.components.TextTitle
import com.example.studybuddy.view.screens.tasks.TasksViewModel
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme


@Composable
fun PageSection(title: String, listItem: List<TaskEnt>, viewModel: TasksViewModel, expandedValue: Boolean, disciplines: List<DisciplineEnt>, expanded: (Boolean) -> Unit) {
    Column {
        Row(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded(!expandedValue)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                TextLight(title, 20.sp, StudyBuddyTheme.colors.primary)
            }
            SpacerWidth(12.dp)
            Icon(
                modifier = Modifier
                    .size(15.dp)
                    .rotate(if (expandedValue) 180f else 0F),
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.textTitle
            )
        }
        AnimatedVisibility(
            visible = expandedValue,
            enter = expandVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
            exit = shrinkVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh))
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                listItem.forEach { task ->
                    TaskComponent(task, viewModel, disciplines)
                }
            }
        }
    }
}

@Composable
fun TaskComponent(el: TaskEnt, viewModel: TasksViewModel, disciplines: List<DisciplineEnt>) {
    var showDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp, shape = RoundedCornerShape(5), spotColor = Color(Black.value)).background(StudyBuddyTheme.colors.primary, RoundedCornerShape(5.dp)).padding(start = 10.dp)
        .background(StudyBuddyTheme.colors.containerDefault, RoundedCornerShape(0.dp, 5.dp, 5.dp, 0.dp))) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 20.dp, start = 10.dp, end = 20.dp)) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = el.title.toUpperCase(),
                        style = StudyBuddyTheme.typography.bold, fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle, maxLines = 2)
                    SpacerHeight(4.dp)
                    TextExtraLight(disciplines.firstOrNull{el.idDiscipline == it.idDiscipline}?.title ?: "Предмет не указан", 12.sp, StudyBuddyTheme.colors.textTitle)
                }
                SpacerWidth(16.dp)
                Checkbox(checked = el.isCompleted, onCheckedChange = {
                    showDialog = true
                }, modifier = Modifier.size(20.dp).clip(RoundedCornerShape(5.dp)),
                    colors = CheckboxDefaults.colors(
                        checkedColor = StudyBuddyTheme.colors.secondary,
                        disabledCheckedColor = StudyBuddyTheme.colors.secondary,
                        disabledUncheckedColor = StudyBuddyTheme.colors.secondary,
                        checkmarkColor = StudyBuddyTheme.colors.containerDefault,
                        disabledIndeterminateColor = StudyBuddyTheme.colors.secondary,
                        uncheckedColor = StudyBuddyTheme.colors.secondary
                    ))
            }
            SpacerHeight(24.dp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_clock),
                        contentDescription = null,
                        tint = StudyBuddyTheme.colors.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                    SpacerWidth(8.dp)
                    TextTitle(ConvertDate(el.deadline), 12.sp, StudyBuddyTheme.colors.textTitle)
                }
                SpacerWidth(16.dp)
                ButtonSmall("Детали", StudyBuddyTheme.colors.primary) {

                }

            }
        }
    }
    SpacerHeight(16.dp)
    if (showDialog) {
        ShowFragment("Подтвердите", "Вы точно хотите изменить статус выполнения данной задачи?") {
            showDialog = false
            if(it) {
                viewModel.updateTask(el.copy(isCompleted = !el.isCompleted))
            }
        }
    }
}

@Composable
fun ShowFragment(title: String, desc: String, onDismissRequest: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Card(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = StudyBuddyTheme.colors.containerDefault
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = title,
                    style = StudyBuddyTheme.typography.regular, fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle, textAlign = TextAlign.Center)
                SpacerHeight(16.dp)
                Text(text = desc,
                    style = StudyBuddyTheme.typography.exstralight, fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle, textAlign = TextAlign.Center)
                SpacerHeight(20.dp)
                Row {
                    ButtonSmall("Да, хочу", StudyBuddyTheme.colors.primary) {
                        onDismissRequest(true)
                    }
                    SpacerWidth(12.dp)
                    ButtonSmall("Нет", StudyBuddyTheme.colors.secondary) {
                        onDismissRequest(false)
                    }
                }
            }
        }


    }
}
