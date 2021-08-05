package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.LocationDao
import javax.inject.Inject
import javax.inject.Provider

class LocationDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<LocationDao> {

    override fun get(): LocationDao {
        return mainDataBase.locationDao()
    }
}