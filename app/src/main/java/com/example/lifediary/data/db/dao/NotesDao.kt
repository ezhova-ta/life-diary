package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.NotesEntity

@Dao
interface NotesDao {
    @Query("SELECT * FROM note LIMIT 1")
    fun get(): NotesEntity?

    @Query("SELECT * FROM note LIMIT 1")
    fun getLiveData(): LiveData<NotesEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: NotesEntity)

    @Query("DELETE FROM note")
    suspend fun delete()
}