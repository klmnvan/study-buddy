package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.GroupEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(group: List<GroupEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(group: GroupEnt)

    @Delete
    fun deleteGroup(group: GroupEnt)

    @Query("DELETE FROM groups")
    fun deleteAllGroups()

    @Query("SELECT * FROM groups")
    fun getAllGroups(): Flow<List<GroupEnt>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGroup(group: GroupEnt)


}