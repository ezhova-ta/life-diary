package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.Day
import com.example.lifediary.data.repositories.ToDoListRepository
import javax.inject.Inject

class ClearToDoListForDayUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	suspend operator fun invoke(day: Day) {
		toDoListRepository.clearToDoList(day)
	}
}