package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.repositories.ToDoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllToDoListsUseCase @Inject constructor(
	private val doToDoListRepository: ToDoListRepository
) {
	operator fun invoke(): Flow<List<ToDoListItem>> {
		return doToDoListRepository.getAllToDoLists()
	}
}