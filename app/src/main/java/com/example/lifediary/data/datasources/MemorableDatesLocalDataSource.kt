package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.MemorableDateDao
import com.example.lifediary.data.db.entities.MemorableDateEntity
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.domain.Day
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class MemorableDatesLocalDataSource @Inject constructor(private val dao: MemorableDateDao) {
    fun getDates(): LiveData<List<MemorableDate>> {
        return dao.getAll().toDomain()
    }

    fun getDates(day: Day): LiveData<List<MemorableDate>> {
        return dao.getAll(day.dayNumber, day.monthNumber).toDomain()
    }

    suspend fun getDate(id: Long): MemorableDate? {
        return dao.get(id)?.toDomain()
    }

    suspend fun addDate(item: MemorableDate) {
        dao.insert(MemorableDateEntity.fromDomain(item))
    }

    suspend fun updateDate(item: MemorableDate) {
        dao.update(MemorableDateEntity.fromDomain(item))
    }

    suspend fun clearDates() {
        dao.deleteAll()
    }

    suspend fun deleteDate(id: Long) {
        dao.delete(id)
    }
}