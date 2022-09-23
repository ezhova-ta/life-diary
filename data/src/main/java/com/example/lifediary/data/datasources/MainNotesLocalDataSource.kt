package com.example.lifediary.data.datasources

import com.example.lifediary.data.CommonDataStoreManager
import com.example.lifediary.data.db.dao.MainNotesDao
import com.example.lifediary.data.db.models.MainNoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNotesLocalDataSource @Inject constructor(
    private val dao: MainNotesDao,
    private val commonDataStoreManager: CommonDataStoreManager
    ) {
    fun getNotes(): Flow<List<MainNoteEntity>> {
        return dao.getFlowAll()
    }

    fun getMainNoteListSortMethodId(): Flow<Int?> {
        return commonDataStoreManager.mainNoteListSortMethodId
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