package com.example.lifediary.domain.usecases.location

import com.example.lifediary.domain.models.Location
import com.example.lifediary.data.repositories.WeatherRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	suspend operator fun invoke(location: Location) {
		weatherRepository.saveLocation(location)
	}
}