package com.example.studybuddy.view.screens.tasks.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.domain.converters.ConvertDate
import com.example.studybuddy.view.generalcomponents.buttons.ButtonSmall
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.generalcomponents.fragments.ShowFragment
import com.example.studybuddy.view.generalcomponents.texts.TextBold
import com.example.studybuddy.view.screens.tasks.TasksViewModel
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme


@Composable
fun TaskListItem(el: TaskEnt, viewModel: TasksViewModel, disciplines: List<DisciplineEnt>, onClick: (el: TaskEnt) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .shadow(
            elevation = 4.dp, shape = RoundedCornerShape(5), spotColor = Color(
                Black.value
            )
        )
        .background(StudyBuddyTheme.colors.primary, RoundedCornerShape(5.dp))
        .padding(start = 10.dp)
        .background(
            StudyBuddyTheme.colors.containerDefault,
            RoundedCornerShape(0.dp, 5.dp, 5.dp, 0.dp)
        )) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 20.dp, start = 10.dp, end = 20.dp)) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = el.title.toUpperCase(),
                        style = StudyBuddyTheme.typography.bold, fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle, maxLines = 2,
                        overflow = TextOverflow.Ellipsis)
                    SpacerHeight(4.dp)
                    TextExtraLight(disciplines.firstOrNull{el.idDiscipline == it.idDiscipline}?.title ?: "Предмет не указан", 12.sp, StudyBuddyTheme.colors.textTitle)
                }
                SpacerWidth(16.dp)
                Checkbox(checked = el.isCompleted, onCheckedChange = {
                    showDialog = true
                }, modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(5.dp)),
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
                    onClick(el)
                }

            }
        }
    }
    SpacerHeight(16.dp)
    if (showDialog) {
        ShowFragment("Подтвердите", "Вы точно хотите изменить статус выполнения данной задачи?") {
            showDialog = false
            if(it) {
                viewModel.updateTask(el.copy(isCompleted = !el.isCompleted)) {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun DropDownMenu(
    value: String, list: List<DisciplineEnt>, placeholder: String, input: (Int) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            expanded = !expanded
        },) {
            TextBold(text = "$placeholder: ", fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle)
            TextExtraLight(text = if(value == "") "Не выбрано" else value, fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle)
        }
        SpacerHeight(8.dp)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.title) },
                    onClick = {
                        input(item.idDiscipline)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(Black)
                )
            }
        }
    }
}

