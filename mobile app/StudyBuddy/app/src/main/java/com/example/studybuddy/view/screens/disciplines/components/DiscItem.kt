package com.example.studybuddy.view.screens.disciplines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun DiscItem(el: DisciplineEnt) {

    Box(modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp, shape = RoundedCornerShape(5)).background(StudyBuddyTheme.colors.containerDefault, RoundedCornerShape(5.dp))) {
        Row(modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = el.title, style = StudyBuddyTheme.typography.bold, fontSize = 12.sp, color = StudyBuddyTheme.colors.textTitle,
                modifier = Modifier.weight(1f), maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            SpacerWidth(24.dp)
            Icon(
                modifier = Modifier
                    .fillMaxHeight()
                    .rotate(90F),
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.textTitle
            )
        }
    }

    SpacerHeight(12.dp)

}