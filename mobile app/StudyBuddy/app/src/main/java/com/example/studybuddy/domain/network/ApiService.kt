package com.example.studybuddy.domain.network

import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.responses.DefaultResp
import com.example.studybuddy.data.responses.GetExamsResp
import com.example.studybuddy.data.responses.GetTasksResp
import com.example.studybuddy.data.responses.LoginResp
import com.example.studybuddy.data.responses.RegisterResp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/** Интерфейс, в котором описаны все методы для запросов к API и создаётся объект ApiServiceImpl */
interface ApiService {

    suspend fun signIn(email: String, password: String): LoginResp
    suspend fun signUp(email: String, password: String, passwordConf: String, nickname: String): RegisterResp
    suspend fun getTasks(token: String): GetTasksResp
    suspend fun getExams(token: String): GetExamsResp
    suspend fun updateTask(token: String, task: TaskEnt): DefaultResp

}