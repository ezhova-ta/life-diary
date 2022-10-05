package com.example.lifediary.domain.models

data class DateNote(
	var id: Long? = null,
	var text: String,
	val day: Day
)
