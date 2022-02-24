package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.MenstruationDatesEntity

@Dao
interface MenstruationDatesDao {
    @Query("SELECT * FROM menstruation_dates ORDER BY start_date DESC")
    fun getAll(): LiveData<List<MenstruationDatesEntity>>

    @Query("SELECT * FROM menstruation_dates WHERE id = :id")
    fun get(id: Long): MenstruationDatesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MenstruationDatesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: MenstruationDatesEntity)

    @Delete
    suspend fun delete(item: MenstruationDatesEntity)

    @Query("DELETE FROM menstruation_dates WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM menstruation_dates")
    suspend fun deleteAll()
}