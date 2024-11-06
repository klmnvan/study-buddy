package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTeacherDto(
    var fullName: String = "",
    var officeNumber: Int = 0,
)

@Serializable
data class CreateDiscDto(
    var title: String = "",
    var idTeacher: Int? = null,
)