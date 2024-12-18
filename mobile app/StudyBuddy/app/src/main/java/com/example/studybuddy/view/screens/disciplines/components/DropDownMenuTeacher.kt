package com.example.studybuddy.view.screens.disciplines.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextBold
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun DropDownMenuTeacher(
    value: String, list: MutableList<TeacherEnt>, placeholder: String, input: (Int) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(list) {
        list.add(0, TeacherEnt(0, "", "Не выбрано", 0))
    }
    Column {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(
                    StudyBuddyTheme.colors.containerPrimary,
                    RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp)
                )
                .padding(vertical = 20.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = !expanded
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextBold(
                    text = "$placeholder: ",
                    fontSize = 12.sp,
                    color = StudyBuddyTheme.colors.textTitle
                )
                Text(
                    text = value,
                    modifier = Modifier.weight(1f),
                    style = StudyBuddyTheme.typography.extralight,
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = StudyBuddyTheme.colors.textTitle,
                    overflow = TextOverflow.Ellipsis
                )
                SpacerWidth(4.dp)
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = StudyBuddyTheme.colors.textTitle,
                    modifier = Modifier.size(12.dp).rotate(180F)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(StudyBuddyTheme.colors.containerDefault.copy(alpha = 0.8f))
                .background(StudyBuddyTheme.colors.containerPrimary),
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = {
                        TextExtraLight(
                            item.fullName,
                            12.sp,
                            StudyBuddyTheme.colors.textTitle
                        )
                    },
                    onClick = {
                        input(item.idTeacher)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(

                    )
                )
            }
        }
    }
}