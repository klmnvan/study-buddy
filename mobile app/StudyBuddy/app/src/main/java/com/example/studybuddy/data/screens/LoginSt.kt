package com.example.studybuddy.data.screens

import kotlinx.serialization.Serializable

@Serializable
data class LoginSt(
    var email: String = "user@example.com",
    var password: String = "12345678",
)
