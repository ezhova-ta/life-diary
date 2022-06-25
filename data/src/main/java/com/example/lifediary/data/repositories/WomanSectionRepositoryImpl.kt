package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.WomanSectionLocalDataSource
import com.example.lifediary.data.db.models.MenstruationPeriodEntity
import com.example.lifediary.data.repositories.mappers.MenstruationPeriodEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.MenstruationPeriodEntityMapper.toEntity
import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.domain.repositories.WomanSectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionRepositoryImpl @Inject constructor(
    private val localDataSource: WomanSectionLocalDataSource
) : WomanSectionRepository {
    override fun getAllMenstruationPeriods(): Flow<List<MenstruationPeriod>> {
        return localDataSource.getAllMenstruationPeriods().toDomain()
    }

    private fun Flow<List<MenstruationPeriodEntity>>.toDomain(): Flow<List<MenstruationPeriod>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<MenstruationPeriodEntity>.toDomain(): List<MenstruationPeriod> {
        return map { entity -> entity.toDomain() }
    }

    override fun getDurationOfMenstrualCycle(): Flow<Int> {
        return localDataSource.getDurationOfMenstrualCycle()
    }

    override fun getDurationOfMenstruationPeriod(): Flow<Int> {
        return localDataSource.getDurationOfMenstruationPeriod()
    }

    override suspend fun addMenstruationPeriod(period: MenstruationPeriod) {
        localDataSource.addMenstruationPeriod(period.toEntity())
    }

    override suspend fun deleteMenstruationPeriod(id: Long) {
        localDataSource.deleteMenstruationPeriod(id)
    }

    override suspend fun clearMenstruationPeriodList() {
        localDataSource.clearMenstruationPeriodList()
    }

    override suspend fun setDurationOfMenstrualCycle(value: Int) {
        localDataSource.setDurationOfMenstrualCycle(value)
    }

    override suspend fun setDurationOfMenstruationPeriod(value: Int) {
        localDataSource.setDurationOfMenstruationPeriod(value)
    }
}