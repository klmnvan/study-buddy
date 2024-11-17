package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

/** Модель пары: время начала, время окончания пары, номер пары */
@Serializable
data class Bell(
    val end_time: String,
    val para: Int,
    val start_time: String
)