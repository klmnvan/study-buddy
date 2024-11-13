package com.example.studybuddy.data.responses

import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt

data class TasksResp (
    val listTask: MutableList<TaskEnt> = mutableListOf(),
    val listDisc: MutableList<DisciplineEnt> = mutableListOf(),
    val task: TaskEnt = TaskEnt(),
    val error: String = "",
)

