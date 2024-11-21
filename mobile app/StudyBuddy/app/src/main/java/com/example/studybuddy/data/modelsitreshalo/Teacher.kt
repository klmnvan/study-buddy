package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Преподаватель */
@Serializable
data class Teacher(
    val full_name: String? = "",
    val id: Int = 0,
    val instrumental_case: String = "",
    val name: String = ""
)