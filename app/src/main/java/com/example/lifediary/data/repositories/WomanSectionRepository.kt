package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.datasources.WomanSectionLocalDataSource
import com.example.lifediary.data.domain.MenstruationPeriod
import com.example.lifediary.utils.ThreeSourceLiveData
import com.example.lifediary.utils.plusDays
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionRepository @Inject constructor(
    private val localDataSource: WomanSectionLocalDataSource
) {
    fun getAllMenstruationPeriods(): LiveData<List<MenstruationPeriod>> {
        return localDataSource.getAllMenstruationPeriods()
    }

    fun getMenstruationPeriod(id: Long): MenstruationPeriod? {
        return localDataSource.getMenstruationPeriod(id)
    }

    fun getDurationOfMenstrualCycle(): LiveData<Int> {
        return localDataSource.getDurationOfMenstrualCycle()
    }

    fun getDurationOfMenstruationPeriod(): LiveData<Int> {
        return localDataSource.getDurationOfMenstruationPeriod()
    }

    fun getLastMenstruationPeriod(): LiveData<MenstruationPeriod?> {
        return getAllMenstruationPeriods().map {
            it.maxByOrNull { period -> period.startDate }
        }
    }

    fun getEstimatedNextMenstruationPeriod(): LiveData<MenstruationPeriod?> {
        return ThreeSourceLiveData(
            getLastMenstruationPeriod(),
            getDurationOfMenstrualCycle(),
            getDurationOfMenstruationPeriod()
        ) { lastMenstruationPeriod, durationOfMenstrualCycle, durationOfMenstruationPeriod ->
            lastMenstruationPeriod ?: return@ThreeSourceLiveData null
            durationOfMenstrualCycle ?: return@ThreeSourceLiveData null
            durationOfMenstruationPeriod ?: return@ThreeSourceLiveData null

            val startDateOfNextMenstruationPeriod = lastMenstruationPeriod.startDate.plusDays(
                durationOfMenstrualCycle
            )
            val endDateOfNextMenstruationPeriod = startDateOfNextMenstruationPeriod.plusDays(
                durationOfMenstruationPeriod - 1
            )

            MenstruationPeriod(
                startDate = startDateOfNextMenstruationPeriod,
                endDate = endDateOfNextMenstruationPeriod
            )
        }
    }

    suspend fun addMenstruationPeriod(period: MenstruationPeriod) {
        localDataSource.addMenstruationPeriod(period)
    }

    suspend fun updateMenstruationPeriod(period: MenstruationPeriod) {
        localDataSource.updateMenstruationPeriod(period)
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