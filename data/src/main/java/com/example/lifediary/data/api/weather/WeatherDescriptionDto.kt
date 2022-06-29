package com.example.lifediary.data.api.weather

import com.google.gson.annotations.SerializedName

data class WeatherDescriptionDto(
    @SerializedName("main")
    val shortDescription: String,
    val description: String,
    val icon: String
)