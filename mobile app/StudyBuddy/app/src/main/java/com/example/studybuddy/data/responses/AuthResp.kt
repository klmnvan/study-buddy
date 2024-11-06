package com.example.studybuddy.data.responses

import com.example.studybuddy.data.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthResp(
    val user: UserDto? = null,
    val error: String = "",
)


