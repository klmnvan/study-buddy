package com.example.studybuddy.view.screens.tasks.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.textfields.PrimaryTextFieldSearchGroup
import com.example.studybuddy.view.generalcomponents.texts.TextBold
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun DropDownMenuDisc(
    value: String, list: MutableList<DisciplineEnt>, placeholder: String, input: (Int) -> Unit){
    val c = LocalDensity.current
    val configuration: Configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    var dropdownWidth by remember {  mutableStateOf(0.dp) } //размер выпадающего списка под контекст
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(list) {
        list.add(0, DisciplineEnt(0, "Не выбрано", 0, ""))
    }
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                dropdownWidth = with(c) { it.size.width.toDp() } }
                .background(StudyBuddyTheme.colors.containerPrimary,
                    if(!expanded)RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp)
                else RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp))
                .padding(vertical = 20.dp, horizontal = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = !expanded
                }
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
                Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = StudyBuddyTheme.colors.textTitle,
                    modifier = Modifier.size(12.dp).rotate(180F)
                )
            }
        }
        if(expanded){
            HorizontalDivider(color = StudyBuddyTheme.colors.textTitle, thickness = 0.5.dp)
            Column (modifier = Modifier.width(dropdownWidth).background(StudyBuddyTheme.colors.containerDefault.copy(alpha = 0.8f))
                .background(StudyBuddyTheme.colors.containerPrimary, RoundedCornerShape(0.dp, 0 .dp, 5.dp, 5.dp)).heightIn(max = screenHeight / 5)
                .verticalScroll(rememberScrollState())) {
                list.forEach { item ->
                    Box( modifier = Modifier.fillMaxWidth().clickable {
                        input(item.idDiscipline)
                        expanded = !expanded
                    }.padding(horizontal = 20.dp, vertical = 18.dp)) {
                        TextExtraLight(
                            item.title,
                            12.sp,
                            StudyBuddyTheme.colors.textTitle
                        )
                    }
                }
            }
        }
    }
}