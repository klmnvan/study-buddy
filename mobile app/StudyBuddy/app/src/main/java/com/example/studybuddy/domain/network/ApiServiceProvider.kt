package com.example.studybuddy.domain.network

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studybuddy.domain.room.dao.DisciplineDao
import com.example.studybuddy.domain.room.dao.TaskDao
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
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
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
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
                register(
                    ContentType.Text.Html, KotlinxSerializationConverter(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                )
            }
        }
    }

    @Provides
    fun provideTaskDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        StudyBuddyDatabase::class.java,
        "tasks"
    ).allowMainThreadQueries().build()

    @Provides
    fun provideService(client: HttpClient, database: StudyBuddyDatabase): ApiServiceImpl {
        return ApiServiceImpl(client, database)
    }

}