package com.example.lifediary.data.db.models

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import java.util.*

@Entity(tableName = "shopping_list", indices = [Index(value = ["text"], unique = true)])
@TypeConverters(CalendarConverter::class)
data class ShoppingListItemEntity(
    @PrimaryKey
    val id: Long?,
    var text: String,
    @ColumnInfo(name = "is_high_priority")
    var isHighPriority: Boolean,
    @ColumnInfo(name = "is_crossed_out")
    var isCrossedOut: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
)