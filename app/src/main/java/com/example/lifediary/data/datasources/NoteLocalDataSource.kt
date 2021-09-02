package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.NoteDao
import com.example.lifediary.data.db.entities.NoteEntity
import com.example.lifediary.data.domain.Note
import com.example.lifediary.utils.Day
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(private val dao: NoteDao) {
    fun getNoteLiveData(day: Day): LiveData<Note?> {
        return dao.getLiveData(day.dayNumber, day.monthNumber, day.year).map { it?.toDomain() }
    }

    fun getNote(day: Day): Note? {
        return dao.get(day.dayNumber, day.monthNumber, day.year)?.toDomain()
    }

    fun getAllNote(): LiveData<List<Note>> {
        return dao.getAll().toDomain()
    }

    private fun LiveData<List<NoteEntity>>.toDomain(): LiveData<List<Note>> {
        return map { noteEntityList ->
            noteEntityList.map { noteEntity ->
                noteEntity.toDomain()
            }
        }
    }

    suspend fun addNote(note: Note) {
        dao.insert(NoteEntity.fromDomain(note))
    }

    suspend fun updateNote(note: Note) {
        dao.update(NoteEntity.fromDomain(note))
    }

    suspend fun deleteNote(id: Long) {
        dao.delete(id)
    }
}