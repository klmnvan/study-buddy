package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tasks")
data class TaskEnt(
    @PrimaryKey
    var idTask: Int = 0,
    var idUser: String = "",
    var deadline: String = "",
    var description: String = "",
    var title: String = "",
    var idDiscipline: Int? = null,
    var isCompleted: Boolean = false,
)

