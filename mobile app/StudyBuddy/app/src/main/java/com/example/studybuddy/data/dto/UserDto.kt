package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val nickname: String,
    val email: String,
    val token: String?,
)
