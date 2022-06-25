package com.example.lifediary.data.datasources

import com.example.lifediary.data.db.dao.MemorableDateDao
import com.example.lifediary.data.db.models.MemorableDateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemorableDatesLocalDataSource @Inject constructor(private val dao: MemorableDateDao) {
    fun getDates(): Flow<List<MemorableDateEntity>> {
        return dao.getAll()
    }

    fun getDates(dayNumber: Int, monthNumber: Int): Flow<List<MemorableDateEntity>> {
        return dao.getAll(dayNumber, monthNumber)
    }

    suspend fun getDate(id: Long): MemorableDateEntity? {
        return dao.get(id)
    }

    suspend fun addDate(item: MemorableDateEntity) {
        dao.insert(item)
    }

    suspend fun updateDate(item: MemorableDateEntity) {
        dao.update(item)
    }

    suspend fun clearDates() {
        dao.deleteAll()
    }

    suspend fun deleteDate(id: Long) {
        dao.delete(id)
    }
}