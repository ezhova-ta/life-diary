package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import kotlinx.coroutines.flow.Flow

interface DateNoteRepository {
	fun getNoteFlow(day: Day): Flow<DateNote?>
	suspend fun getNote(day: Day): DateNote?
	fun getFlowAllNotes(): Flow<List<DateNote>>
	suspend fun getAllNotes(): List<DateNote>
	suspend fun addNote(text: String, day: Day)
	suspend fun addAllNotes(notes: List<DateNote>)
	suspend fun updateNote(note: DateNote)
	suspend fun deleteNote(id: Long)
	suspend fun clearAllNotes()
}