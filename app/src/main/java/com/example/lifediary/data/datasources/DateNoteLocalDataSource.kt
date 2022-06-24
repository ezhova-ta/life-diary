package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.DateNoteDao
import com.example.lifediary.data.db.entities.DateNoteEntity
import javax.inject.Inject

class DateNoteLocalDataSource @Inject constructor(private val dao: DateNoteDao) {
    fun getNoteLiveData(dayNumber: Int, monthNumber: Int, year: Int): LiveData<DateNoteEntity?> {
        return dao.getLiveData(dayNumber, monthNumber, year)
    }

    suspend fun getNote(dayNumber: Int, monthNumber: Int, year: Int): DateNoteEntity? {
        return dao.get(dayNumber, monthNumber, year)
    }

    fun getAllNotes(): LiveData<List<DateNoteEntity>> {
        return dao.getAll()
    }

    suspend fun addNote(note: DateNoteEntity) {
        dao.insert(note)
    }

    suspend fun updateNote(note: DateNoteEntity) {
        dao.update(note)
    }

    suspend fun deleteNote(id: Long) {
        dao.delete(id)
    }

    suspend fun clearAllNotes() {
        dao.deleteAll()
    }
}