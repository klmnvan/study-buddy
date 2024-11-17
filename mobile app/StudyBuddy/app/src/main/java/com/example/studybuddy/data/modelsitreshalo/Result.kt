package com.example.studybuddy.data.modelsitreshalo

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val cabinet: List<Cabinet> = listOf(),
    val group: List<Group> = listOf(),
    val teacher: List<Teacher> = listOf()
)