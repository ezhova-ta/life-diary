package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.WeatherEntity

@Dao
interface CurrentWeatherDao {
    @Query("SELECT * FROM current_weather LIMIT 1")
    fun get(): LiveData<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(weather: WeatherEntity)

    @Query("DELETE FROM current_weather")
    suspend fun delete()
}