package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.TeacherEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeacher(teachers: List<TeacherEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeacher(teacher: TeacherEnt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTeacher(teacher: TeacherEnt)

    @Delete
    fun deleteTeacher(teacher: TeacherEnt)

    @Query("DELETE FROM teachers")
    fun deleteAllTeacher()

    @Query("SELECT * FROM teachers")
    fun getAllTeacher(): Flow<List<TeacherEnt>>

}