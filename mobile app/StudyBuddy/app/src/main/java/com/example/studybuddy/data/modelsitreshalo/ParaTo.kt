package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Изменение в занятии: Кто ведёт пару, какая пара или просто изменённая пара*/
@Serializable
data class ParaTo(
    val hours: Int,
    val lesson: Lesson?,
    val subgroup: Int,
    val teachers: List<Teacher>
)