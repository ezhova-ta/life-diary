package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lifediary.data.WomanSectionDataStoreManager
import com.example.lifediary.data.db.dao.MenstruationPeriodDao
import com.example.lifediary.data.db.models.MenstruationPeriodEntity
import javax.inject.Inject

class WomanSectionLocalDataSource @Inject constructor(
    private val dao: MenstruationPeriodDao,
    private val womanSectionDataStoreManager: WomanSectionDataStoreManager
) {
    fun getAllMenstruationPeriods(): LiveData<List<MenstruationPeriodEntity>> {
        return dao.getAll()
    }

    fun getDurationOfMenstrualCycle(): LiveData<Int> {
        return womanSectionDataStoreManager.durationOfMenstrualCycle.asLiveData()
    }

    fun getDurationOfMenstruationPeriod(): LiveData<Int> {
        return womanSectionDataStoreManager.durationOfMenstruationPeriod.asLiveData()
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