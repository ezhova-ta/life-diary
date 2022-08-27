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

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") locationId: Long,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY
    ): CurrentWeatherResponse

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("id") locationId: Long,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY
    ): WeatherForecastResponse
}