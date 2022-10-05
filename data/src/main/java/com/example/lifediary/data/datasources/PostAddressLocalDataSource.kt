package com.example.lifediary.data.datasources

import com.example.lifediary.data.db.dao.PostAddressDao
import com.example.lifediary.data.db.models.PostAddressEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAddressLocalDataSource @Inject constructor(private val dao: PostAddressDao) {
    fun getFlowAllAddresses(): Flow<List<PostAddressEntity>> {
        return dao.getFlowAll()
    }

    suspend fun getAll(): List<PostAddressEntity> {
        return dao.getAll()
    }

    suspend fun addAddress(address: PostAddressEntity) {
        dao.insert(address)
    }

    suspend fun addAllAddresses(addresses: List<PostAddressEntity>) {
        dao.insertAll(addresses)
    }

    suspend fun updateAddress(address: PostAddressEntity) {
        dao.update(address)
    }

    suspend fun deleteAddress(id: Long) {
        dao.delete(id)
    }

    suspend fun clearAddresses() {
        dao.deleteAll()
    }
}