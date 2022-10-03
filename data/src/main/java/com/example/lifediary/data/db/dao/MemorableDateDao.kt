package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.models.MemorableDateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemorableDateDao {
    @Query("SELECT * FROM memorable_date ORDER BY month ASC, day ASC, year DESC")
    fun getFlowAll(): Flow<List<MemorableDateEntity>>

    @Query("SELECT * FROM memorable_date ORDER BY month ASC, day ASC, year DESC")
    fun getAll(): List<MemorableDateEntity>

    @Query("SELECT * FROM memorable_date WHERE day = :dayNumber AND month = :monthNumber ORDER BY month ASC, day ASC, year DESC")
    fun getFlowAll(dayNumber: Int, monthNumber: Int): Flow<List<MemorableDateEntity>>

    @Query("SELECT * FROM memorable_date WHERE id = :id")
    suspend fun get(id: Long): MemorableDateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MemorableDateEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: MemorableDateEntity)

    @Query("DELETE FROM memorable_date WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM memorable_date")
    suspend fun deleteAll()
}