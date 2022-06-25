package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.PostAddress
import kotlinx.coroutines.flow.Flow

interface PostAddressRepository {
	fun getAllAddresses(): Flow<List<PostAddress>>
	suspend fun addAddress(address: PostAddress)
	suspend fun updateAddress(address: PostAddress)
	suspend fun deleteAddress(id: Long)
	suspend fun clearAddresses()
}