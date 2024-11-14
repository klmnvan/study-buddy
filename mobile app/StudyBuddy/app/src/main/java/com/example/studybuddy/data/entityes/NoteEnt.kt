package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "notes")
data class NoteEnt(
    @PrimaryKey
    var idNote: Int,
    var idExam: Int,
    var content: String
)