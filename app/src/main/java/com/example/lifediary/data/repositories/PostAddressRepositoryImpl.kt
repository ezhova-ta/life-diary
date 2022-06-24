package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.datasources.PostAddressLocalDataSource
import com.example.lifediary.data.db.entities.PostAddressEntity
import com.example.lifediary.data.repositories.mappers.PostAddressEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.PostAddressEntityMapper.toEntity
import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.domain.repositories.PostAddressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAddressRepositoryImpl @Inject constructor(
    private val localDataSource: PostAddressLocalDataSource
) : PostAddressRepository {
    override fun getAllAddresses(): LiveData<List<PostAddress>> {
        return localDataSource.getAllAddresses().toDomain()
    }

    private fun LiveData<List<PostAddressEntity>>.toDomain(): LiveData<List<PostAddress>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<PostAddressEntity>.toDomain(): List<PostAddress> {
        return map { entity -> entity.toDomain() }
    }

    override suspend fun addAddress(address: PostAddress) {
        localDataSource.addAddress(address.toEntity())
    }

    override suspend fun updateAddress(address: PostAddress) {
        localDataSource.updateAddress(address.toEntity())
    }

    override suspend fun deleteAddress(id: Long) {
        localDataSource.deleteAddress(id)
    }

    override suspend fun clearAddresses() {
        localDataSource.clearAddresses()
    }
}