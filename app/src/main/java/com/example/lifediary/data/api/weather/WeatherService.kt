package com.example.lifediary.data.api.weather

import com.example.lifediary.BuildConfig
import com.example.lifediary.data.api.weather.location.LocationsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherService {
    @GET("find")
    suspend fun findLocations(
        @Query("q") name: String,
        @Query("lang") lang: String = "ru", // TODO Support other langs
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY,
        @Header("x-rapidapi-host") rapidApiHost: String = BuildConfig.WEATHER_API_HOST
    ): LocationsResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") locationId: Long,
        @Query("lang") lang: String = "ru", // TODO Support other langs
        @Query("units") units: String = "metric",
        @Header("x-rapidapi-key") rapidApiKey: String = BuildConfig.WEATHER_API_KEY,
        @Header("x-rapidapi-host") rapidApiHost: String = BuildConfig.WEATHER_API_HOST
    ): CurrentWeatherResponse
}