package com.example.lifediary.data.domain

data class City(
    val id: Long? = null,
    val name: String,
    val lat: Double,
    val lon: Double
) {
    fun getFormattedCoordinatesString() =
            "$lat, $lon"
}
