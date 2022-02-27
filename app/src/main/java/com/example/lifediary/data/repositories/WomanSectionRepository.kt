package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.WomanSectionLocalDataSource
import com.example.lifediary.data.domain.MenstruationDates
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionRepository @Inject constructor(
    private val localDataSource: WomanSectionLocalDataSource
) {
    fun getAllMenstruationDates(): LiveData<List<MenstruationDates>> {
        return localDataSource.getAllMenstruationDates()
    }

    fun getMenstruationDates(id: Long): MenstruationDates? {
        return localDataSource.getMenstruationDates(id)
    }

    fun getDurationOfMenstrualCycle(): LiveData<Int> {
        return localDataSource.getDurationOfMenstrualCycle()
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

    suspend fun setDurationOfMenstrualCycle(value: Int) {
        localDataSource.setDurationOfMenstrualCycle(value)
    }
}