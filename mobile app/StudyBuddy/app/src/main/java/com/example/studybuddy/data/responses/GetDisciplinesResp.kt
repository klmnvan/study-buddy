package com.example.studybuddy.data.responses

import com.example.studybuddy.data.dto.UserDto
import com.example.studybuddy.data.entityes.DisciplineEnt

data class GetDisciplinesResp(
    val listDiscipline: List<DisciplineEnt>? = null,
    val error: String = "",
)
