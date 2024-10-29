package com.example.studybuddy.data.states

import com.example.studybuddy.data.entityes.ExamEnt

data class ExamsSt(
    var exams: List<ExamEnt> = listOf()
)