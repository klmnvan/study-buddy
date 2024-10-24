package com.example.studybuddy.view.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Cream = Color(0xFFFDFAF4)
val White = Color(0xFFFFFFFF)
val Orange = Color(0xFFEE9D5B)
val Gray = Color(0xFFB4B8C0)
val Orange20 = Color(0x33EE9D5B)
val Dark_Green = Color(0xFF4D544C)
val Green = Color(0xFF899C6F)
val Green20 = Color(0x33899C6F)

data class ColorPalette(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val textTitle: Color,
    val textDesc: Color,
    val textButton: Color,
    val containerPrimary: Color,
    val containerSecondary: Color,

)

val baseLightPalette = ColorPalette(
    background = Cream,
    primary = Green,
    secondary = Orange,
    textTitle = Dark_Green,
    textDesc = Green,
    textButton = Cream,
    containerPrimary = Green20,
    containerSecondary = Orange20
)

val LocalColors = staticCompositionLocalOf { baseLightPalette }