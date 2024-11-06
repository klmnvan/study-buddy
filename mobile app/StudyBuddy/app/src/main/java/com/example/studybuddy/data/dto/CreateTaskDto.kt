package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskDto(
    var title: String = "",
    var deadline: String = "",
    var idDiscipline: Int? = null,
    var description: String = "",
)
