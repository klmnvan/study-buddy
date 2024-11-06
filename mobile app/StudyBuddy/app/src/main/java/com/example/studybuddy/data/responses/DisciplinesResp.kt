package com.example.studybuddy.data.responses

import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TeacherEnt

data class DisciplinesResp(
    val listDiscipline: List<DisciplineEnt>? = null,
    val listTeachers: List<TeacherEnt>? = null,
    val disc: DisciplineEnt = DisciplineEnt(),
    val error: String = "",
)
