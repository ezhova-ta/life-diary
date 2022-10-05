package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.MenstruationPeriod
import kotlinx.coroutines.flow.Flow

interface WomanSectionRepository {
	fun getFlowAllMenstruationPeriods(): Flow<List<MenstruationPeriod>>
	suspend fun getAllMenstruationPeriods(): List<MenstruationPeriod>
	fun getDurationOfMenstrualCycleFlow(): Flow<Int>
	fun getDurationOfMenstruationPeriodFlow(): Flow<Int>
	suspend fun addMenstruationPeriod(period: MenstruationPeriod)
	suspend fun addAllMenstruationPeriods(periods: List<MenstruationPeriod>)
	suspend fun deleteMenstruationPeriod(id: Long)
	suspend fun clearMenstruationPeriodList()
	suspend fun setDurationOfMenstrualCycle(value: Int)
	suspend fun setDurationOfMenstruationPeriod(value: Int)
}