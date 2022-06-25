package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lifediary.data.CommonDataStoreManager
import com.example.lifediary.data.db.dao.MainNotesDao
import com.example.lifediary.data.db.models.MainNoteEntity
import javax.inject.Inject

class MainNotesLocalDataSource @Inject constructor(
    private val dao: MainNotesDao,
    private val commonDataStoreManager: CommonDataStoreManager
    ) {
    fun getNotes(): LiveData<List<MainNoteEntity>> {
        return dao.getAll()
    }

    fun getMainNoteListSortMethodId(): LiveData<Int?> {
        return commonDataStoreManager.mainNoteListSortMethodId.asLiveData()
    }

    suspend fun getNote(id: Long): MainNoteEntity? {
        return dao.get(id)
    }

    suspend fun addNote(item: MainNoteEntity) {
        dao.insert(item)
    }

    suspend fun updateNote(item: MainNoteEntity) {
        dao.update(item)
    }

    suspend fun clearNotes() {
        dao.deleteAll()
    }

    suspend fun deleteNote(id: Long) {
        dao.delete(id)
    }

    suspend fun setMainNoteListSortMethodId(id: Int) {
        commonDataStoreManager.setMainNoteListSortMethodId(id)
    }
}