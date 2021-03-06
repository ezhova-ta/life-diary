package com.example.lifediary.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lifediary.data.db.models.MenstruationPeriodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenstruationPeriodDao {
    @Query("SELECT * FROM menstruation_dates ORDER BY start_date DESC")
    fun getAll(): Flow<List<MenstruationPeriodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MenstruationPeriodEntity)

    @Query("DELETE FROM menstruation_dates WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM menstruation_dates")
    suspend fun deleteAll()
}