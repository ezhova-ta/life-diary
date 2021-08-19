package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.NotesLocalDataSource
import com.example.lifediary.data.domain.Notes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val localDataSource: NotesLocalDataSource
) {
    fun getNotesLiveData(): LiveData<Notes?> {
        return localDataSource.getNotesLiveData()
    }

    fun getNotes(): Notes? {
        return localDataSource.getNotes()
    }

    suspend fun saveNotes(notes: Notes) {
        localDataSource.saveNotes(notes)
    }

    suspend fun saveNotes(text: String) {
        localDataSource.saveNotes(Notes(text = text))
    }
}