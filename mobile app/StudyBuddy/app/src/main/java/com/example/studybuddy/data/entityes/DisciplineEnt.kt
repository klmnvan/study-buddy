package com.example.studybuddy.data.entityes

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity(tableName = "disciplines")
@Serializable
data class DisciplineEnt(
    var idDiscipline: Int,
    var title: String,
    var idTeacher: Int?,
    var idUser: String,
)
