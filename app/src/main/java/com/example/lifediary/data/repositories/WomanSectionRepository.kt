package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.WomanSectionLocalDataSource
import com.example.lifediary.domain.models.MenstruationPeriod
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionRepository @Inject constructor(
    private val localDataSource: WomanSectionLocalDataSource
) {
    fun getAllMenstruationPeriods(): LiveData<List<MenstruationPeriod>> {
        return localDataSource.getAllMenstruationPeriods()
    }

    fun getDurationOfMenstrualCycle(): LiveData<Int> {
        return localDataSource.getDurationOfMenstrualCycle()
    }

    fun getDurationOfMenstruationPeriod(): LiveData<Int> {
        return localDataSource.getDurationOfMenstruationPeriod()
    }

    suspend fun addMenstruationPeriod(period: MenstruationPeriod) {
        localDataSource.addMenstruationPeriod(period)
    }

    suspend fun deleteMenstruationPeriod(id: Long) {
        localDataSource.deleteMenstruationPeriod(id)
    }

    suspend fun clearMenstruationPeriodList() {
        localDataSource.clearMenstruationPeriodList()
    }

    suspend fun setDurationOfMenstrualCycle(value: Int) {
        localDataSource.setDurationOfMenstrualCycle(value)
    }

    suspend fun setDurationOfMenstruationPeriod(value: Int) {
        localDataSource.setDurationOfMenstruationPeriod(value)
    }
}