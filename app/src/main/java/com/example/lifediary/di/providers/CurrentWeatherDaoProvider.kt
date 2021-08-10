package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.CurrentWeatherDao
import javax.inject.Inject
import javax.inject.Provider

class CurrentWeatherDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<CurrentWeatherDao> {

    override fun get(): CurrentWeatherDao {
        return mainDataBase.currentWeatherDao()
    }
}