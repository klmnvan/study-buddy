package com.example.studybuddy.view.generalcomponents.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun ButtonFillMaxWidth(text: String, color: Color, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = StudyBuddyTheme.colors.textButton,
            containerColor = color,
            disabledContainerColor = color,
            disabledContentColor = StudyBuddyTheme.colors.textButton
        ),
        shape = RoundedCornerShape(5.dp),
        enabled = enabled
    ) {
        Text(
            text = text.toUpperCase(),
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 20.dp),
            style = StudyBuddyTheme.typography.regular,
            fontSize = 16.sp
        )
    }
}

