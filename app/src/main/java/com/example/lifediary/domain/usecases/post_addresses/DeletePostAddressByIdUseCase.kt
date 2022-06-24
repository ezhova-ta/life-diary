package com.example.lifediary.domain.usecases.post_addresses

import com.example.lifediary.data.repositories.PostAddressRepository
import javax.inject.Inject

class DeletePostAddressByIdUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	suspend operator fun invoke(id: Long) {
		postAddressRepository.deleteAddress(id)
	}
}