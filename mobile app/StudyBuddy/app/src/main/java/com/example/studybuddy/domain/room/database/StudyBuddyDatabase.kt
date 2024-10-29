package com.example.studybuddy.domain.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.domain.room.dao.DisciplineDao
import com.example.studybuddy.domain.room.dao.ExamDao
import com.example.studybuddy.domain.room.dao.TaskDao

@Database(
    entities = [TaskEnt::class, DisciplineEnt::class, ExamEnt::class],
    version = 1,
    exportSchema = false
)
abstract class StudyBuddyDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val disciplineDao: DisciplineDao
    abstract val examDao: ExamDao

}