package com.example.studybuddy.data.responses

import com.example.studybuddy.data.modelsitreshalo.Values
import com.example.studybuddy.data.modelsitreshalo.ValuesSchedule

data class ScheduleResp(
    val values: Values = Values(),
    val valuesSchedule: ValuesSchedule = ValuesSchedule(),
    val error: String = ""
)