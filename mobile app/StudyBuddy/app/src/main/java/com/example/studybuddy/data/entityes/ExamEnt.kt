package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "exams")
@Serializable
data class ExamEnt(
    @PrimaryKey
    var idExam: Int,
    var duration: String,
    var numberTickets: Int,
    var dateExam: String,
    var idUser: String,
    var title: String,
)