package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.WeatherDescriptionDto
import com.example.lifediary.data.api.weather.forecast.WeatherForecastItemDto
import com.example.lifediary.data.repositories.mappers.api.WeatherDescriptionDtoMapper.toDomain
import com.example.lifediary.data.repositories.mappers.api.WeatherForecastTemperatureDtoMapper.toDomain
import com.example.lifediary.domain.models.WeatherDescription
import com.example.lifediary.domain.models.WeatherForecastItem

object WeatherForecastItemDtoMapper : DtoMapper<WeatherForecastItemDto, WeatherForecastItem> {
	override fun WeatherForecastItemDto.toDomain(): WeatherForecastItem {
		return WeatherForecastItem(
			dateInSeconds = dateInSeconds,
			temperature = temperature.toDomain(),
			temperatureFeelsLike = temperatureFeelsLike.toDomain(),
			pressure = pressure,
			humidity = humidity,
			weather = weather.toDomain(),
			windSpeed = windSpeed,
			gustSpeed = gustSpeed,
			clouds = clouds,
			rain = rain
		)
	}

	private fun List<WeatherDescriptionDto>.toDomain(): List<WeatherDescription> {
		return map { it.toDomain() }
	}
}