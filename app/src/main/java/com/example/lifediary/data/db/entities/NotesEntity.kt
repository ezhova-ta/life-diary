package com.example.lifediary.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.Notes

@Entity(tableName = "note")
data class NotesEntity(
    @PrimaryKey
    val id: Long?,
    val text: String
) {
    companion object {
        fun fromDomain(notes: Notes): NotesEntity {
            return NotesEntity(id = notes.id, text = notes.text)
        }
    }

    fun toDomain(): Notes {
        return Notes(id = id, text = text)
    }
}
