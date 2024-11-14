package com.example.studybuddy.data.states

import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.NoteEnt
import com.example.studybuddy.data.entityes.RequirementEnt

data class ExamsSt(
    var exams: List<ExamEnt> = listOf(),
    var notes: List<NoteEnt> = listOf(),
    var notesByExam: List<NoteEnt> = listOf(),
)