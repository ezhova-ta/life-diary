package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.repositories.ToDoListRepository
import com.example.lifediary.domain.utils.sorters.ToDoListSorter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSortedToDoListForDayUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	operator fun invoke(day: Day): Flow<List<ToDoListItem>> {
		val originalToDoListFlow = toDoListRepository.getToDoList(day)
		val sortMethodIdFlow = toDoListRepository.getToDoListSortMethodId()

		return originalToDoListFlow.combine(sortMethodIdFlow) { originalToDoList, sortMethodId ->
			val sorter = ToDoListSorter.Factory.getInstance(sortMethodId)
			sorter.sort(originalToDoList)
		}
	}
}