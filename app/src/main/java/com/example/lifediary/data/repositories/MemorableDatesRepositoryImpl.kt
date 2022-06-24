package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.MemorableDatesLocalDataSource
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemorableDatesRepositoryImpl @Inject constructor(
    private val localDataSource: MemorableDatesLocalDataSource
) : MemorableDatesRepository {
    override fun getDates(): LiveData<List<MemorableDate>> {
        return localDataSource.getDates()
    }

    override fun getDates(day: Day): LiveData<List<MemorableDate>> {
        return localDataSource.getDates(day)
    }

    override suspend fun getDate(id: Long): MemorableDate? {
        return localDataSource.getDate(id)
    }

    override suspend fun addDate(item: MemorableDate) {
        localDataSource.addDate(item)
    }

    override suspend fun updateDate(item: MemorableDate) {
        localDataSource.updateDate(item)
    }

    override suspend fun clearDates() {
        localDataSource.clearDates()
    }

    override suspend fun deleteDate(id: Long) {
        localDataSource.deleteDate(id)
    }
}