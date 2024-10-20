package com.example.studybuddy.view.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(
    val titleScreen: TextStyle,
)

val typography = Typography(
    titleScreen = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp)
)

val LocalTypography = staticCompositionLocalOf { typography }