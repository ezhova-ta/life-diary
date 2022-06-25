package com.example.lifediary.domain.usecases.post_addresses

import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.domain.repositories.PostAddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostAddressesUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	operator fun invoke(): Flow<List<PostAddress>> {
		return postAddressRepository.getAllAddresses()
	}
}