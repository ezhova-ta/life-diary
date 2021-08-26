package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.PostAddressDao
import com.example.lifediary.data.db.entities.PostAddressEntity
import com.example.lifediary.data.domain.PostAddress
import javax.inject.Inject

class PostAddressLocalDataSource @Inject constructor(private val dao: PostAddressDao) {
    fun getAllAddresses(): LiveData<List<PostAddress>> {
        return dao.getAll().toDomain()
    }

    private fun LiveData<List<PostAddressEntity>>.toDomain(): LiveData<List<PostAddress>> {
        return map { postAddressEntityList ->
            postAddressEntityList.map { postAddressEntity ->
                postAddressEntity.toDomain()
            }
        }
    }

    suspend fun addAddress(address: PostAddress) {
        dao.insert(PostAddressEntity.fromDomain(address))
    }

    suspend fun updateAddress(address: PostAddress) {
        dao.update(PostAddressEntity.fromDomain(address))
    }

    suspend fun deleteAddress(id: Long) {
        dao.delete(id)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}