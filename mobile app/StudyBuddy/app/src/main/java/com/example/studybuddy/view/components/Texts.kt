package com.example.studybuddy.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun TextTitle(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.regular, fontSize = fontSize, color = color)
}

@Composable
fun TextDesc(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.light, fontSize = fontSize, color = color)
}

@Composable
fun TextBold(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.bold, fontSize = fontSize, color = color)
}

@Composable
fun TextExtraLight(text: String, fontSize: TextUnit, color: Color) {
    Text(text = text, style = StudyBuddyTheme.typography.exstralight, fontSize = fontSize, color = color)
}

@Composable
fun DoubleText(firstPart: String, secondPart: String, onClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = StudyBuddyTheme.colors.primary, fontWeight = FontWeight.Normal)) {
                append(firstPart)
            }
            withStyle(SpanStyle(color = StudyBuddyTheme.colors.textTitle, fontWeight = FontWeight.Bold)) {
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

