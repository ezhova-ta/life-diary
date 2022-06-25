package com.example.lifediary.domain.usecases.shopping_list

import com.example.lifediary.domain.models.ShoppingListItem
import com.example.lifediary.domain.repositories.ShoppingListRepository
import com.example.lifediary.domain.utils.sorters.ShoppingListSorter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSortedShoppingListUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	operator fun invoke(): Flow<List<ShoppingListItem>> {
		val originalShoppingListFlow = shoppingListRepository.getShoppingList()
		val sortMethodIdFlow = shoppingListRepository.getShoppingListSortMethodId()

		return originalShoppingListFlow.combine(sortMethodIdFlow) { originalShoppingList, sortMethodId ->
			val sorter = ShoppingListSorter.Factory.getInstance(sortMethodId)
			sorter.sort(originalShoppingList)
		}
	}
}