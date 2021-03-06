package com.example.lifediary.data.datasources

import com.example.lifediary.data.WomanSectionDataStoreManager
import com.example.lifediary.data.db.dao.MenstruationPeriodDao
import com.example.lifediary.data.db.models.MenstruationPeriodEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionLocalDataSource @Inject constructor(
    private val dao: MenstruationPeriodDao,
    private val womanSectionDataStoreManager: WomanSectionDataStoreManager
) {
    fun getAllMenstruationPeriods(): Flow<List<MenstruationPeriodEntity>> {
        return dao.getAll()
    }

    fun getDurationOfMenstrualCycle(): Flow<Int> {
        return womanSectionDataStoreManager.durationOfMenstrualCycle
    }

    fun getDurationOfMenstruationPeriod(): Flow<Int> {
        return womanSectionDataStoreManager.durationOfMenstruationPeriod
    }

    suspend fun addMenstruationPeriod(period: MenstruationPeriodEntity) {
        dao.insert(period)
    }

    suspend fun deleteMenstruationPeriod(id: Long) {
        dao.delete(id)
    }

    suspend fun clearMenstruationPeriodList() {
        dao.deleteAll()
    }

    suspend fun setDurationOfMenstrualCycle(value: Int) {
        womanSectionDataStoreManager.setDurationOfMenstrualCycle(value)
    }

    suspend fun setDurationOfMenstruationPeriod(value: Int) {
        womanSectionDataStoreManager.setDurationOfMenstruationPeriod(value)
    }
}