package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.PostAddress
import kotlinx.coroutines.flow.Flow

interface PostAddressRepository {
	fun getFlowAllAddresses(): Flow<List<PostAddress>>
	suspend fun getAllAddresses(): List<PostAddress>
	suspend fun addAddress(address: PostAddress)
	suspend fun addAllAddresses(addresses: List<PostAddress>)
	suspend fun updateAddress(address: PostAddress)
	suspend fun deleteAddress(id: Long)
	suspend fun clearAddresses()
}