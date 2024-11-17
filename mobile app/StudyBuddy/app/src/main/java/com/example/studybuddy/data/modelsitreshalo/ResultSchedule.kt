package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

@Serializable
data class ResultSchedule(
    val bell: List<Bell> = listOf(),
    val change: List<Change> = listOf(),
    val change_status: String = "",
    val main: List<Main> = listOf(),
)