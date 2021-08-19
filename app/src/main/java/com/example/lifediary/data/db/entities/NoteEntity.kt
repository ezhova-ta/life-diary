package com.example.lifediary.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey
    val id: Long?,
    val text: String
) {
    companion object {
        fun fromDomain(note: Note): NoteEntity {
            return NoteEntity(id = note.id, text = note.text)
        }
    }

    fun toDomain(): Note {
        return Note(id = id, text = text)
    }
}
