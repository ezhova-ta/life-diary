package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.Weather

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
) : DbEntity<Weather>() {
    companion object {
        fun fromDomain(weather: Weather): WeatherEntity {
            return WeatherEntity(
                id = weather.id,
                shortDescription = weather.shortDescription,
                description = weather.description,
                icon = weather.icon,
                temperature = weather.temperature,
                temperatureFeelsLike = weather.temperatureFeelsLike,
                pressure = weather.pressure,
                humidity = weather.humidity,
                windSpeed = weather.windSpeed,
                gustSpeed = weather.gustSpeed
            )
        }
    }

    override fun toDomain(): Weather {
        return Weather(
            id = id,
            shortDescription = shortDescription,
            description = description,
            icon = icon,
            temperature = temperature,
            temperatureFeelsLike = temperatureFeelsLike,
            pressure = pressure,
            humidity = humidity,
            windSpeed = windSpeed,
            gustSpeed = gustSpeed
        )
    }
}