package com.example.studybuddy.view.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val Cream = Color(0xFFFDFAF4)

val LightColorScheme = lightColorScheme(
    background = Cream
)

val LocalColors = staticCompositionLocalOf { LightColorScheme }