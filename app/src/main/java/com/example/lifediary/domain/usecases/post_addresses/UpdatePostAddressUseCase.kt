package com.example.lifediary.domain.usecases.post_addresses

import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.data.repositories.PostAddressRepository
import javax.inject.Inject

class UpdatePostAddressUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	suspend operator fun invoke(address: PostAddress) {
		postAddressRepository.updateAddress(address)
	}
}