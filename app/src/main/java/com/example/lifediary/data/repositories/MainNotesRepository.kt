package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.MainNotesLocalDataSource
import com.example.lifediary.data.domain.MainNote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNotesRepository @Inject constructor(
    private val localDataSource: MainNotesLocalDataSource
) {
    fun getNotes(): LiveData<List<MainNote>> {
        return localDataSource.getNotes()
    }

    suspend fun getNote(id: Long): MainNote? {
        return localDataSource.getNote(id)
    }

    suspend fun addNote(text: String) {
        localDataSource.addNote(MainNote(text = text))
    }

    suspend fun updateNote(item: MainNote) {
        localDataSource.updateNote(item)
    }

    suspend fun clearNoteList() {
        localDataSource.clearNoteList()
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }
}