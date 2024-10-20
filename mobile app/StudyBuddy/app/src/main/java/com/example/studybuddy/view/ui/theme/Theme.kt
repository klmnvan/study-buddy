package com.example.studybuddy.view.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

object StudyBuddyTheme {
    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current
    val colors: ColorScheme
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
        ThemeMode.Dark -> LightColorScheme
    }
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography
    ){
        content()
    }
}

sealed class ThemeMode(val title: String) {
    data object Dark: ThemeMode(title = "Light")
}