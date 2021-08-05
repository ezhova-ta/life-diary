package com.example.lifediary.data.api

import com.example.lifediary.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherService {
    @GET("find")
    suspend fun findCity(
        @Query("q") name: String,
        @Query("lang") lang: String = "ru", // TODO Support other langs
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY,
        @Header("x-rapidapi-host") rapidApiHost: String = BuildConfig.WEATHER_API_HOST
    ): CitiesResponse
}