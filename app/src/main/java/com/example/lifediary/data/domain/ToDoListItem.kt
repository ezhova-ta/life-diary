package com.example.lifediary.data.domain

import com.example.lifediary.utils.Day
import java.util.*

data class ToDoListItem(
    val id: Long? = null,
    var text: String,
	val day: Day,
    var isDone: Boolean = false,
    val createdAt: Calendar = Calendar.getInstance()
)
