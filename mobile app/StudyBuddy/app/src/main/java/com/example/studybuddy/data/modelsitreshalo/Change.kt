package com.example.studybuddy.data.modelsitreshalo

import com.example.studybuddy.data.entityes.GroupEnt
import kotlinx.serialization.Serializable

/** Изменения */
@Serializable
data class Change(
    val cabinet: Cabinet?,
    val group: GroupEnt,
    val id: Int,
    val is_distance: Boolean,
    val para: Int,
    val paraFrom: ParaFrom,
    val paraTo: ParaTo?,
    val parallel: Teacher?,
    val schedule_id: Int,
    val type: String
)