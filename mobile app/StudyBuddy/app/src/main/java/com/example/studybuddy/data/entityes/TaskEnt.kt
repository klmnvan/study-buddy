package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tasks")
data class TaskEnt(
    @PrimaryKey()
    val idTask: Int,
    val idUser: String,
    val deadline: String,
    val description: String,
    val title: String,
    val idDiscipline: Int,
    val isCompleted: Boolean,
)