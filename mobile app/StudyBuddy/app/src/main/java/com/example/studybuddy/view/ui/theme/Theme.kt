package com.example.studybuddy.view.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object StudyBuddyTheme {
    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current
    val colors: ColorPalette
        @ReadOnlyComposable
        @Composable
        get() = LocalColors.current
}

@Composable
fun StudyBuddyTheme(
    themeMode: ThemeMode = ThemeMode.Dark,
    typography: Typography = StudyBuddyTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = when (themeMode) {
        ThemeMode.Dark -> darkPalette
        ThemeMode.Light -> baseLightPalette
    }
    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalColors provides colors
    ){
        content()
    }
}

sealed class ThemeMode(val title: String) {

    data object Dark: ThemeMode(title = "Dark")
    data object Light: ThemeMode(title = "Light")

}