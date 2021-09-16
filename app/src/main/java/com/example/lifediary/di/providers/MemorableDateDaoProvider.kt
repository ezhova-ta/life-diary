package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.MemorableDateDao
import javax.inject.Inject
import javax.inject.Provider

class MemorableDateDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<MemorableDateDao> {

    override fun get(): MemorableDateDao {
        return mainDataBase.memorableDateDao()
    }
}