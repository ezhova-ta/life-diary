package com.example.lifediary.data.datasources

import com.example.lifediary.data.db.dao.DateNoteDao
import com.example.lifediary.data.db.models.DateNoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateNoteLocalDataSource @Inject constructor(private val dao: DateNoteDao) {
    fun getNoteFlow(dayNumber: Int, monthNumber: Int, year: Int): Flow<DateNoteEntity?> {
        return dao.getFlow(dayNumber, monthNumber, year)
    }

    suspend fun getNote(dayNumber: Int, monthNumber: Int, year: Int): DateNoteEntity? {
        return dao.get(dayNumber, monthNumber, year)
    }

    fun getAllNotes(): Flow<List<DateNoteEntity>> {
        return dao.getFlowAll()
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