package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.NotesLocalDataSource
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.utils.Day
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val localDataSource: NotesLocalDataSource
) {
    fun getNotesLiveData(day: Day): LiveData<Notes?> {
        return localDataSource.getNotesLiveData(day)
    }

    fun getNotes(day: Day): Notes? {
        return localDataSource.getNotes(day)
    }

    suspend fun addNotes(text: String, day: Day) {
        localDataSource.addNotes(Notes(text = text, day = day))
    }

    suspend fun updateNotes(notes: Notes) {
        localDataSource.updateNotes(notes)
    }

    suspend fun deleteNotes(id: Long) {
        localDataSource.deleteNotes(id)
    }
}