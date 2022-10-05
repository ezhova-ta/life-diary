package com.example.lifediary.data.db.models

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import java.util.*

@Entity(
    tableName = "main_note",
    indices = [Index(value = ["text"], unique = true)]
)
@TypeConverters(CalendarConverter::class)
data class MainNoteEntity(
    @PrimaryKey
    val id: Long?,
    val text: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
)
