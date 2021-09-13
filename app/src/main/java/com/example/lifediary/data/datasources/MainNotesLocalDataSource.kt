package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.MainNotesDao
import com.example.lifediary.data.db.entities.MainNoteEntity
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class MainNotesLocalDataSource @Inject constructor(private val dao: MainNotesDao) {
    fun getNotes(): LiveData<List<MainNote>> {
        return dao.getAll().toDomain()
    }

    suspend fun getNote(id: Long): MainNote? {
        return dao.get(id)?.toDomain()
    }

    suspend fun addNote(item: MainNote) {
        dao.insert(MainNoteEntity.fromDomain(item))
    }

    suspend fun updateNote(item: MainNote) {
        dao.update(MainNoteEntity.fromDomain(item))
    }

    suspend fun clearNotes() {
        dao.deleteAll()
    }

    suspend fun deleteNote(id: Long) {
        dao.delete(id)
    }
}