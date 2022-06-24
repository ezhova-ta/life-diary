package com.example.lifediary.domain.usecases.post_addresses

import com.example.lifediary.data.domain.PostAddress
import com.example.lifediary.data.repositories.PostAddressRepository
import javax.inject.Inject

class AddPostAddressUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	suspend operator fun invoke(address: PostAddress) {
		postAddressRepository.addAddress(address)
	}
}