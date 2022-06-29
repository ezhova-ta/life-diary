package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.CurrentWeatherResponse
import com.example.lifediary.domain.models.Weather

object CurrentWeatherResponseMapper : DtoMapper<CurrentWeatherResponse, Weather> {
	override fun CurrentWeatherResponse.toDomain(): Weather {
		return Weather(
			shortDescription = descriptions.firstOrNull()?.shortDescription,
			description = descriptions.firstOrNull()?.description,
			icon = descriptions.firstOrNull()?.icon,
			temperature = parameters.temperature.toInt(),
			temperatureFeelsLike = parameters.temperatureFeelsLike.toInt(),
			pressure = parameters.pressure,
			humidity = parameters.humidity,
			windSpeed = wind.speed,
			gustSpeed = wind.gust
		)
	}
}