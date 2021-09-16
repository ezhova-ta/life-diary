package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.MemorableDateEntity

@Dao
interface MemorableDateDao {
    @Query("SELECT * FROM memorable_date ORDER BY month ASC, day ASC, year DESC")
    fun getAll(): LiveData<List<MemorableDateEntity>>

    @Query("SELECT * FROM memorable_date WHERE id = :id")
    suspend fun get(id: Long): MemorableDateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MemorableDateEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: MemorableDateEntity)

    @Delete
    suspend fun delete(item: MemorableDateEntity)

    @Query("DELETE FROM memorable_date WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM memorable_date")
    suspend fun deleteAll()
}