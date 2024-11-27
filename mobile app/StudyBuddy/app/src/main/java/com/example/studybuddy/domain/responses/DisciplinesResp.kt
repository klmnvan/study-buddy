package com.example.studybuddy.domain.responses

import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TeacherEnt

data class DisciplinesResp(
    val listDiscipline: List<DisciplineEnt>? = null,
    val listTeachers: List<TeacherEnt>? = null,
    var listRequirements: List<RequirementEnt>? = null,
    val disc: DisciplineEnt = DisciplineEnt(),
    val teacher: TeacherEnt? = null,
    val error: String = "",
)

