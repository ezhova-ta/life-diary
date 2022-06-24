package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.datasources.DateNoteLocalDataSource
import com.example.lifediary.data.db.entities.DateNoteEntity
import com.example.lifediary.data.repositories.mappers.DateNoteEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.DateNoteEntityMapper.toEntity
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.repositories.DateNoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateNoteRepositoryImpl @Inject constructor(
    private val localDataSource: DateNoteLocalDataSource
) : DateNoteRepository {
    override fun getNoteLiveData(day: Day): LiveData<DateNote?> {
        return localDataSource.getNoteLiveData(day.dayNumber, day.monthNumber, day.year).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun getNote(day: Day): DateNote? {
        return localDataSource.getNote(day.dayNumber, day.monthNumber, day.year)?.toDomain()
    }

    override fun getAllNotes(): LiveData<List<DateNote>> {
        return localDataSource.getAllNotes().toDomain()
    }

    private fun LiveData<List<DateNoteEntity>>.toDomain(): LiveData<List<DateNote>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<DateNoteEntity>.toDomain(): List<DateNote> {
        return map { entity -> entity.toDomain() }
    }

    override suspend fun addNote(text: String, day: Day) {
        localDataSource.addNote(DateNote(text = text, day = day).toEntity())
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