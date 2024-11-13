package com.example.studybuddy.domain.converters

import java.text.SimpleDateFormat
import java.util.Locale

fun ConvertDate2(inputDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
        val date = inputFormat.parse(inputDate)
        val formattedDate = outputFormat.format(date)
        formattedDate
    } catch (e: Exception) { inputDate }
}