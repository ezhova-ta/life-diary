package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.data.repositories.ToDoListRepository
import javax.inject.Inject

class ClearAllToDoListsUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	suspend operator fun invoke() {
		toDoListRepository.clearToDoLists()
	}
}