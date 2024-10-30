package com.example.studybuddy.view.screens.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.texts.DateTextViewer
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ShowTaskItem(el: TaskEnt) {
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
            TextTitle(text = el.title, fontSize = 24.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(height = 12.dp)
            DateTextViewer(el.deadline)
            SpacerHeight(height = 8.dp)
            TextTitle(text = "Описание", fontSize = 18.sp, color = StudyBuddyTheme.colors.textTitle)
            SpacerHeight(height = 8.dp)
            TextExtraLight(text = el.description, fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle)
        }
    }
}