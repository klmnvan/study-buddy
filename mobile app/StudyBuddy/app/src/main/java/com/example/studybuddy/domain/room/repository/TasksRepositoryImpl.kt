package com.example.studybuddy.domain.room.repository

import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.domain.CachedData
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.domain.room.dao.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val newsDao: TaskDao,
    private val service: ApiServiceImpl
): TasksRepository {

    override suspend fun fetchTasks() {
        val tasksList = service.getTasks(CachedData.token)
        tasksList.listTask.forEach {
            newsDao.insertTask(it)
        }
    }

    override fun getAllTasks(): Flow<List<TaskEnt>> {
        return newsDao.getAllTasks()
    }

}