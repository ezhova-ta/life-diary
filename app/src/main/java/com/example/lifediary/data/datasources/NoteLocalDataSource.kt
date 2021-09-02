package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.NoteDao
import com.example.lifediary.data.db.entities.DateNoteEntity
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.utils.Day
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(private val dao: NoteDao) {
    fun getNoteLiveData(day: Day): LiveData<DateNote?> {
        return dao.getLiveData(day.dayNumber, day.monthNumber, day.year).map { it?.toDomain() }
    }

    fun getNote(day: Day): DateNote? {
        return dao.get(day.dayNumber, day.monthNumber, day.year)?.toDomain()
    }

    fun getAllNote(): LiveData<List<DateNote>> {
        return dao.getAll().toDomain()
    }

    private fun LiveData<List<DateNoteEntity>>.toDomain(): LiveData<List<DateNote>> {
        return map { noteEntityList ->
            noteEntityList.map { noteEntity ->
                noteEntity.toDomain()
            }
        }
    }

    suspend fun addNote(note: DateNote) {
        dao.insert(DateNoteEntity.fromDomain(note))
    }

    suspend fun updateNote(note: DateNote) {
        dao.update(DateNoteEntity.fromDomain(note))
    }

    suspend fun deleteNote(id: Long) {
        dao.delete(id)
    }
}