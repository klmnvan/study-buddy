package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.RequirementEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface RequirementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReq(requirements: List<RequirementEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReq(requirement: RequirementEnt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateReq(requirement: RequirementEnt)

    @Delete
    fun deleteReq(requirement: RequirementEnt)

    @Query("DELETE FROM requirements")
    fun deleteAllReq()

    @Query("DELETE FROM requirements WHERE idDiscipline = :idDisc")
    fun deleteAllReqByIdDisc(idDisc: Int)

    @Query("SELECT * FROM requirements WHERE idDiscipline = :idDisc")
    fun getAllReqByIdDisc(idDisc: Int): Flow<List<RequirementEnt>>

    @Query("SELECT * FROM requirements")
    fun getAllReqs(): Flow<List<RequirementEnt>>

}

