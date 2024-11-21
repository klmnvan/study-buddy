package com.example.studybuddy.domain.converters

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun DateToTimestamp(dateString: String): Long? {
    try {
        val localDate = LocalDate.parse(dateString)
        val timestamp = localDate.toEpochDay() * 86400 // секунды с эпохи
        return timestamp
    } catch (e: DateTimeParseException) {
        Log.d("Ошибка преобразования даты", e.message.toString())
        return null
    }
}