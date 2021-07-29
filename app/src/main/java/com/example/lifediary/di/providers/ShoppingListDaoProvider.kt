package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.ShoppingListDao
import javax.inject.Inject
import javax.inject.Provider

class ShoppingListDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<ShoppingListDao> {

    override fun get(): ShoppingListDao {
        return mainDataBase.shoppingListDao()
    }
}