package com.example.lifediary.data.domain

data class MemorableDate(
    val id: Long? = null,
    var name: String,
	var dayNumber: Int,
	var monthNumber: Int,
	var year: Int? = null
)
