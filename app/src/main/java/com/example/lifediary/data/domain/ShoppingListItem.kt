package com.example.lifediary.data.domain

import org.joda.time.LocalDateTime

data class ShoppingListItem(
    val id: Long? = null,
    var text: String,
    var isHighPriority: Boolean = false,
    var isCrossedOut: Boolean = false,
    val createdTimestamp: Long = LocalDateTime.now().toDate().time
)
