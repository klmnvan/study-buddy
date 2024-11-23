package com.example.studybuddy.data.modelsitreshalo

import com.example.studybuddy.data.entityes.GroupEnt
import kotlinx.serialization.Serializable

/** Информация о всех группах, кабинетах и преподавателях */
@Serializable
data class Result(
    val cabinet: List<Cabinet> = listOf(),
    val group: List<GroupEnt> = listOf(),
    val teacher: List<Teacher> = listOf()
)