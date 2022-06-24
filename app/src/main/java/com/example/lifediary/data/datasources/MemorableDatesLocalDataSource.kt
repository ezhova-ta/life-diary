package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.MemorableDateDao
import com.example.lifediary.data.db.entities.MemorableDateEntity
import com.example.lifediary.domain.models.Day
import javax.inject.Inject

class MemorableDatesLocalDataSource @Inject constructor(private val dao: MemorableDateDao) {
    fun getDates(): LiveData<List<MemorableDateEntity>> {
        return dao.getAll()
    }

    fun getDates(dayNumber: Int, monthNumber: Int): LiveData<List<MemorableDateEntity>> {
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