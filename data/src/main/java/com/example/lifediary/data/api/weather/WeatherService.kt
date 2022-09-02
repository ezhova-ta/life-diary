package com.example.lifediary.data.api.weather

import com.example.lifediary.data.BuildConfig
import com.example.lifediary.data.api.weather.forecast.WeatherForecastResponse
import com.example.lifediary.data.api.weather.location.LocationDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherService {
    @GET("search.json")
    suspend fun findLocations(
        @Query("q") name: String,
        @Query("lang") lang: String = "ru",
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): List<LocationDto>

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") locationName: String,
        @Query("lang") lang: String = "ru",
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): CurrentWeatherResponse

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") locationName: String,
        @Query("days") days: Int = 10,
        @Query("lang") lang: String = "ru",
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): WeatherForecastResponse
}