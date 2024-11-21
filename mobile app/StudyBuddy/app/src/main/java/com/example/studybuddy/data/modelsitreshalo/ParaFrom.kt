package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Отменённая пара */
@Serializable
data class ParaFrom(
    val lesson: Lesson?,
    val subgroup: Int,
    val teachers: List<Teacher>
)