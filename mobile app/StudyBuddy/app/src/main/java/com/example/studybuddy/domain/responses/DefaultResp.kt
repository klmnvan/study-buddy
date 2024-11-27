package com.example.studybuddy.domain.responses

import kotlinx.serialization.Serializable

@Serializable
data class DefaultResp(
    val error: String = "",
)
