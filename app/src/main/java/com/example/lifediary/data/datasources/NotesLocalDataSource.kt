package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.NotesDao
import com.example.lifediary.data.db.entities.NotesEntity
import com.example.lifediary.data.domain.Notes
import javax.inject.Inject

class NotesLocalDataSource @Inject constructor(private val dao: NotesDao) {
    fun getNotesLiveData(): LiveData<Notes?> {
        return dao.getLiveData().map { it?.toDomain() }
    }

    fun getNotes(): Notes? {
        return dao.get()?.toDomain()
    }

    suspend fun saveNotes(notes: Notes) {
        dao.delete()
        dao.insert(NotesEntity.fromDomain(notes))
    }
}