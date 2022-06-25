package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.models.MenstruationPeriodEntity

@Dao
interface MenstruationPeriodDao {
    @Query("SELECT * FROM menstruation_dates ORDER BY start_date DESC")
    fun getAll(): LiveData<List<MenstruationPeriodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MenstruationPeriodEntity)

    @Query("DELETE FROM menstruation_dates WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM menstruation_dates")
    suspend fun deleteAll()
}