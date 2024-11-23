package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "groups")
@Serializable
data class GroupEnt(
    @PrimaryKey
    var id: Int = 0,
    var course: Int? = 0,
    var name: String? = ""
)