package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.MainNotesLocalDataSource
import com.example.lifediary.data.db.models.MainNoteEntity
import com.example.lifediary.data.repositories.mappers.db.MainNoteEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.MainNoteEntityMapper.toEntity
import com.example.lifediary.domain.models.MainNote
import com.example.lifediary.domain.repositories.MainNotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNotesRepositoryImpl @Inject constructor(
    private val localDataSource: MainNotesLocalDataSource
) : MainNotesRepository {
    override fun getNotes(): Flow<List<MainNote>> {
        return localDataSource.getNotes().toDomain()
    }

    private fun Flow<List<MainNoteEntity>>.toDomain(): Flow<List<MainNote>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<MainNoteEntity>.toDomain(): List<MainNote> {
        return map { entity -> entity.toDomain() }
    }

    override fun getSortMethodId(): Flow<Int?> {
        return localDataSource.getMainNoteListSortMethodId()
    }

    override suspend fun getNote(id: Long): MainNote? {
        return localDataSource.getNote(id)?.toDomain()
    }

    override suspend fun addNote(text: String) {
        localDataSource.addNote(MainNote(text = text).toEntity())
    }

    override suspend fun updateNote(item: MainNote) {
        localDataSource.updateNote(item.toEntity())
    }

    override suspend fun clearNotes() {
        localDataSource.clearNotes()
    }

    override suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    override suspend fun saveSortMethodId(id: Int) {
        localDataSource.setMainNoteListSortMethodId(id)
    }
}