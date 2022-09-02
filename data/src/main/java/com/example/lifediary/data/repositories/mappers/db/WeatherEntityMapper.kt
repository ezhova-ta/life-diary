package com.example.lifediary.data.repositories.mappers.db

import com.example.lifediary.data.db.models.WeatherEntity
import com.example.lifediary.domain.models.Weather

object WeatherEntityMapper : EntityMapper<WeatherEntity, Weather> {
	override fun WeatherEntity.toDomain(): Weather {
		return Weather(
			id = id,
			temperature = temperature,
			temperatureFeelsLike = temperatureFeelsLike,
			humidity = humidity,
			windSpeedMetersPerSecond = windSpeedMetersPerSecond,
			text = text,
			iconUrl = iconUrl
		)
	}

	override fun Weather.toEntity(): WeatherEntity {
		return WeatherEntity(
			id = id,
			temperature = temperature,
			temperatureFeelsLike = temperatureFeelsLike,
			humidity = humidity,
			windSpeedMetersPerSecond = windSpeedMetersPerSecond,
			text = text,
			iconUrl = iconUrl
		)
	}
}