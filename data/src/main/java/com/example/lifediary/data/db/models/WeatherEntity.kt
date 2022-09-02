package com.example.lifediary.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class WeatherEntity(
    @PrimaryKey
    val id: Long?,
    val temperature: Int,
    @ColumnInfo(name = "temperature_feels_like")
    val temperatureFeelsLike: Int,
    val humidity: Int,
    @ColumnInfo(name = "wind_speed_meters_per_second", defaultValue = "0.0")
    val windSpeedMetersPerSecond: Double,
    @ColumnInfo(name = "icon_url", defaultValue = "NULL")
    val iconUrl: String?,
    @ColumnInfo(defaultValue = "NULL")
    val text: String?
)