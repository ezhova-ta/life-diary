package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.utils.Day

@Entity(tableName = "note")
data class DateNoteEntity(
    @PrimaryKey
    val id: Long?,
    val text: String,
    @ColumnInfo(name = "day")
    val dayNumber: Int,
    @ColumnInfo(name = "month")
    val monthNumber: Int,
    val year: Int
) {
    companion object {
        fun fromDomain(note: DateNote): DateNoteEntity {
            return DateNoteEntity(
                id = note.id,
                text = note.text,
                dayNumber = note.day.dayNumber,
                monthNumber = note.day.monthNumber,
                year = note.day.year
            )
        }
    }

    fun toDomain(): DateNote {
        return DateNote(
            id = id,
            text = text,
            day = Day(dayNumber, monthNumber, year)
        )
    }
}