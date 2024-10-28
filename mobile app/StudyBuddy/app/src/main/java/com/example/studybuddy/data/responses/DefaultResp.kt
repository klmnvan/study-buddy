package com.example.studybuddy.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class DefaultResp(
    val error: String = "",
)
