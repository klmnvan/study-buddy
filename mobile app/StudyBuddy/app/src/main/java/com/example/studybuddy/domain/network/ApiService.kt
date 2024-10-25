package com.example.studybuddy.domain.network

import com.example.studybuddy.data.responses.LoginResp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ApiService {

    //тут будут запросы к API
    suspend fun signIn(email: String, password: String): LoginResp

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