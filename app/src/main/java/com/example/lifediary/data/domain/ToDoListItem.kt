package com.example.lifediary.data.domain

import java.util.*

data class ToDoListItem(
    val id: Long? = null,
    var text: String,
    var isDone: Boolean = false,
    val createdAt: Calendar = Calendar.getInstance()
)
