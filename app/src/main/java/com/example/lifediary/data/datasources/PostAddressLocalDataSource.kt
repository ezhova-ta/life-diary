package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.PostAddressDao
import com.example.lifediary.data.db.entities.PostAddressEntity
import javax.inject.Inject

class PostAddressLocalDataSource @Inject constructor(private val dao: PostAddressDao) {
    fun getAllAddresses(): LiveData<List<PostAddressEntity>> {
        return dao.getAll()
    }

    suspend fun addAddress(address: PostAddressEntity) {
        dao.insert(address)
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