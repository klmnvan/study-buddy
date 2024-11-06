package com.example.studybuddy.domain.network

import com.example.studybuddy.data.dto.CreateTaskDto
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.responses.DefaultResp
import com.example.studybuddy.data.responses.ExamsResp
import com.example.studybuddy.data.responses.TasksResp
import com.example.studybuddy.data.responses.AuthResp

/** Интерфейс, в котором описаны все методы для запросов к API и создаётся объект ApiServiceImpl */
interface ApiService {

    suspend fun signIn(email: String, password: String): AuthResp
    suspend fun signUp(email: String, password: String, passwordConf: String, nickname: String): AuthResp
    suspend fun getTasks(token: String): TasksResp
    suspend fun getExams(token: String): ExamsResp
    suspend fun updateTask(token: String, task: TaskEnt): DefaultResp
    suspend fun deleteTask(token: String, task: TaskEnt): DefaultResp
    suspend fun createTask(token: String, task: CreateTaskDto): TasksResp

}