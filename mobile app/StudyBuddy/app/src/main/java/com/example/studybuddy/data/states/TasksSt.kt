package com.example.studybuddy.data.states

import com.example.studybuddy.data.entityes.TaskEnt

data class TasksSt(
    var tasks: List<TaskEnt> = listOf()
)
