package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Занятие */
@Serializable
data class Lesson(
    val id: Int,
    val name: String,
    val short_name: String
)