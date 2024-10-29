package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studybuddy.data.entityes.ExamEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExam(task: List<ExamEnt>)

    @Query("DELETE FROM exams")
    fun deleteAllExams()

    @Query("SELECT * FROM exams")
    fun getAllExams(): Flow<List<ExamEnt>>

}