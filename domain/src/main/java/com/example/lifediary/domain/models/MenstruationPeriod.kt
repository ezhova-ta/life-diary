package com.example.lifediary.domain.models

import java.util.*

data class MenstruationPeriod(
    val id: Long? = null,
    var startDate: Calendar,
    var endDate: Calendar
)
