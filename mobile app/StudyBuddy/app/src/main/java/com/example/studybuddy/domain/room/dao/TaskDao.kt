package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studybuddy.data.entityes.TaskEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: List<TaskEnt>)

    @Query("DELETE FROM tasks")
    fun deleteAllTask()

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEnt>>

    @Query("SELECT * FROM tasks WHERE isCompleted = TRUE")
    fun getCompletedTasks(): Flow<List<TaskEnt>>

}
