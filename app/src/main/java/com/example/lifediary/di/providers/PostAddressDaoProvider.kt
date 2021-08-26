package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.PostAddressDao
import javax.inject.Inject
import javax.inject.Provider

class PostAddressDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<PostAddressDao> {

    override fun get(): PostAddressDao {
        return mainDataBase.postAddressDao()
    }
}