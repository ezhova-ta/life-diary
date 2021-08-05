package com.example.lifediary.data.domain

data class Location(
    val id: Long? = null,
    val name: String,
    val lat: Double,
    val lon: Double
) {
    fun getFormattedCoordinatesString() =
            "$lat, $lon"
}
