package com.example.lifediary.domain.usecases.location

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import javax.inject.Inject

class GetLocationLiveDataUseCase @Inject constructor(
	private val weatherRepository: WeatherRepository
) {
	operator fun invoke(): LiveData<Location?> {
		return weatherRepository.getLocationLiveData()
	}
}