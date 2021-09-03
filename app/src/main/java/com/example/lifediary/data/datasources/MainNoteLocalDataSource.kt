package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.MainNotesDao
import com.example.lifediary.data.db.entities.MainNoteEntity
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class MainNoteLocalDataSource @Inject constructor(private val dao: MainNotesDao) {
    fun getNotes(): LiveData<List<MainNote>> {
        return dao.getAll().toDomain()
    }

    suspend fun saveNote(item: MainNote) {
        dao.insert(MainNoteEntity.fromDomain(item))
    }

    suspend fun clearNoteList() {
        dao.deleteAll()
    }

    suspend fun deleteNote(id: Long) {
        dao.delete(id)
    }
}