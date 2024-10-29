package com.example.studybuddy.view.generalcomponents.texts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun DoubleText(firstPart: String, secondPart: String, onClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = StudyBuddyTheme.colors.primary,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(firstPart)
            }
            withStyle(
                SpanStyle(
                    color = StudyBuddyTheme.colors.textTitle,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(secondPart)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        style = StudyBuddyTheme.typography.bold
    )
}