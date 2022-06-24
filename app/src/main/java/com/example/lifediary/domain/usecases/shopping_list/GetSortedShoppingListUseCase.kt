package com.example.lifediary.domain.usecases.shopping_list

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.data.repositories.ShoppingListRepository
import com.example.lifediary.ui.shopping_list.ShoppingListSorter
import com.example.lifediary.utils.livedata.TwoSourceLiveData
import javax.inject.Inject

class GetSortedShoppingListUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	operator fun invoke(): LiveData<List<ShoppingListItem>> {
		return TwoSourceLiveData<List<ShoppingListItem>, Int?, List<ShoppingListItem>>(
			shoppingListRepository.getShoppingList(),
			shoppingListRepository.getShoppingListSortMethodId()
		) { originalList, sortMethodId ->
			originalList ?: return@TwoSourceLiveData emptyList()
			val sorter = ShoppingListSorter.Factory.getInstance(sortMethodId)
			sorter.sort(originalList)
		}
	}
}