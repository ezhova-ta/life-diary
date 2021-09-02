package com.example.lifediary.data.domain

import java.util.*

data class MainNote(
	val id: Long? = null,
	var text: String,
	val createdAt: Calendar = Calendar.getInstance()
)
