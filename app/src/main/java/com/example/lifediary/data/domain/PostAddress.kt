package com.example.lifediary.data.domain

data class PostAddress(
	val id: Long? = null,
	val name: String,
	val street: String,
	val buildingNumber: String,
	val apartmentNumber: String? = null,
	val city: String,
	val postcode: String,
	val edgeRegion: String? = null
)