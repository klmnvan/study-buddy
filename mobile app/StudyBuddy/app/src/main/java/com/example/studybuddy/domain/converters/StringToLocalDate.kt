package com.example.studybuddy.domain.converters

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun StringToLocalDate(dateString: String): LocalDate? {
    return try {
        LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    } catch (e: Exception) {
        null
    }
}