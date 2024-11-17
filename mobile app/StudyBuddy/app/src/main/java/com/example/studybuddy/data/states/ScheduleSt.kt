package com.example.studybuddy.data.states

import com.example.studybuddy.data.modelsitreshalo.Cabinet
import com.example.studybuddy.data.modelsitreshalo.Group
import com.example.studybuddy.data.modelsitreshalo.Teacher

data class ScheduleSt(
    var groups: List<Group> = listOf(),
    var teachers: List<Teacher> = listOf(),
    var cabinets: List<Cabinet> = listOf(),
    var selGroup: Group = Group(id = 0, name = "Не выбрано")
)