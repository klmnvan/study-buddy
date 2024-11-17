package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

@Serializable
data class Values(
    val redirected: Boolean = false,
    val result: Result = Result()
)

