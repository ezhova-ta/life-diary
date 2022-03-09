package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.DateNoteLocalDataSource
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.data.domain.Day
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateNoteRepository @Inject constructor(
    private val localDataSource: DateNoteLocalDataSource
) {
    fun getNoteLiveData(day: Day): LiveData<DateNote?> {
        return localDataSource.getNoteLiveData(day)
    }

    fun getNote(day: Day): DateNote? {
        return localDataSource.getNote(day)
    }

    fun getAllNotes(): LiveData<List<DateNote>> {
        return localDataSource.getAllNotes()
    }

    suspend fun addNote(text: String, day: Day) {
        localDataSource.addNote(DateNote(text = text, day = day))
    }

    suspend fun updateNote(note: DateNote) {
        localDataSource.updateNote(note)
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    suspend fun clearNotes() {
        localDataSource.clearNotes()
    }
}