package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.MenstruationDatesDao
import javax.inject.Inject
import javax.inject.Provider

class MenstruationDatesDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<MenstruationDatesDao> {
    override fun get(): MenstruationDatesDao {
        return mainDataBase.menstruationDatesDao()
    }
}