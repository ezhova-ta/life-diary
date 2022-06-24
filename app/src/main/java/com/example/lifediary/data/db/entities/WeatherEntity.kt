package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class WeatherEntity(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "short_description")
    val shortDescription: String?,
    val description: String?,
    val icon: String?,
    val temperature: Int,
    @ColumnInfo(name = "temperature_feels_like")
    val temperatureFeelsLike: Int,
    val pressure: Int,
    val humidity: Int,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    @ColumnInfo(name = "gust_speed")
    val gustSpeed: Double
)