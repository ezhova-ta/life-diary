package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.Note
import com.example.lifediary.utils.Day

@Entity(tableName = "note")
data class NoteEntity(
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
        fun fromDomain(note: Note): NoteEntity {
            return NoteEntity(
                id = note.id,
                text = note.text,
                dayNumber = note.day.dayNumber,
                monthNumber = note.day.monthNumber,
                year = note.day.year
            )
        }
    }

    fun toDomain(): Note {
        return Note(
            id = id,
            text = text,
            day = Day(dayNumber, monthNumber, year)
        )
    }
}
