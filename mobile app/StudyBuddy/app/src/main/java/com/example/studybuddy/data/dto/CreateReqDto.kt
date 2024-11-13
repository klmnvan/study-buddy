package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateReqDto(
    var idDiscipline: Int = 0,
    var content: String = "",
)