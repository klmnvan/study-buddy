package com.example.studybuddy.view.generalcomponents.buttons

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ButtonSmall(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = StudyBuddyTheme.colors.textButton,
            containerColor = color,
            disabledContainerColor = color,
            disabledContentColor = StudyBuddyTheme.colors.textButton
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            color = StudyBuddyTheme.colors.textButton,
            style = StudyBuddyTheme.typography.regular,
            fontSize = 12.sp
        )
    }
}
