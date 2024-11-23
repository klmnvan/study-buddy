package com.example.studybuddy.view.generalcomponents.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.view.generalcomponents.texts.TextThin
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextField(value: String, placeholder: String, lineCount: Int, input: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { input(it) },
        textStyle = StudyBuddyTheme.typography.extralight.copy(
            color = StudyBuddyTheme.colors.textTitle,
            fontSize = 12.sp
        ),
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            TextThin(
                text = placeholder,
                fontSize = 12.sp,
                color = StudyBuddyTheme.colors.textTitle.copy(alpha = 0.6f)
            )
        },
        minLines = lineCount,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = StudyBuddyTheme.colors.containerPrimary,
            cursorColor = StudyBuddyTheme.colors.containerPrimary
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(value: String, placeholder: String, input: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { input(it) },
        textStyle = StudyBuddyTheme.typography.extralight.copy(
            color = StudyBuddyTheme.colors.textTitle,
            fontSize = 12.sp
        ),
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(5), spotColor = Color(Black.value))
            .fillMaxWidth(),
        placeholder = {
            TextThin(
                text = placeholder,
                fontSize = 12.sp,
                color = StudyBuddyTheme.colors.textTitle.copy(alpha = 0.6f)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_search),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.textTitle,
                modifier = Modifier.size(18.dp)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = StudyBuddyTheme.colors.containerDefault,
            cursorColor = StudyBuddyTheme.colors.textTitle
        ),
    )
}

