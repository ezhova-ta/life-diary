package com.example.lifediary.data.domain

import com.example.lifediary.utils.Day

data class Note(
	val id: Long? = null,
	var text: String,
	val day: Day
)
