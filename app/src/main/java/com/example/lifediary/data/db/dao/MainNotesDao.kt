package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.MainNoteEntity

@Dao
interface MainNotesDao {
    @Query("SELECT * FROM main_note ORDER BY created_at DESC")
    fun getAll(): LiveData<List<MainNoteEntity>>

    @Query("SELECT * FROM main_note WHERE id = :id")
    suspend fun get(id: Long): MainNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MainNoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: MainNoteEntity)

    @Query("DELETE FROM main_note WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM main_note")
    suspend fun deleteAll()
}