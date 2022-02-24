package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.MenstruationDatesDao
import com.example.lifediary.data.db.entities.MenstruationDatesEntity
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class MenstruationDatesLocalDataSource @Inject constructor(private val dao: MenstruationDatesDao) {
    fun getAllMenstruationDates(): LiveData<List<MenstruationDates>> {
        return dao.getAll().toDomain()
    }

    fun getMenstruationDates(id: Long): MenstruationDates? {
        return dao.get(id)?.toDomain()
    }

    suspend fun addMenstruationDates(dates: MenstruationDates) {
        dao.insert(MenstruationDatesEntity.fromDomain(dates))
    }

    suspend fun updateMenstruationDates(dates: MenstruationDates) {
        dao.update(MenstruationDatesEntity.fromDomain(dates))
    }

    suspend fun deleteMenstruationDates(id: Long) {
        dao.delete(id)
    }

    suspend fun clearMenstruationDatesList() {
        dao.deleteAll()
    }
}