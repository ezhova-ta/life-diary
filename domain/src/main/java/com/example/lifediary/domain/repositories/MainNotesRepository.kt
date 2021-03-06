package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.MainNote
import kotlinx.coroutines.flow.Flow

interface MainNotesRepository {
	fun getNotes(): Flow<List<MainNote>>
	fun getSortMethodId(): Flow<Int?>
	suspend fun getNote(id: Long): MainNote?
	suspend fun addNote(text: String)
	suspend fun updateNote(item: MainNote)
	suspend fun clearNotes()
	suspend fun deleteNote(id: Long)
	suspend fun saveSortMethodId(id: Int)
}