package com.example.lifediary.data.domain

data class Addressee(
	val name: String,
	val street: String,
	val buildingNumber: String,
	val apartmentNumber: String,
	val city: String,
	val postcode: String,
	val edgeRegion: String? = null
)