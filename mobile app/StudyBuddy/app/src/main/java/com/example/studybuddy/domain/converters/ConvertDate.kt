package com.example.studybuddy.domain.converters

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun ConvertDate(inputDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
        val date = inputFormat.parse(inputDate)
        val formattedDate = outputFormat.format(date)
        formattedDate
    } catch (e: Exception) { inputDate }
}

fun StringToLocalDate(dateString: String): LocalDate? {
    return try {
        LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    } catch (e: Exception) {
        null
    }
}