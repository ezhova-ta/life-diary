package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.PostAddressLocalDataSource
import com.example.lifediary.data.db.models.PostAddressEntity
import com.example.lifediary.data.repositories.mappers.db.PostAddressEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.PostAddressEntityMapper.toEntity
import com.example.lifediary.domain.models.PostAddress
import com.example.lifediary.domain.repositories.PostAddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAddressRepositoryImpl @Inject constructor(
    private val localDataSource: PostAddressLocalDataSource
) : PostAddressRepository {
    override fun getFlowAllAddresses(): Flow<List<PostAddress>> {
        return localDataSource.getFlowAllAddresses().toDomain()
    }

    override suspend fun getAllAddresses(): List<PostAddress> {
        return localDataSource.getAll().toDomain()
    }

    private fun Flow<List<PostAddressEntity>>.toDomain(): Flow<List<PostAddress>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<PostAddressEntity>.toDomain(): List<PostAddress> {
        return map { entity -> entity.toDomain() }
    }

    override suspend fun addAddress(address: PostAddress) {
        localDataSource.addAddress(address.toEntity())
    }

    override suspend fun addAllAddresses(addresses: List<PostAddress>) {
        localDataSource.addAllAddresses(addresses.toEntity())
    }

    private fun List<PostAddress>.toEntity(): List<PostAddressEntity> {
        return map { domain -> domain.toEntity() }
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