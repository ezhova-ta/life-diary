package com.example.lifediary.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class WeatherEntity(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String?,
    val temperature: Int,
    @ColumnInfo(name = "temperature_feels_like")
    val temperatureFeelsLike: Int,
    val humidity: Int,
    @ColumnInfo(name = "wind_speed_meters_per_second")
    val windSpeedMetersPerSecond: Double,
    @ColumnInfo(defaultValue = "")
    val text: String?
)