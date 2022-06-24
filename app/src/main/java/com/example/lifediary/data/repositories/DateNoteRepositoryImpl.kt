package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.DateNoteLocalDataSource
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.repositories.DateNoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateNoteRepositoryImpl @Inject constructor(
    private val localDataSource: DateNoteLocalDataSource
) : DateNoteRepository {
    override fun getNoteLiveData(day: Day): LiveData<DateNote?> {
        return localDataSource.getNoteLiveData(day)
    }

    override suspend fun getNote(day: Day): DateNote? {
        return localDataSource.getNote(day)
    }

    override fun getAllNotes(): LiveData<List<DateNote>> {
        return localDataSource.getAllNotes()
    }

    override suspend fun addNote(text: String, day: Day) {
        localDataSource.addNote(DateNote(text = text, day = day))
    }

    override suspend fun updateNote(note: DateNote) {
        localDataSource.updateNote(note)
    }

    override suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    override suspend fun clearAllNotes() {
        localDataSource.clearAllNotes()
    }
}