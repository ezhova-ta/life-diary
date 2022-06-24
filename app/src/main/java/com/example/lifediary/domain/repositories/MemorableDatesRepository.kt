package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.MemorableDate

interface MemorableDatesRepository {
	fun getDates(): LiveData<List<MemorableDate>>
	fun getDates(day: Day): LiveData<List<MemorableDate>>
	suspend fun getDate(id: Long): MemorableDate?
	suspend fun addDate(item: MemorableDate)
	suspend fun updateDate(item: MemorableDate)
	suspend fun clearDates()
	suspend fun deleteDate(id: Long)
}