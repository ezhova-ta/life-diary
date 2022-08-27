package com.example.lifediary.domain.models

data class Location(
    val id: Long? = null,
    val name: String,
    val region: String? = null,
    val country: String? = null,
    val lat: Double,
    val lon: Double
)
