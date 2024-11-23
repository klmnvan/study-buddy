package com.example.studybuddy.view.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Cream = Color(0xFFFDFAF4)
val DarkBack = Color(0xFF2F2C2B)
val DarkCont = Color(0xFF242424)
val White = Color(0xFFFFFFFF)
val White40 = Color(0x66FFFFFF)
val Orange = Color(0xFFEE9D5B)
val Orange2 = Color(0xFFFA9442)
val Gray = Color(0xFFB4B8C0)
val Dark_Green = Color(0xFF4D544C)
val Green = Color(0xFF899C6F)
val Green2 = Color(0xFF89A95E)
val Yellow = Color(0xFFF8DA7A)

data class ColorPalette(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val third: Color,
    val textTitle: Color,
    val textDesc: Color,
    val textButton: Color,
    val containerPrimary: Color,
    val containerSecondary: Color,
    val containerThird: Color,
    val containerDefault: Color,
    val unselectItem: Color
)

val baseLightPalette = ColorPalette(
    background = Cream,
    primary = Green,
    secondary = Orange,
    third = Yellow,
    textTitle = Dark_Green,
    textDesc = Green,
    textButton = White,
    containerPrimary = Green.copy(alpha = 0.2f),
    containerSecondary = Orange.copy(alpha = 0.2f),
    containerThird = Yellow.copy(alpha = 0.8f),
    containerDefault = White,
    unselectItem = Gray
)

val darkPalette = ColorPalette(
    background = DarkBack,
    primary = Green2,
    secondary = Orange2,
    third = Yellow,
    textTitle = White,
    textDesc = Green2,
    textButton = White,
    containerPrimary = Green2.copy(alpha = 0.2f),
    containerSecondary = Orange2.copy(alpha = 0.2f),
    containerDefault = DarkCont,
    containerThird = Yellow.copy(alpha = 0.8f),
    unselectItem = White40
)

val LocalColors = staticCompositionLocalOf { darkPalette }