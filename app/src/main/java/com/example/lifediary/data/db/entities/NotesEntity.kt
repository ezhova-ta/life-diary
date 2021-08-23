package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.utils.Day

@Entity(tableName = "note")
data class NotesEntity(
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
        fun fromDomain(notes: Notes): NotesEntity {
            return NotesEntity(
                id = notes.id,
                text = notes.text,
                dayNumber = notes.day.dayNumber,
                monthNumber = notes.day.monthNumber,
                year = notes.day.year
            )
        }
    }

    fun toDomain(): Notes {
        return Notes(
            id = id,
            text = text,
            day = Day(dayNumber, monthNumber, year)
        )
    }
}
