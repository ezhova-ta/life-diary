package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.MenstruationPeriodDao
import javax.inject.Inject
import javax.inject.Provider

class MenstruationPeriodDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<MenstruationPeriodDao> {
    override fun get(): MenstruationPeriodDao {
        return mainDataBase.menstruationPeriodDao()
    }
}