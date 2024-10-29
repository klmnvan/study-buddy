package com.example.studybuddy.view.screens.exams.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.domain.converters.ConvertDate
import com.example.studybuddy.view.generalcomponents.icons.ButtonMore
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.generalcomponents.texts.TextLight
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ExamItem(el: ExamEnt) {

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
                    TextExtraLight("Длительность: ${el.duration}", 12.sp, StudyBuddyTheme.colors.textTitle)
                }
                SpacerWidth(16.dp)
                ButtonMore {  }
            }
            SpacerHeight(24.dp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_tickets),
                        contentDescription = null,
                        tint = StudyBuddyTheme.colors.secondary,
                        modifier = Modifier.size(30.dp)
                    )
                    SpacerWidth(4.dp)
                    TextLight(": ${el.numberTickets}", 16.sp, StudyBuddyTheme.colors.primary)
                }
                SpacerWidth(16.dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_clock),
                        contentDescription = null,
                        tint = StudyBuddyTheme.colors.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                    SpacerWidth(8.dp)
                    TextTitle(ConvertDate(el.dateExam), 12.sp, StudyBuddyTheme.colors.textTitle)
                }

            }
        }
    }
    SpacerHeight(16.dp)

}

