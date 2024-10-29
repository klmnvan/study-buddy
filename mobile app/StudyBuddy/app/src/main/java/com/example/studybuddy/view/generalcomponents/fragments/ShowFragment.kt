package com.example.studybuddy.view.generalcomponents.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.studybuddy.view.generalcomponents.buttons.ButtonSmall
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

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