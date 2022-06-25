package com.example.lifediary.domain.usecases.weather

import com.example.lifediary.domain.models.Weather
import com.example.lifediary.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	operator fun invoke(): Flow<Weather?> {
		return weatherRepository.getCurrentWeather()
	}
}