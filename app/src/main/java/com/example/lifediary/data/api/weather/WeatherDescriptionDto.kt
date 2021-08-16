package com.example.lifediary.data.api.weather

import com.example.lifediary.data.domain.WeatherDescription
import com.google.gson.annotations.SerializedName

data class WeatherDescriptionDto(
    @SerializedName("main")
    val shortDescription: String,
    val description: String,
    val icon: String
) {
    fun toDomain(): WeatherDescription {
        return WeatherDescription(
            shortDescription = shortDescription,
            description = description,
            icon = icon
        )
    }
}