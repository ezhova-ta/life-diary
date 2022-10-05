package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.models.DateNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DateNoteDao {
    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    suspend fun get(dayNumber: Int, monthNumber: Int, year: Int): DateNoteEntity?

    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun getFlow(dayNumber: Int, monthNumber: Int, year: Int): Flow<DateNoteEntity?>

    @Query("SELECT * FROM note")
    fun getFlowAll(): Flow<List<DateNoteEntity>>

    @Query("SELECT * FROM note")
    suspend fun getAll(): List<DateNoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: DateNoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<DateNoteEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: DateNoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}