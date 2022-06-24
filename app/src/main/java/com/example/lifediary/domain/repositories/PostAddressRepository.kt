package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.PostAddress

interface PostAddressRepository {
	fun getAllAddresses(): LiveData<List<PostAddress>>
	suspend fun addAddress(address: PostAddress)
	suspend fun updateAddress(address: PostAddress)
	suspend fun deleteAddress(id: Long)
	suspend fun clearAddresses()
}