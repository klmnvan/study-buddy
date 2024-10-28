package com.example.studybuddy.data.responses

import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt

data class GetTasksResp (
    val listTask: List<TaskEnt> = listOf(),
    val listDisc: List<DisciplineEnt> = listOf(),
    val error: String = "",
)