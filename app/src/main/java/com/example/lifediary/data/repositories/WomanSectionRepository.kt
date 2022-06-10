package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.datasources.WomanSectionLocalDataSource
import com.example.lifediary.data.domain.Day
import com.example.lifediary.data.domain.MenstruationPeriod
import com.example.lifediary.utils.dates.CalendarBuilder
import com.example.lifediary.utils.dates.getDaysBetween
import com.example.lifediary.utils.dates.isWithinInterval
import com.example.lifediary.utils.dates.plusDays
import com.example.lifediary.utils.livedata.ThreeSourceLiveData
import com.example.lifediary.utils.livedata.TwoSourceLiveData
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

    fun getDelayOfMenstruation(): LiveData<Long?> {
        return getEstimatedNextMenstruationPeriod().map { nextMenstruationPeriod ->
            val startNextMenstruationPeriod = nextMenstruationPeriod?.startDate ?: return@map null
            val now = CalendarBuilder().build()
            if(now.before(startNextMenstruationPeriod)) return@map null
            getDaysBetween(startNextMenstruationPeriod, now)
        }
    }

    fun isDayOfMenstruationPeriod(day: Day): LiveData<Boolean> {
        return getAllMenstruationPeriods().map { menstruationPeriods ->
            menstruationPeriods.find { day.isWithinInterval(it.startDate, it.endDate) } != null
        }
    }

    fun isDayOfEstimatedMenstruationPeriod(day: Day): LiveData<Boolean> {
        return getEstimatedNextMenstruationPeriod().map { menstruationPeriod ->
            menstruationPeriod ?: return@map false
            day.isWithinInterval(menstruationPeriod.startDate, menstruationPeriod.endDate)
        }
    }

    fun isDayNotIncludedInMenstruationPeriod(day: Day): LiveData<Boolean> {
        return TwoSourceLiveData<Boolean, Boolean, Boolean>(
            isDayOfMenstruationPeriod(day),
            isDayOfEstimatedMenstruationPeriod(day)
        ) { isDayOfMenstruationPeriod, isDayOfEstimatedMenstruationPeriod ->
            isDayOfMenstruationPeriod != true && isDayOfEstimatedMenstruationPeriod != true
        }
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