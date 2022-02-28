package com.example.lifediary.data.domain

import com.example.lifediary.utils.toDateString
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
