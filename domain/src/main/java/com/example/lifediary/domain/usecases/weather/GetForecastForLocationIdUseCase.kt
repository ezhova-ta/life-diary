package com.example.lifediary.domain.usecases.weather

import com.example.lifediary.domain.models.WeatherForecast
import com.example.lifediary.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetForecastForLocationIdUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	suspend operator fun invoke(locationId: Long): WeatherForecast {
		return weatherRepository.getForecast(locationId)
	}
}