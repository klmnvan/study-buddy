package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studybuddy.data.entityes.DisciplineEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDisc(task: List<DisciplineEnt>)

    @Query("DELETE FROM disciplines")
    fun deleteAllDisc()

    @Query("SELECT * FROM disciplines")
    fun getAllDiscs(): Flow<List<DisciplineEnt>>

}

