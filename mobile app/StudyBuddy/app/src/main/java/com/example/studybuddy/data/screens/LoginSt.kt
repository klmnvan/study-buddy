package com.example.studybuddy.data.screens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginSt(
    @SerialName("Email")
    var email: String = "user@example.com",
    @SerialName("Password")
    var password: String = "12345678",
)
