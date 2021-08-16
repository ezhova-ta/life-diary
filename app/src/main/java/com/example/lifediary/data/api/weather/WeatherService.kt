package com.example.lifediary.data.api.weather

import com.example.lifediary.BuildConfig
import com.example.lifediary.data.api.weather.forecast.WeatherForecastResponse
import com.example.lifediary.data.api.weather.location.LocationsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherService {
    // TODO Support other langs

    @GET("find")
    suspend fun findLocations(
        @Query("q") name: String,
        @Query("lang") lang: String = "ru",
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY,
        @Header("x-rapidapi-host") rapidApiHost: String = BuildConfig.WEATHER_API_HOST
    ): LocationsResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") locationId: Long,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY,
        @Header("x-rapidapi-host") rapidApiHost: String = BuildConfig.WEATHER_API_HOST
    ): CurrentWeatherResponse

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("id") locationId: Long,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY,
        @Header("x-rapidapi-host") rapidApiHost: String = BuildConfig.WEATHER_API_HOST
    ): WeatherForecastResponse
}