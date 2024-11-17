package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Основное расписание без изменений */
@Serializable
data class Main(
    val cabinet: Cabinet,
    val group: Group,
    val id: Int,
    val is_short: Boolean,
    val lesson: Lesson,
    val para: Int,
    val schedule_id: Int,
    val subgroup: Int,
    val teachers: List<Teacher>,
    val type: String,
    val week_type: Int,
    val weekday: Int
)