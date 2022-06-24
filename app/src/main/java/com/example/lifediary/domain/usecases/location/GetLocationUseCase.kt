package com.example.lifediary.domain.usecases.location

import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	suspend operator fun invoke(): Location? {
		return weatherRepository.getLocation()
	}
}