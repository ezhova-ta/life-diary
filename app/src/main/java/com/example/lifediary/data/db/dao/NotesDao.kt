package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.db.entities.NotesEntity
import java.util.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun get(dayNumber: Int, monthNumber: Int, year: Int): NotesEntity?

    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<NotesEntity>>

    @Query("SELECT * FROM note WHERE day = :dayNumber AND month = :monthNumber AND year = :year LIMIT 1")
    fun getLiveData(dayNumber: Int, monthNumber: Int, year: Int): LiveData<NotesEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: NotesEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun delete(id: Long)
}