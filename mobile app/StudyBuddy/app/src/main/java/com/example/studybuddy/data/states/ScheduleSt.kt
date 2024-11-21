package com.example.studybuddy.data.states

import com.example.studybuddy.data.modelsitreshalo.Cabinet
import com.example.studybuddy.data.modelsitreshalo.Group
import com.example.studybuddy.data.modelsitreshalo.Teacher
import com.example.studybuddy.data.modelsitreshalo.ValuesSchedule

data class ScheduleSt(
    var groups: List<Group> = listOf(),
    var teachers: List<Teacher> = listOf(),
    var cabinets: List<Cabinet> = listOf(),
    val valuesSchedule: ValuesSchedule = ValuesSchedule(),
    var selGroup: Group = Group(id = 42, name = "33П")
)