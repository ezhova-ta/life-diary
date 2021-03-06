package com.example.lifediary.data.db.converters

import androidx.room.TypeConverter
import com.example.lifediary.domain.utils.CalendarBuilder
import com.example.lifediary.domain.utils.toCalendar
import com.example.lifediary.domain.utils.toLong
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long {
        return calendar.toLong()
    }

    @TypeConverter
    fun longToCalendar(long: Long): Calendar {
        if(long == 0L) return CalendarBuilder().build()
        return long.toCalendar()
    }
}