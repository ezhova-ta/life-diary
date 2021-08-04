package com.example.lifediary.di

import android.content.Context
import com.example.lifediary.LifeDiaryApplication
import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.ShoppingListDao
import com.example.lifediary.di.providers.MainDatabaseProvider
import com.example.lifediary.di.providers.ShoppingListDaoProvider
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(application: LifeDiaryApplication) : Module() {
    init {
        bind(NavigatorHolder::class.java).toInstance(application.navigationHolder)
        bind(Router::class.java).toInstance(application.router)
        bind(Context::class.java).toInstance(application.applicationContext)
        bind(MainDataBase::class.java).toProvider(MainDatabaseProvider::class.java).singleton()
        bind(ShoppingListDao::class.java).toProvider(ShoppingListDaoProvider::class.java)
    }
}