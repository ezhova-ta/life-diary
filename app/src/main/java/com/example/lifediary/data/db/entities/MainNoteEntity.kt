package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lifediary.data.db.converters.CalendarConverter
import java.util.*

@Entity(tableName = "main_note")
@TypeConverters(CalendarConverter::class)
data class MainNoteEntity(
    @PrimaryKey
    val id: Long?,
    val text: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
)
