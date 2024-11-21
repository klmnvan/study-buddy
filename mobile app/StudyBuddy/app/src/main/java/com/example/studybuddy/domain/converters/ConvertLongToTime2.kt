package com.example.studybuddy.domain.converters

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun ConvertLongToTime2(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(date)
}

