package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun get(dayNumber: Int, monthNumber: Int, year: Int): NoteEntity?

    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun getLiveData(dayNumber: Int, monthNumber: Int, year: Int): LiveData<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun delete(id: Long)
}