package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.PostAddressLocalDataSource
import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.domain.repositories.PostAddressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAddressRepositoryImpl @Inject constructor(
    private val localDataSource: PostAddressLocalDataSource
) : PostAddressRepository {
    override fun getAllAddresses(): LiveData<List<PostAddress>> {
        return localDataSource.getAllAddresses()
    }

    override suspend fun addAddress(address: PostAddress) {
        localDataSource.addAddress(address)
    }

    override suspend fun updateAddress(address: PostAddress) {
        localDataSource.updateAddress(address)
    }

    override suspend fun deleteAddress(id: Long) {
        localDataSource.deleteAddress(id)
    }

    override suspend fun clearAddresses() {
        localDataSource.clearAddresses()
    }
}