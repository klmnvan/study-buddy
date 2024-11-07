package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTeacherDto(
    var fullName: String = "",
    var officeNumber: Int? = null,
)