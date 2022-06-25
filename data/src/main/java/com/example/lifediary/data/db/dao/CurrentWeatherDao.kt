package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.models.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {
    @Query("SELECT * FROM current_weather LIMIT 1")
    fun get(): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity)

    @Query("DELETE FROM current_weather")
    suspend fun delete()
}