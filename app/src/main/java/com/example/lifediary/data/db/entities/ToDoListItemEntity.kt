package com.example.lifediary.data.db.entities

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.ToDoListItem
import java.util.*

@Entity(tableName = "to_do_list", indices = [Index(value = ["text"], unique = true)])
@TypeConverters(CalendarConverter::class)
data class ToDoListItemEntity(
    @PrimaryKey
    val id: Long?,
    var text: String,
    @ColumnInfo(name = "is_done")
    var isDone: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
) {
    companion object {
        fun fromDomain(toDoListItem: ToDoListItem): ToDoListItemEntity {
            return ToDoListItemEntity(
                id = toDoListItem.id,
                text = toDoListItem.text,
                isDone = toDoListItem.isDone,
                createdAt = toDoListItem.createdAt
            )
        }
    }

    fun toDomain(): ToDoListItem {
        return ToDoListItem(
            id = id,
            text = text,
            isDone = isDone,
            createdAt = createdAt
        )
    }
}