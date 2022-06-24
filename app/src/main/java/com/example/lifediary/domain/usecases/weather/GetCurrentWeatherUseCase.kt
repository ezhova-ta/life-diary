package com.example.lifediary.domain.usecases.weather

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.Weather
import com.example.lifediary.data.repositories.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	operator fun invoke(): LiveData<Weather?> {
		return weatherRepository.getCurrentWeather()
	}
}