package com.example.lifediary.data.api

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    val count: Int,
    val message: String,
    @SerializedName("cod")
    val code: Int,
    @SerializedName("list")
    val cities: List<CityDto>
)