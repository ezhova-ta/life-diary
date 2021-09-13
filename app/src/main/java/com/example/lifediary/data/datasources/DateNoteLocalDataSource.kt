package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.DateNoteDao
import com.example.lifediary.data.db.entities.DateNoteEntity
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class DateNoteLocalDataSource @Inject constructor(private val dao: DateNoteDao) {
    fun getNoteLiveData(day: Day): LiveData<DateNote?> {
        return dao.getLiveData(day.dayNumber, day.monthNumber, day.year).map { it?.toDomain() }
    }

    fun getNote(day: Day): DateNote? {
        return dao.get(day.dayNumber, day.monthNumber, day.year)?.toDomain()
    }

    fun getAllNotes(): LiveData<List<DateNote>> {
        return dao.getAll().toDomain()
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

    suspend fun clearNotes() {
        dao.deleteAll()
    }
}