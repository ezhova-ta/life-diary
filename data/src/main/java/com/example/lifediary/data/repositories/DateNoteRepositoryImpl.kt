package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.DateNoteLocalDataSource
import com.example.lifediary.data.db.models.DateNoteEntity
import com.example.lifediary.data.repositories.mappers.db.DateNoteEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.DateNoteEntityMapper.toEntity
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.repositories.DateNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateNoteRepositoryImpl @Inject constructor(
    private val localDataSource: DateNoteLocalDataSource
) : DateNoteRepository {
    override fun getNoteFlow(day: Day): Flow<DateNote?> {
        return localDataSource.getNoteFlow(day.dayNumber, day.monthNumber, day.year).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun getNote(day: Day): DateNote? {
        return localDataSource.getNote(day.dayNumber, day.monthNumber, day.year)?.toDomain()
    }

    override fun getFlowAllNotes(): Flow<List<DateNote>> {
        return localDataSource.getFlowAllNotes().toDomain()
    }

    override suspend fun getAllNotes(): List<DateNote> {
        return localDataSource.getAllNotes().toDomain()
    }

    private fun Flow<List<DateNoteEntity>>.toDomain(): Flow<List<DateNote>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<DateNoteEntity>.toDomain(): List<DateNote> {
        return map { entity -> entity.toDomain() }
    }

    override suspend fun addNote(text: String, day: Day) {
        localDataSource.addNote(DateNote(text = text, day = day).toEntity())
    }

    override suspend fun addAllNotes(notes: List<DateNote>) {
        localDataSource.addAllNotes(notes.toEntity())
    }

    private fun List<DateNote>.toEntity(): List<DateNoteEntity> {
        return map { domain -> domain.toEntity() }
    }

    override suspend fun updateNote(note: DateNote) {
        localDataSource.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    override suspend fun clearAllNotes() {
        localDataSource.clearAllNotes()
    }
}