package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Информация о расписании на конкретную дату и для конкретного фильтра (id группы, кабинета или преподавателя) */
@Serializable
data class ResultSchedule(
    val bell: List<Bell> = listOf(),
    val change: List<Change> = listOf(),
    val change_status: String = "",
    val main: List<Main> = listOf(),
)