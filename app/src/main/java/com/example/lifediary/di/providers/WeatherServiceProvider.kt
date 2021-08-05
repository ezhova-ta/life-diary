package com.example.lifediary.di.providers

import com.example.lifediary.data.WeatherApiRetrofitBuilder
import com.example.lifediary.data.api.WeatherService
import javax.inject.Inject
import javax.inject.Provider

class WeatherServiceProvider @Inject constructor(
    private val retrofitBuilder: WeatherApiRetrofitBuilder
) : Provider<WeatherService> {
    override fun get(): WeatherService {
        val retrofit = retrofitBuilder.build()
        return retrofit.create(WeatherService::class.java)
    }
}