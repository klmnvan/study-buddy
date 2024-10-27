package com.example.studybuddy.domain.room.repository

import com.example.studybuddy.data.entityes.TaskEnt
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun fetchTasks()
    fun getAllTasks(): Flow<List<TaskEnt>>

}