package com.example.lifediary.domain.usecases.location

import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationLiveDataUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	operator fun invoke(): Flow<Location?> {
		return weatherRepository.getLocationFlow()
	}
}