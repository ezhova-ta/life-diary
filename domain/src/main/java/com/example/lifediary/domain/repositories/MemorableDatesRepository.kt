package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.MemorableDate
import kotlinx.coroutines.flow.Flow

interface MemorableDatesRepository {
	fun getFlowDates(): Flow<List<MemorableDate>>
	suspend fun getDates(): List<MemorableDate>
	fun getFlowDates(day: Day): Flow<List<MemorableDate>>
	suspend fun getDate(id: Long): MemorableDate?
	suspend fun addDate(item: MemorableDate)
	suspend fun updateDate(item: MemorableDate)
	suspend fun clearDates()
	suspend fun deleteDate(id: Long)
}