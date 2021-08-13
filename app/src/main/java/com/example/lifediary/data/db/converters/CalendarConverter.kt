package com.example.lifediary.data.db.converters

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun longToCalendar(long: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = long
        return calendar
    }
}