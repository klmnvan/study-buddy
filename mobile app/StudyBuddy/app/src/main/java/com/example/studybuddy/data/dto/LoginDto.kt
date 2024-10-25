package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val email: String?,
    val password: String?
)
