package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateNoteDto(
    var idExam: Int = 0,
    var content: String = "",
)

