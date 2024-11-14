package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.states.ExamsSt
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExam(task: List<ExamEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExam(task: ExamEnt)

    @Delete
    fun deleteExam(exam: ExamEnt)

    @Query("DELETE FROM exams")
    fun deleteAllExams()

    @Query("SELECT * FROM exams")
    fun getAllExams(): Flow<List<ExamEnt>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateExam(exam: ExamEnt)


}