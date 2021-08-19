package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note LIMIT 1")
    fun get(): NoteEntity?

    @Query("SELECT * FROM note LIMIT 1")
    fun getLiveData(): LiveData<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: NoteEntity)

    @Query("DELETE FROM note")
    suspend fun delete()
}