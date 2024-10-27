package com.example.studybuddy.domain.room.repository

import android.content.Context
import androidx.room.Room
import com.example.studybuddy.domain.room.dao.TaskDao
import com.example.studybuddy.domain.room.database.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/** Hilt Модуль, в котором описано как получать зависимость  с типом ApiServiceImpl  */
@Module
@InstallIn(SingletonComponent::class)
class TasksRepositoryProvider {

    @Provides
    fun provideTaskDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        TasksDatabase::class.java,
        "tasks"
    ).build()

    @Provides
    fun provideDao(db: TasksDatabase): TaskDao {
        return db.taskDao
    }

}
