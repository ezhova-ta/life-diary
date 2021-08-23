package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.NotesDao
import com.example.lifediary.data.db.entities.NotesEntity
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.utils.Day
import javax.inject.Inject

class NotesLocalDataSource @Inject constructor(private val dao: NotesDao) {
    fun getNotesLiveData(day: Day): LiveData<Notes?> {
        return dao.getLiveData(day.dayNumber, day.monthNumber, day.year).map { it?.toDomain() }
    }

    fun getNotes(day: Day): Notes? {
        return dao.get(day.dayNumber, day.monthNumber, day.year)?.toDomain()
    }

    suspend fun addNotes(notes: Notes) {
        dao.insert(NotesEntity.fromDomain(notes))
    }

    suspend fun updateNotes(notes: Notes) {
        dao.update(NotesEntity.fromDomain(notes))
    }

    suspend fun deleteNotes(id: Long) {
        dao.delete(id)
    }
}