package com.example.studybuddy.domain.converters

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun ConvertLongToTime(time: LocalDate): String {
    try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = time.format(formatter)
        return formattedDate
    } catch (e: Exception) {
        return ""
    }
}

