package com.example.lifediary.data.domain

import java.util.*

data class ShoppingListItem(
    val id: Long? = null,
    var text: String,
    var isHighPriority: Boolean = false,
    var isCrossedOut: Boolean = false,
    val createdTimestamp: Long = Calendar.getInstance().timeInMillis
)
