package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.MainNoteLocalDataSource
import com.example.lifediary.data.domain.MainNote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNoteRepository @Inject constructor(
    private val localDataSource: MainNoteLocalDataSource
) {
    fun getNotes(): LiveData<List<MainNote>> {
        return localDataSource.getNotes()
    }

    suspend fun saveNote(item: MainNote) {
        localDataSource.saveNote(item)
    }

    suspend fun clearNoteList() {
        localDataSource.clearNoteList()
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }
}