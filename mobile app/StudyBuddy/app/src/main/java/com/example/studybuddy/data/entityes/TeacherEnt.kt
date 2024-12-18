package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "teachers")
data class TeacherEnt(
    @PrimaryKey
    var idTeacher: Int,
    var idUser: String,
    var fullName: String,
    var officeNumber: Int?,
)

