package com.example.lifediary.data.db.converters

import androidx.room.TypeConverter
import com.example.lifediary.utils.toCalendar
import com.example.lifediary.utils.toLong
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long {
        return calendar.toLong()
    }

    @TypeConverter
    fun longToCalendar(long: Long): Calendar {
        return long.toCalendar()
    }
}