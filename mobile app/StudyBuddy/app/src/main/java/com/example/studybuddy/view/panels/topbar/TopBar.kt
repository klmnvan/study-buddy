package com.example.studybuddy.view.panels.topbar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studybuddy.R
import com.example.studybuddy.domain.CachedData
import com.example.studybuddy.domain.CachedData.themes
import com.example.studybuddy.domain.navigation.NavigationRoutes
import com.example.studybuddy.view.components.SpacerHeight
import com.example.studybuddy.view.components.SpacerWidth
import com.example.studybuddy.view.components.TextLight
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import com.example.studybuddy.view.ui.theme.ThemeMode

@Composable
fun TopBar(controller: NavHostController, currentThemeMode: MutableState<ThemeMode>) {

    Box {
        Row(modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp, spotColor = Color(
            Black.value)
        ).background(StudyBuddyTheme.colors.containerDefault).padding(bottom = 12.dp, top = 12.dp).padding(horizontal = 24.dp)) {
            Row (
                modifier = Modifier.background(StudyBuddyTheme.colors.secondary, RoundedCornerShape(5.dp))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        controller.navigate(NavigationRoutes.AUTH) {
                            controller.currentDestination?.route?.let {
                                controller.popBackStack(
                                    route = it, // Очистить весь стек
                                    inclusive = true // Удалить все элементы стека, включая текущий
                                )
                            }
                        }
                    },
            ) {
                Row(modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)) {
                    TextLight("Выйти", 12.sp, StudyBuddyTheme.colors.textButton)
                    SpacerWidth(6.dp)
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_log_out),
                        modifier = Modifier.size(15.dp),
                        contentDescription = "", tint = StudyBuddyTheme.colors.textButton)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            val gradientForBrushIcon = Brush.verticalGradient(
                listOf(StudyBuddyTheme.colors.secondary, StudyBuddyTheme.colors.primary))
            Icon(imageVector = ImageVector.vectorResource(R.drawable.icon_brush),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.containerDefault,
                modifier = Modifier.background(gradientForBrushIcon, RoundedCornerShape(20)).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    currentThemeMode.value = themes.first { theme -> theme.title != CachedData.theme }
                    CachedData.theme = currentThemeMode.value.title
                }.padding(8.dp).size(12.dp)
            )
        }
    }

}