package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.CurrentWeatherResponse
import com.example.lifediary.data.utils.getMetersPerSecondFromKmPerHour
import com.example.lifediary.domain.models.Weather

object CurrentWeatherResponseMapper : DtoMapper<CurrentWeatherResponse, Weather> {
	override fun CurrentWeatherResponse.toDomain(): Weather {
		return Weather(
			temperature = parameters.temperature.toInt(),
			temperatureFeelsLike = parameters.temperatureFeelsLike.toInt(),
			humidity = parameters.humidity,
			windSpeedMetersPerSecond = getMetersPerSecondFromKmPerHour(parameters.windSpeedKmPerHour),
			text = parameters.condition.text,
			iconUrl = parameters.condition.iconUrl
		)
	}
}