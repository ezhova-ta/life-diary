package com.example.lifediary.domain.models

import com.example.lifediary.domain.utils.CalendarBuilder
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
	val createdAt: Calendar = CalendarBuilder().build()
)