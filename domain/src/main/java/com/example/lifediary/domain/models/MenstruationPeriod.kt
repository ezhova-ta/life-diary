package com.example.lifediary.domain.models

import java.util.*

data class MenstruationPeriod(
    var id: Long? = null,
    var startDate: Calendar,
    var endDate: Calendar
)
