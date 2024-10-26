package com.example.studybuddy.data.entityes

import kotlinx.serialization.Serializable

@Serializable
data class TaskEnt(
    val idTask: Int,
    val idUser: String,
    val deadline: String,
    val description: String,
    val title: String,
    val idDiscipline: Int,
    val isCompleted: Boolean,
)