package com.example.lifediary.domain.usecases.post_addresses

import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.domain.repositories.PostAddressRepository
import javax.inject.Inject

class AddPostAddressUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	suspend operator fun invoke(address: PostAddress) {
		postAddressRepository.addAddress(address)
	}
}