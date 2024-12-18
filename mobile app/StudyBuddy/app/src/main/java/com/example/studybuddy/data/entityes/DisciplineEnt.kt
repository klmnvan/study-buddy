package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "disciplines")
@Serializable
data class DisciplineEnt(
    @PrimaryKey
    var idDiscipline: Int = 0,
    var title: String = "",
    var idTeacher: Int? = null,
    var idUser: String = "",
)

