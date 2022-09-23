package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.models.MainNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainNotesDao {
    @Query("SELECT * FROM main_note ORDER BY created_at DESC")
    fun getFlowAll(): Flow<List<MainNoteEntity>>

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