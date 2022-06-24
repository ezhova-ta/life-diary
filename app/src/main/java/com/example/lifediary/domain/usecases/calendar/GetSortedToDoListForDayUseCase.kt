package com.example.lifediary.domain.usecases.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.data.repositories.ToDoListRepository
import com.example.lifediary.presentation.ToDoListSorter
import com.example.lifediary.presentation.utils.livedata.TwoSourceLiveData
import javax.inject.Inject

class GetSortedToDoListForDayUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	operator fun invoke(day: Day): LiveData<List<ToDoListItem>> {
		return TwoSourceLiveData<List<ToDoListItem>, Int?, List<ToDoListItem>>(
			toDoListRepository.getToDoList(day),
			toDoListRepository.getToDoListSortMethodId()
		) { originalList, sortMethodId ->
			originalList ?: return@TwoSourceLiveData emptyList()
			val sorter = ToDoListSorter.Factory.getInstance(sortMethodId)
			sorter.sort(originalList)
		}
	}
}