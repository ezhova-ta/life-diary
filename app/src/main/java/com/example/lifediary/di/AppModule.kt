package com.example.lifediary.di

import android.content.Context
import com.example.lifediary.data.api.weather.WeatherService
import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.*
import com.example.lifediary.data.repositories.*
import com.example.lifediary.di.providers.*
import com.example.lifediary.domain.repositories.*
import com.example.lifediary.presentation.LifeDiaryApplication
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
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
        bind(CurrentWeatherDao::class.java).toProvider(CurrentWeatherDaoProvider::class.java)
        bind(DateNoteDao::class.java).toProvider(DateNoteDaoProvider::class.java)
        bind(MainNotesDao::class.java).toProvider(MainNotesDaoProvider::class.java)
        bind(PostAddressDao::class.java).toProvider(PostAddressDaoProvider::class.java)
        bind(ToDoListDao::class.java).toProvider(ToDoListDaoProvider::class.java)
        bind(MemorableDateDao::class.java).toProvider(MemorableDateDaoProvider::class.java)
        bind(MenstruationPeriodDao::class.java).toProvider(MenstruationPeriodDaoProvider::class.java)

        bind(DateNoteRepository::class.java).to(DateNoteRepositoryImpl::class.java)
        bind(MainNotesRepository::class.java).to(MainNotesRepositoryImpl::class.java)
        bind(MemorableDatesRepository::class.java).to(MemorableDatesRepositoryImpl::class.java)
        bind(PostAddressRepository::class.java).to(PostAddressRepositoryImpl::class.java)
        bind(SettingsRepository::class.java).to(SettingsRepositoryImpl::class.java)
        bind(ShoppingListRepository::class.java).to(ShoppingListRepositoryImpl::class.java)
        bind(ToDoListRepository::class.java).to(ToDoListRepositoryImpl::class.java)
        bind(WeatherRepository::class.java).to(WeatherRepositoryImpl::class.java)
        bind(WomanSectionRepository::class.java).to(WomanSectionRepositoryImpl::class.java)

        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
        bind(Converter.Factory::class.java).toInstance(GsonConverterFactory.create())
        bind(WeatherService::class.java).toProvider(WeatherServiceProvider::class.java).singleton()
    }
}