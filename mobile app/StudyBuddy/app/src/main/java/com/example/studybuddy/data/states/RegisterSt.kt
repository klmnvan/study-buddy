package com.example.studybuddy.data.states

data class RegisterSt(
    var nickname: String = "",
    var email: String = "user@example.com",
    var password: String = "12345678",
    var confirmPassword: String = "12345678",
)
