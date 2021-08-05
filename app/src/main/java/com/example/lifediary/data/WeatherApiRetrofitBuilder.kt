package com.example.lifediary.data

import com.example.lifediary.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherApiRetrofitBuilder @Inject constructor(
    private val client: OkHttpClient,
    private val converterFactory: Converter.Factory
) {
    fun build(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.WEATHER_API_BASE_URL)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
}