package com.example.studybuddy.view.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R

@Immutable
data class Typography(
    val bold16: TextStyle,
    val regular: TextStyle,
    val exstralight: TextStyle,
    val light: TextStyle,
)

val Geologica = FontFamily(
    Font(R.font.geologica_black, FontWeight.Black),
    Font(R.font.geologica_bold, FontWeight.Bold),
    Font(R.font.geologica_extra_bold, FontWeight.ExtraBold),
    Font(R.font.geologica_extra_light, FontWeight.ExtraLight),
    Font(R.font.geologica_light, FontWeight.Light),
    Font(R.font.geologica_medium, FontWeight.Medium),
    Font(R.font.geologica_regular, FontWeight.Normal),
    Font(R.font.geologica_semi_bold, FontWeight.SemiBold),
    Font(R.font.geologica_thin, FontWeight.Thin)
)

val typography = Typography(
    bold16 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = Geologica,
        fontSize = 16.sp
    ),
    regular = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = Geologica
    ),
    exstralight = TextStyle(
        fontWeight = FontWeight.ExtraLight,
        fontFamily = Geologica
    ),
    light = TextStyle(
        fontWeight = FontWeight.Light,
        fontFamily = Geologica
    ),
)

val LocalTypography = staticCompositionLocalOf { typography }