package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.DateNoteEntity

@Dao
interface DateNoteDao {
    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun get(dayNumber: Int, monthNumber: Int, year: Int): DateNoteEntity?

    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<DateNoteEntity>>

    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun getLiveData(dayNumber: Int, monthNumber: Int, year: Int): LiveData<DateNoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: DateNoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: DateNoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}