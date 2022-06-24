package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day

interface DateNoteRepository {
	fun getNoteLiveData(day: Day): LiveData<DateNote?>
	suspend fun getNote(day: Day): DateNote?
	fun getAllNotes(): LiveData<List<DateNote>>
	suspend fun addNote(text: String, day: Day)
	suspend fun updateNote(note: DateNote)
	suspend fun deleteNote(id: Long)
	suspend fun clearAllNotes()
}