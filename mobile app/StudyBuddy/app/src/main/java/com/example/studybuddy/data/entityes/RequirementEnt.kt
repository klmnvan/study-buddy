package com.example.studybuddy.data.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "requirements")
data class RequirementEnt(
    @PrimaryKey
    var idRequirement: Int = 0,
    var idDiscipline: Int = 0,
    var content: String = "",
)