package com.example.lifediary.data.domain

import com.example.lifediary.utils.Day

data class Notes(
	val id: Long? = null,
	var text: String,
	val day: Day
)
