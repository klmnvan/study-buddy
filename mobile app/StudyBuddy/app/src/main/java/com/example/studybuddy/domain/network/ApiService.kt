package com.example.studybuddy.domain.network

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

    //тут будут запросы к API
    suspend fun signIn(email: String, password: String): LoginResp
    suspend fun signUp(email: String, password: String, passwordConf: String, nickname: String): RegisterResp

    companion object {
        fun create(): ApiServiceImpl {
            return ApiServiceImpl(
                client = HttpClient(Android){
                    expectSuccess = true
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation){
                        json(
                            Json {
                                encodeDefaults = false
                                ignoreUnknownKeys = true
                                isLenient = true
                                useAlternativeNames = false
                            })
                    }

                }
            )
        }
    }

}