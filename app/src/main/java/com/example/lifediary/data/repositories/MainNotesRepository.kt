package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.MainNotesLocalDataSource
import com.example.lifediary.domain.models.MainNote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNotesRepository @Inject constructor(
    private val localDataSource: MainNotesLocalDataSource
) {
    fun getNotes(): LiveData<List<MainNote>> {
        return localDataSource.getNotes()
    }

    fun getSortMethodId(): LiveData<Int?> {
        return localDataSource.getMainNoteListSortMethodId()
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

    suspend fun clearNotes() {
        localDataSource.clearNotes()
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    suspend fun saveSortMethodId(id: Int) {
        localDataSource.setMainNoteListSortMethodId(id)
    }
}