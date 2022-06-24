package com.example.lifediary.domain.models

data class DateNote(
	val id: Long? = null,
	var text: String,
	val day: Day
)
