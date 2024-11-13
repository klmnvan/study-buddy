package com.example.studybuddy.data.states

import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TeacherEnt

data class DisciplinesSt(
    var disciplines: List<DisciplineEnt> = listOf(),
    var teachers: List<TeacherEnt> = listOf(),
    var requirements: List<RequirementEnt> = listOf(),
    var requirementsByDisc: List<RequirementEnt> = listOf(),
)

