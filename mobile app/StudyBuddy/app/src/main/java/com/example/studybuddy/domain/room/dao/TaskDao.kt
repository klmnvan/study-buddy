package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studybuddy.data.entityes.TaskEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskEnt)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEnt>>

}
