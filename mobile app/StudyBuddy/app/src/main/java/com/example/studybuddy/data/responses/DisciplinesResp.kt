package com.example.studybuddy.data.responses

import com.example.studybuddy.data.entityes.DisciplineEnt

data class DisciplinesResp(
    val listDiscipline: List<DisciplineEnt>? = null,
    val error: String = "",
)
