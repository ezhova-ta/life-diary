package com.example.lifediary.data.repositories.mappers.db

import com.example.lifediary.data.db.models.WeatherEntity
import com.example.lifediary.domain.models.Weather

object WeatherEntityMapper : EntityMapper<WeatherEntity, Weather> {
	override fun WeatherEntity.toDomain(): Weather {
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

	override fun Weather.toEntity(): WeatherEntity {
		return WeatherEntity(
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