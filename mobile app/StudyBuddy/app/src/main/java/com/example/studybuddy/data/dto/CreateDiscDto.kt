package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateDiscDto(
    var title: String = "",
    var idTeacher: Int? = null,
)