package com.example.lifediary.domain.usecases.shopping_list

import com.example.lifediary.domain.models.ShoppingListItem
import com.example.lifediary.data.repositories.ShoppingListRepository
import javax.inject.Inject

class AddShoppingListItemUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	suspend operator fun invoke(item: ShoppingListItem) {
		shoppingListRepository.addShoppingListItem(item)
	}
}