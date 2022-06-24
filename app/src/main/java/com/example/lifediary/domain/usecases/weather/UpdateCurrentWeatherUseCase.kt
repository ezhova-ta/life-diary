package com.example.lifediary.domain.usecases.weather

import com.example.lifediary.data.repositories.WeatherRepository
import javax.inject.Inject

class UpdateCurrentWeatherUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	suspend operator fun invoke(locationId: Long) {
		weatherRepository.updateCurrentWeather(locationId)
	}
}