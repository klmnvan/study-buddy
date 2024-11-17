package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val course: Int? = 0,
    val id: Int = 0,
    val name: String? = "",
    val specialization: Specialization? = Specialization()
)