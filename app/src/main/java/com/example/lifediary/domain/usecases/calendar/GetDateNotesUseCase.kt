package com.example.lifediary.domain.usecases.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.repositories.DateNoteRepository
import javax.inject.Inject

class GetDateNotesUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	operator fun invoke(): LiveData<List<DateNote>> {
		return noteRepository.getAllNotes()
	}
}