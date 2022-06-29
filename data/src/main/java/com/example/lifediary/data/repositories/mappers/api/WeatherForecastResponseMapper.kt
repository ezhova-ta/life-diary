package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.forecast.WeatherForecastResponse
import com.example.lifediary.data.repositories.mappers.api.WeatherForecastItemDtoMapper.toDomain
import com.example.lifediary.domain.models.WeatherForecast

object WeatherForecastResponseMapper : DtoMapper<WeatherForecastResponse, WeatherForecast> {
	override fun WeatherForecastResponse.toDomain(): WeatherForecast {
		return WeatherForecast(
			items = items.map { it.toDomain() }
		)
	}
}