package com.example.lifediary.domain.usecases.woman_section

import androidx.lifecycle.LiveData
import com.example.lifediary.data.repositories.WomanSectionRepository
import javax.inject.Inject

class GetDelayOfMenstruationUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	operator fun invoke(): LiveData<Long?> {
		return womanSectionRepository.getDelayOfMenstruation()
	}
}