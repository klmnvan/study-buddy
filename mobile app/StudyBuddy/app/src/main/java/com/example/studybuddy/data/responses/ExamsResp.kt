package com.example.studybuddy.data.responses

import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.NoteEnt

data class ExamsResp (
    val listExams: List<ExamEnt> = listOf(),
    val listNotes: List<NoteEnt> = listOf(),
    val error: String = "",
)