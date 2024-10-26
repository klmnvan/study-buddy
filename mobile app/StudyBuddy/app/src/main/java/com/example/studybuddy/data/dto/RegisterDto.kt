package com.example.studybuddy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDto(
    val nickname: String?,
    val email: String?,
    val password: String?,
    val confirmPassword: String?
)
