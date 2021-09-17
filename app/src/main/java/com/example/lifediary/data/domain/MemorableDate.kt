package com.example.lifediary.data.domain

data class MemorableDate(
    val id: Long? = null,
    val name: String,
	val dayNumber: Int,
	val monthNumber: Int,
	val year: Int? = null
)
