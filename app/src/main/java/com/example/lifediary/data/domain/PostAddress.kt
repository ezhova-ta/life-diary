package com.example.lifediary.data.domain

import java.util.*

data class PostAddress(
	var id: Long? = null,
	val name: String,
	val street: String,
	val buildingNumber: String,
	val apartmentNumber: String,
	val city: String,
	val postcode: String,
	val edgeRegion: String,
	var createdAt: Calendar = Calendar.getInstance()
)