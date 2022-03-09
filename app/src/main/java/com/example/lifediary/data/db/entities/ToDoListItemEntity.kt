package com.example.lifediary.data.db.entities

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.data.domain.Day
import java.util.*

@Entity(tableName = "to_do_list")
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
) : DbEntity<ToDoListItem>() {
    companion object {
        fun fromDomain(toDoListItem: ToDoListItem): ToDoListItemEntity {
            return ToDoListItemEntity(
                id = toDoListItem.id,
                text = toDoListItem.text,
                dayNumber = toDoListItem.day.dayNumber,
                monthNumber = toDoListItem.day.monthNumber,
                year = toDoListItem.day.year,
                isDone = toDoListItem.isDone,
                notificationEnabled = toDoListItem.notificationEnabled,
                notificationTime = toDoListItem.notificationTime,
                createdAt = toDoListItem.createdAt
            )
        }
    }

    override fun toDomain(): ToDoListItem {
        return ToDoListItem(
            id = id,
            text = text,
            day = Day(dayNumber, monthNumber, year),
            isDone = isDone,
            notificationEnabled = notificationEnabled,
            notificationTime = notificationTime,
            createdAt = createdAt
        )
    }
}