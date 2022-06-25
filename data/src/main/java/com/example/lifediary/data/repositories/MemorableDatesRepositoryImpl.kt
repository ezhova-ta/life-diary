package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.MemorableDatesLocalDataSource
import com.example.lifediary.data.db.models.MemorableDateEntity
import com.example.lifediary.data.repositories.mappers.MemorableDateEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.MemorableDateEntityMapper.toEntity
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemorableDatesRepositoryImpl @Inject constructor(
    private val localDataSource: MemorableDatesLocalDataSource
) : MemorableDatesRepository {
    override fun getDates(): Flow<List<MemorableDate>> {
        return localDataSource.getDates().toDomain()
    }

    override fun getDates(day: Day): Flow<List<MemorableDate>> {
        return localDataSource.getDates(day.dayNumber, day.monthNumber).toDomain()
    }

    private fun Flow<List<MemorableDateEntity>>.toDomain(): Flow<List<MemorableDate>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<MemorableDateEntity>.toDomain(): List<MemorableDate> {
        return map { entity -> entity.toDomain() }
    }

    override suspend fun getDate(id: Long): MemorableDate? {
        return localDataSource.getDate(id)?.toDomain()
    }

    override suspend fun addDate(item: MemorableDate) {
        localDataSource.addDate(item.toEntity())
    }

    override suspend fun updateDate(item: MemorableDate) {
        localDataSource.updateDate(item.toEntity())
    }

    override suspend fun clearDates() {
        localDataSource.clearDates()
    }

    override suspend fun deleteDate(id: Long) {
        localDataSource.deleteDate(id)
    }
}