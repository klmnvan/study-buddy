package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "disciplines")
@Serializable
data class DisciplineEnt(
    @PrimaryKey
    var idDiscipline: Int,
    var title: String,
    var idTeacher: Int?,
    var idUser: String,
)
