package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.WomanSectionLocalDataSource
import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.domain.repositories.WomanSectionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionRepositoryImpl @Inject constructor(
    private val localDataSource: WomanSectionLocalDataSource
) : WomanSectionRepository {
    override fun getAllMenstruationPeriods(): LiveData<List<MenstruationPeriod>> {
        return localDataSource.getAllMenstruationPeriods()
    }

    override fun getDurationOfMenstrualCycle(): LiveData<Int> {
        return localDataSource.getDurationOfMenstrualCycle()
    }

    override fun getDurationOfMenstruationPeriod(): LiveData<Int> {
        return localDataSource.getDurationOfMenstruationPeriod()
    }

    override suspend fun addMenstruationPeriod(period: MenstruationPeriod) {
        localDataSource.addMenstruationPeriod(period)
    }

    override suspend fun deleteMenstruationPeriod(id: Long) {
        localDataSource.deleteMenstruationPeriod(id)
    }

    override suspend fun clearMenstruationPeriodList() {
        localDataSource.clearMenstruationPeriodList()
    }

    override suspend fun setDurationOfMenstrualCycle(value: Int) {
        localDataSource.setDurationOfMenstrualCycle(value)
    }

    override suspend fun setDurationOfMenstruationPeriod(value: Int) {
        localDataSource.setDurationOfMenstruationPeriod(value)
    }
}