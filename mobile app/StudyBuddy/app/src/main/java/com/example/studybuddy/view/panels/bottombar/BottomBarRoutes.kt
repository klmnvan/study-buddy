package com.example.studybuddy.view.panels.bottombar

import com.example.studybuddy.R
import com.example.studybuddy.view.navigation.NavigationRoutes

sealed class BottomBarRoutes(
    val route: String,
    val title: String = "",
    val resourceId: Int? = null
) {
    object TaskScreen : BottomBarRoutes(
        route = NavigationRoutes.TASKS,
        title = "Задания",
        resourceId = R.drawable.task_icon
    )

    object DisciplineScreen : BottomBarRoutes(
        route = NavigationRoutes.DISCIPLINES,
        title = "Предметы",
        resourceId = R.drawable.disc_icon
    )

    object ExamScreen : BottomBarRoutes(
        route = NavigationRoutes.EXAMS,
        title = "Экзамены",
        resourceId = R.drawable.exam_icon
    )

    object ScheduleScreen : BottomBarRoutes(
        route = NavigationRoutes.SCHEDULE,
        title = "Расписание",
        resourceId = R.drawable.schedule_icon
    )
}

