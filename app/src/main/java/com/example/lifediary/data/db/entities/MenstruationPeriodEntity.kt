package com.example.lifediary.data.db.entities

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import java.util.*

@Entity(
    tableName = "menstruation_dates",
    indices = [Index(value = ["start_date", "end_date"], unique = true)]
)
@TypeConverters(CalendarConverter::class)
data class MenstruationPeriodEntity(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "start_date")
    var startDate: Calendar,
    @ColumnInfo(name = "end_date")
    var endDate: Calendar
)
