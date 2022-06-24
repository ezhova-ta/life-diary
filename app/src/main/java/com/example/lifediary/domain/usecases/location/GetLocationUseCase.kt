package com.example.lifediary.domain.usecases.location

import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	suspend operator fun invoke(): Location? {
		return weatherRepository.getLocation()
	}
}