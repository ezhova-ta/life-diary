package com.example.lifediary.data.domain

data class DateNote(
	val id: Long? = null,
	var text: String,
	val day: Day
)
