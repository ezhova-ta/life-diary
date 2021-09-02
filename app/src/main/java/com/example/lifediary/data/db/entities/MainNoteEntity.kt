package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.MainNote
import java.util.*

@Entity(tableName = "main_note")
@TypeConverters(CalendarConverter::class)
data class MainNoteEntity(
    @PrimaryKey
    val id: Long?,
    val text: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
) {
    companion object {
        fun fromDomain(note: MainNote): MainNoteEntity {
            return MainNoteEntity(
                id = note.id,
                text = note.text,
                createdAt = note.createdAt
            )
        }
    }

    fun toDomain(): MainNote {
        return MainNote(
            id = id,
            text = text,
            createdAt = createdAt
        )
    }
}
