package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateExamDto(
    var title: String = "",
    var duration: String = "",
    var numberTickets: Int = 0,
    var dateExam: String = "",
)