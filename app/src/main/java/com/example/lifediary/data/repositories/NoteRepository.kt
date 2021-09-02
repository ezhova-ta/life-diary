package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.NoteLocalDataSource
import com.example.lifediary.data.domain.Note
import com.example.lifediary.utils.Day
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val localDataSource: NoteLocalDataSource
) {
    fun getNoteLiveData(day: Day): LiveData<Note?> {
        return localDataSource.getNoteLiveData(day)
    }

    fun getNote(day: Day): Note? {
        return localDataSource.getNote(day)
    }

    fun getAllNote(): LiveData<List<Note>> {
        return localDataSource.getAllNote()
    }

    suspend fun addNote(text: String, day: Day) {
        localDataSource.addNote(Note(text = text, day = day))
    }

    suspend fun updateNote(note: Note) {
        localDataSource.updateNote(note)
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }
}