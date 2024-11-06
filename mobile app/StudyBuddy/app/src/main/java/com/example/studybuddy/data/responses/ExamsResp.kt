package com.example.studybuddy.data.responses

import com.example.studybuddy.data.entityes.ExamEnt

data class ExamsResp (
    val listExams: List<ExamEnt> = listOf(),
    val error: String = "",
)