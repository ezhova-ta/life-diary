package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.MenstruationPeriod
import kotlinx.coroutines.flow.Flow

interface WomanSectionRepository {
	fun getAllMenstruationPeriods(): Flow<List<MenstruationPeriod>>
	fun getDurationOfMenstrualCycle(): Flow<Int>
	fun getDurationOfMenstruationPeriod(): Flow<Int>
	suspend fun addMenstruationPeriod(period: MenstruationPeriod)
	suspend fun deleteMenstruationPeriod(id: Long)
	suspend fun clearMenstruationPeriodList()
	suspend fun setDurationOfMenstrualCycle(value: Int)
	suspend fun setDurationOfMenstruationPeriod(value: Int)
}