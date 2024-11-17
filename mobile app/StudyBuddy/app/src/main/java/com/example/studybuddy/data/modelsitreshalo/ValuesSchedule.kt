package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

@Serializable
data class ValuesSchedule(
    val redirected: Boolean = false,
    val result: ResultSchedule = ResultSchedule()
)