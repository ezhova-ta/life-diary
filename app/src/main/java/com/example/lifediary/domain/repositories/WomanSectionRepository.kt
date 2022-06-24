package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.MenstruationPeriod

interface WomanSectionRepository {
	fun getAllMenstruationPeriods(): LiveData<List<MenstruationPeriod>>
	fun getDurationOfMenstrualCycle(): LiveData<Int>
	fun getDurationOfMenstruationPeriod(): LiveData<Int>
	suspend fun addMenstruationPeriod(period: MenstruationPeriod)
	suspend fun deleteMenstruationPeriod(id: Long)
	suspend fun clearMenstruationPeriodList()
	suspend fun setDurationOfMenstrualCycle(value: Int)
	suspend fun setDurationOfMenstruationPeriod(value: Int)
}