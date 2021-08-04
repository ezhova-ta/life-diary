package com.example.lifediary.data.domain

data class CityListItem(
    val id: Long? = null,
    val name: String,
    val lat: Double,
    val lon: Double,
    val countryCode: String
) {
    fun getCoordinatedString() =
            "$lat, $lon"
}
