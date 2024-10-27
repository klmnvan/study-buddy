package com.example.studybuddy.domain.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.domain.room.dao.TaskDao

@Database(
    entities = [TaskEnt::class],
    version = 1,
    exportSchema = false
)
abstract class TasksDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao

}