package com.example.lifediary.di

import android.content.Context
import com.example.lifediary.LifeDiaryApplication
import com.example.lifediary.data.api.WeatherService
import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.dao.ShoppingListDao
import com.example.lifediary.di.providers.*
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module

class AppModule(application: LifeDiaryApplication) : Module() {
    init {
        bind(NavigatorHolder::class.java).toInstance(application.navigationHolder)
        bind(Router::class.java).toInstance(application.router)
        bind(Context::class.java).toInstance(application.applicationContext)
        bind(MainDataBase::class.java).toProvider(MainDatabaseProvider::class.java).singleton()
        bind(ShoppingListDao::class.java).toProvider(ShoppingListDaoProvider::class.java)
        bind(LocationDao::class.java).toProvider(LocationDaoProvider::class.java)
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
        bind(Converter.Factory::class.java).toInstance(GsonConverterFactory.create())
        bind(WeatherService::class.java).toProvider(WeatherServiceProvider::class.java).singleton()
        bind(WeatherService::class.java).toProvider(WeatherServiceProvider::class.java).singleton()
    }
}