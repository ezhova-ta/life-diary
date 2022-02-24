package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.MenstruationDatesLocalDataSource
import com.example.lifediary.data.db.entities.MenstruationDatesEntity
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.utils.toDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenstruationDatesRepository @Inject constructor(
    private val localDataSource: MenstruationDatesLocalDataSource
) {
    fun getAllMenstruationDates(): LiveData<List<MenstruationDates>> {
        return localDataSource.getAllMenstruationDates()
    }

    fun getMenstruationDates(id: Long): MenstruationDates? {
        return localDataSource.getMenstruationDates(id)
    }

    suspend fun addMenstruationDates(dates: MenstruationDates) {
        localDataSource.addMenstruationDates(dates)
    }

    suspend fun updateMenstruationDates(dates: MenstruationDates) {
        localDataSource.updateMenstruationDates(dates)
    }

    suspend fun deleteMenstruationDates(id: Long) {
        localDataSource.deleteMenstruationDates(id)
    }

    suspend fun clearMenstruationDatesList() {
        localDataSource.clearMenstruationDatesList()
    }
}