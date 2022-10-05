package com.example.lifediary.data.db.models

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import java.util.*

@Entity(
    tableName = "to_do_list",
    indices = [Index(value = ["text", "day", "month", "year"], unique = true)]
)
@TypeConverters(CalendarConverter::class)
data class ToDoListItemEntity(
    @PrimaryKey
    val id: Long?,
    var text: String,
    @ColumnInfo(name = "day")
    val dayNumber: Int,
    @ColumnInfo(name = "month")
    val monthNumber: Int,
    val year: Int,
    @ColumnInfo(name = "is_done")
    var isDone: Boolean,
    @ColumnInfo(name = "notification_enabled", defaultValue = "0")
    var notificationEnabled: Boolean,
    @ColumnInfo(name = "notification_time", defaultValue = "0")
    var notificationTime: Calendar,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
)