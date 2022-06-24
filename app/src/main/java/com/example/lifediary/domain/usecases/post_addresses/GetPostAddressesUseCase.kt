package com.example.lifediary.domain.usecases.post_addresses

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.domain.repositories.PostAddressRepository
import javax.inject.Inject

class GetPostAddressesUseCase @Inject constructor(
	private val postAddressRepository: PostAddressRepository
) {
	operator fun invoke(): LiveData<List<PostAddress>> {
		return postAddressRepository.getAllAddresses()
	}
}