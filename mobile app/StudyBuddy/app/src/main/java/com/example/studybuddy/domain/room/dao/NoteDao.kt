package com.example.studybuddy.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studybuddy.data.entityes.NoteEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(notes: List<NoteEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteEnt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: NoteEnt)

    @Delete
    fun deleteNote(note: NoteEnt)

    @Query("DELETE FROM notes")
    fun deleteAllNote()

    @Query("DELETE FROM notes WHERE idExam = :idExam")
    fun deleteAllNoteByIdExam(idExam: Int)

    @Query("SELECT * FROM notes WHERE idExam = :idExam")
    fun getAllNoteByIdExam(idExam: Int): Flow<List<NoteEnt>>

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEnt>>

}