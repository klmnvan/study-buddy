package com.example.studybuddy.view.screens.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.studybuddy.data.modelsitreshalo.Change
import com.example.studybuddy.data.modelsitreshalo.Main
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ItemRowSchedule(time: String, listMain: List<Main>, listChange: List<Change>) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        //Пара
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
                text = time, style = StudyBuddyTheme.typography.bold,
                fontSize = 2.5.em,
                color = StudyBuddyTheme.colors.secondary, textAlign = TextAlign.Center
            )
        }
        SpacerWidth(8.dp)
        //Основное расписание
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (listMain.isNotEmpty()) {
                    listMain.forEach { mainItem ->
                        Column(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                                .background(
                                    StudyBuddyTheme.colors.containerSecondary,
                                    RoundedCornerShape(5.dp)
                                )
                                .padding(vertical = 15.dp, horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${mainItem.lesson.short_name}\n" +
                                        (if (mainItem.teachers.isNotEmpty()) "${mainItem.teachers.first().name}\n" else "") +
                                        "Группа: ${mainItem.group.name}" +
                                        (if (mainItem.cabinet != null) "\nКабинет: ${mainItem.cabinet.name}" else "") +
                                        (if (mainItem.subgroup != 0) "\nПодгруппа: ${mainItem.subgroup}" else ""),
                                style = StudyBuddyTheme.typography.bold,
                                fontSize = 2.5.em,
                                color = StudyBuddyTheme.colors.textTitle,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier.fillMaxSize()
                            .background(
                                StudyBuddyTheme.colors.containerSecondary,
                                RoundedCornerShape(5.dp)
                            )
                            .padding(vertical = 15.dp, horizontal = 8.dp),
                        text = ""
                    )
                }
            }
        }
        SpacerWidth(8.dp)
        if (listChange.isNotEmpty()) {
            listChange.forEach { changeItem ->
                var textChange = ""
                var colorBack = StudyBuddyTheme.colors.containerSecondary
                if (changeItem.type == "replace") {
                    colorBack = StudyBuddyTheme.colors.containerThird
                    textChange = "ЗАМЕНА \n${changeItem.paraFrom.lesson!!.short_name}\nНА\n" +
                            "${changeItem.paraTo!!.lesson!!.name}\n" +
                            (if (changeItem.paraTo.teachers.isNotEmpty()) changeItem.paraTo.teachers.first().name + "\n" else "") +
                            "Группа: ${changeItem.group.name}\n" +
                            "Кабинет: ${changeItem.cabinet?.name}"
                } else if (changeItem.type == "remove") {
                    colorBack = StudyBuddyTheme.colors.secondary
                    textChange = "ОТМЕНА \n${changeItem.paraFrom.lesson!!.short_name}\n У " +
                            "${changeItem.paraFrom.teachers.first().instrumental_case}\n" +
                            "Группа: ${changeItem.group.name}"
                } else if (changeItem.paraTo?.lesson != null && changeItem.parallel == null) {
                    colorBack = StudyBuddyTheme.colors.primary
                    textChange = "${changeItem.paraTo.lesson.short_name}\n" +
                            "${changeItem.paraTo.teachers.first().name}\n" +
                            "Группа: ${changeItem.group.name}\n" +
                            "Кабинет: ${changeItem.cabinet?.name}"
                } else if (changeItem.parallel != null) {
                    colorBack = StudyBuddyTheme.colors.primary
                    textChange = "${changeItem.paraTo?.lesson?.short_name}\n" +
                            "${changeItem.paraTo?.teachers?.first()?.name}\n" +
                            "Группа: ${changeItem.group.name}\n" +
                            "Кабинет: ${changeItem.cabinet?.name}\n\n" +
                            "ПАРАЛЛЕЛЬНО С ${changeItem.parallel.instrumental_case}"

                }
                CustomTextChange(textChange, StudyBuddyTheme.colors.textTitle, colorBack)
            }
        } else {
            CustomTextChange(
                "",
                StudyBuddyTheme.colors.secondary,
                StudyBuddyTheme.colors.containerSecondary
            )
        }
    }
}