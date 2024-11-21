package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Предмет */
@Serializable
data class Specialization(
    val code: String = "",
    val full_name: String? = "",
    val name: String = ""
)