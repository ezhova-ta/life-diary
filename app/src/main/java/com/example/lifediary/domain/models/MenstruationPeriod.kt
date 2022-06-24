package com.example.lifediary.domain.models

import com.example.lifediary.presentation.utils.dates.toDateString
import java.util.*

data class MenstruationPeriod(
    val id: Long? = null,
    var startDate: Calendar,
    var endDate: Calendar
) {
    fun toOutputFormattedString(): String {
        return "${startDate.toDateString()} - ${endDate.toDateString()}"
    }
}
