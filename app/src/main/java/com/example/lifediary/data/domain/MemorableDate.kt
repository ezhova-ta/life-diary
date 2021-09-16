package com.example.lifediary.data.domain

import com.example.lifediary.utils.Day

data class MemorableDate(
    val id: Long? = null,
    val name: String,
    val day: Day
)
