package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.PostAddressLocalDataSource
import com.example.lifediary.data.domain.PostAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAddressRepository @Inject constructor(
    private val localDataSource: PostAddressLocalDataSource
) {
    fun getAllAddresses(): LiveData<List<PostAddress>> {
        return localDataSource.getAllAddresses()
    }

    suspend fun addAddress(address: PostAddress) {
        localDataSource.addAddress(address)
    }

    suspend fun updateAddress(address: PostAddress) {
        localDataSource.updateAddress(address)
    }

    suspend fun deleteAddress(id: Long) {
        localDataSource.deleteAddress(id)
    }

    suspend fun clearAddresses() {
        localDataSource.clearAddresses()
    }
}