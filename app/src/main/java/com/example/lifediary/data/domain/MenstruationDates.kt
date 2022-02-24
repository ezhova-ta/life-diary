package com.example.lifediary.data.domain

import java.util.*

data class MenstruationDates(
    val id: Long? = null,
    var startDate: Calendar,
    var endDate: Calendar
)
