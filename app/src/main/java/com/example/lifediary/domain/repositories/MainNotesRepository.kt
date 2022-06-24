package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.MainNote

interface MainNotesRepository {
	fun getNotes(): LiveData<List<MainNote>>
	fun getSortMethodId(): LiveData<Int?>
	suspend fun getNote(id: Long): MainNote?
	suspend fun addNote(text: String)
	suspend fun updateNote(item: MainNote)
	suspend fun clearNotes()
	suspend fun deleteNote(id: Long)
	suspend fun saveSortMethodId(id: Int)
}