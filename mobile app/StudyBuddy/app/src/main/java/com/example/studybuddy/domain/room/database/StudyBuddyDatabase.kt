package com.example.studybuddy.domain.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.domain.room.dao.DisciplineDao
import com.example.studybuddy.domain.room.dao.ExamDao
import com.example.studybuddy.domain.room.dao.TaskDao
import com.example.studybuddy.domain.room.dao.TeacherDao

@Database(
    entities = [TaskEnt::class, DisciplineEnt::class, ExamEnt::class, TeacherEnt::class],
    version = 1,
    exportSchema = false
)
abstract class StudyBuddyDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val disciplineDao: DisciplineDao
    abstract val examDao: ExamDao
    abstract val teacherDao: TeacherDao

}