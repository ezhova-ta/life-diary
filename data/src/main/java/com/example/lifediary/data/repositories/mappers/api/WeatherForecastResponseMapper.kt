package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.forecast.WeatherForecastItemDto
import com.example.lifediary.data.api.weather.forecast.WeatherForecastResponse
import com.example.lifediary.data.repositories.mappers.api.WeatherForecastItemDtoMapper.toDomain
import com.example.lifediary.domain.models.WeatherForecast
import com.example.lifediary.domain.models.WeatherForecastItem

object WeatherForecastResponseMapper : DtoMapper<WeatherForecastResponse, WeatherForecast> {
	override fun WeatherForecastResponse.toDomain(): WeatherForecast {
		return WeatherForecast(
			items = forecast.items.toDomain()
		)
	}

	private fun List<WeatherForecastItemDto>.toDomain(): List<WeatherForecastItem> {
		return map { it.toDomain() }
	}
}