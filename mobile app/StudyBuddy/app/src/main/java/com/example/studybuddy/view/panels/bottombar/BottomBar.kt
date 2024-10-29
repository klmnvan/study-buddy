package com.example.studybuddy.view.panels.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun BottomBar(controller: NavHostController) {
    val screens = listOf(BottomBarRoutes.TaskScreen, BottomBarRoutes.DisciplineScreen, BottomBarRoutes.ExamScreen, BottomBarRoutes.ScheduleScreen)
    Box {
        val navBackStackEntry by controller.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Row(modifier = Modifier.background(StudyBuddyTheme.colors.containerDefault).padding(bottom = 12.dp, top = 8.dp)) {
            screens.forEach { screen ->
                Column(modifier = Modifier.weight(1f)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        if(currentRoute != screen.route) {
                            controller.navigate(screen.route) {
                                currentRoute?.let {
                                    popUpTo(it) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },horizontalAlignment = Alignment.CenterHorizontally) {
                    var selectedColor = StudyBuddyTheme.colors.unselectItem
                    if(currentRoute == screen.route) {
                        selectedColor = StudyBuddyTheme.colors.textTitle
                    }
                    SpacerHeight(12.dp)
                    Icon(imageVector = ImageVector.vectorResource(id = screen.resourceId!!),
                        modifier = Modifier.size(25.dp),
                        contentDescription = "", tint = selectedColor)
                    SpacerHeight(4.dp)
                    TextExtraLight(screen.title, 12.sp, selectedColor)
                }
            }
        }
    }
}