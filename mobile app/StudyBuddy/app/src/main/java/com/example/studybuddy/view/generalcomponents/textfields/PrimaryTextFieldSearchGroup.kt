package com.example.studybuddy.view.generalcomponents.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.view.generalcomponents.texts.TextBold
import com.example.studybuddy.view.generalcomponents.texts.TextThin
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextFieldSearchGroup(value: String, placeholder: String, lineCount: Int, clickIcon: () -> Unit, input: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { input(it) },
        textStyle = StudyBuddyTheme.typography.extralight.copy(
            color = StudyBuddyTheme.colors.textTitle,
            fontSize = 12.sp
        ),
        leadingIcon = {
            Box(modifier = Modifier.padding(start = 12.dp)) {
                TextBold("Группа: ", 12.sp, StudyBuddyTheme.colors.textTitle)
            }
        },
        modifier = Modifier
            .fillMaxWidth().focusable(true),
        placeholder = {
            TextThin(
                text = placeholder,
                fontSize = 12.sp,
                color = StudyBuddyTheme.colors.textTitle.copy(alpha = 0.6f)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.textTitle,
                modifier = Modifier.size(12.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    clickIcon()
                }
            )
        },
        minLines = lineCount,
        shape = RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = StudyBuddyTheme.colors.containerPrimary,
            cursorColor = StudyBuddyTheme.colors.containerPrimary
        ),
    )
}