package com.example.studybuddy.domain.network

import android.content.Context
import androidx.room.Room
import com.example.studybuddy.domain.room.dao.TaskDao
import com.example.studybuddy.domain.room.database.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/** Hilt Модуль, в котором описано как получать зависимость  с типом ApiServiceImpl  */
@Module
@InstallIn(SingletonComponent::class)
class ApiServiceProvider {

    @Provides
    fun provideClient(): HttpClient {
        return HttpClient(Android){
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
    }

    @Provides
    fun provideTaskDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        TasksDatabase::class.java,
        "tasks"
    ).allowMainThreadQueries().build()

    @Provides
    fun provideDao(db: TasksDatabase): TaskDao {
        return db.taskDao
    }

    @Provides
    fun provideService(client: HttpClient, taskDao: TaskDao): ApiServiceImpl {
        return ApiServiceImpl(client, taskDao)
    }

}