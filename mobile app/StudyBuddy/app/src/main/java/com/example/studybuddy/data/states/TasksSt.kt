package com.example.studybuddy.data.states

import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt

data class TasksSt(
    var tasks: List<TaskEnt> = listOf(),
    var disciplines: List<DisciplineEnt> = listOf()
)
