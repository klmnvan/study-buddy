package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDisc(task: List<DisciplineEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDisc(task: DisciplineEnt)

    @Query("DELETE FROM disciplines")
    fun deleteAllDisc()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDisc(disc: DisciplineEnt)

    @Delete
    fun deleteDisc(disc: DisciplineEnt)

    @Query("SELECT * FROM disciplines")
    fun getAllDiscs(): Flow<List<DisciplineEnt>>

}

