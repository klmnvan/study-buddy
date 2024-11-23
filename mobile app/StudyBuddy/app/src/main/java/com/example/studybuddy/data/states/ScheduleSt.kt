package com.example.studybuddy.data.states

import com.example.studybuddy.data.modelsitreshalo.Cabinet
import com.example.studybuddy.data.entityes.GroupEnt
import com.example.studybuddy.data.modelsitreshalo.Teacher
import com.example.studybuddy.data.modelsitreshalo.ValuesSchedule

data class ScheduleSt(
    var group: List<GroupEnt> = listOf(),
    var teachers: List<Teacher> = listOf(),
    var cabinets: List<Cabinet> = listOf(),
    val valuesSchedule: ValuesSchedule = ValuesSchedule(),
    var selGroup: GroupEnt = GroupEnt(id = 42, name = "33П")
)