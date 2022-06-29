package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.forecast.WeatherForecastTemperatureDto
import com.example.lifediary.domain.models.WeatherForecastTemperature

object WeatherForecastTemperatureDtoMapper :
	DtoMapper<WeatherForecastTemperatureDto, WeatherForecastTemperature> {

	override fun WeatherForecastTemperatureDto.toDomain(): WeatherForecastTemperature {
		return WeatherForecastTemperature(
			day = day.toInt(),
			night = night.toInt(),
			morning = morning.toInt(),
			min = min?.toInt(),
			max = max?.toInt()
		)
	}
}