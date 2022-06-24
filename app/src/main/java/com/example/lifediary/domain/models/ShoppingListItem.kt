package com.example.lifediary.domain.models

import com.example.lifediary.presentation.utils.dates.CalendarBuilder
import java.util.*

data class ShoppingListItem(
    val id: Long? = null,
    var text: String,
    var isHighPriority: Boolean = false,
    var isCrossedOut: Boolean = false,
    val createdAt: Calendar = CalendarBuilder().build()
)
