package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

@Serializable
data class Cabinet(
    val building: Building,
    val id: Int,
    val name: String
)