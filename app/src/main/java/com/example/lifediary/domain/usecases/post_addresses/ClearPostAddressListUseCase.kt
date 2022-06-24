package com.example.lifediary.domain.usecases.post_addresses

import com.example.lifediary.data.repositories.PostAddressRepository
import javax.inject.Inject

class ClearPostAddressListUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	suspend operator fun invoke() {
		postAddressRepository.clearAddresses()
	}
}