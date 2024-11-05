package com.example.studybuddy.view.generalcomponents.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAuthNickname(value: String, input: (String) -> Unit, placeholder: String){
    OutlinedTextField(
        value = value,
        onValueChange = { input(it) },
        textStyle = StudyBuddyTheme.typography.extralight.copy(
            color = StudyBuddyTheme.colors.textTitle,
            fontSize = 16.sp
        ),
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                style = StudyBuddyTheme.typography.extralight.copy(
                    color = StudyBuddyTheme.colors.primary,
                    fontSize = 16.sp
                )
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = StudyBuddyTheme.colors.containerPrimary,
            cursorColor = StudyBuddyTheme.colors.containerPrimary
        ),
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_user),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = StudyBuddyTheme.colors.textTitle
            )
        },
    )
}

