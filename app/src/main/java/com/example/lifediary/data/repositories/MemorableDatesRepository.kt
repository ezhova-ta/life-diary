package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.MemorableDatesLocalDataSource
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.models.Day
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemorableDatesRepository @Inject constructor(
    private val localDataSource: MemorableDatesLocalDataSource
) {
    fun getDates(): LiveData<List<MemorableDate>> {
        return localDataSource.getDates()
    }

    fun getDates(day: Day): LiveData<List<MemorableDate>> {
        return localDataSource.getDates(day)
    }

    suspend fun getDate(id: Long): MemorableDate? {
        return localDataSource.getDate(id)
    }

    suspend fun addDate(item: MemorableDate) {
        localDataSource.addDate(item)
    }

    suspend fun updateDate(item: MemorableDate) {
        localDataSource.updateDate(item)
    }

    suspend fun clearDates() {
        localDataSource.clearDates()
    }

    suspend fun deleteDate(id: Long) {
        localDataSource.deleteDate(id)
    }
}