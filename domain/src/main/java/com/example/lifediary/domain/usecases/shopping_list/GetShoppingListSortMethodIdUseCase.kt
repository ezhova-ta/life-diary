package com.example.lifediary.domain.usecases.shopping_list

import com.example.lifediary.domain.repositories.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingListSortMethodIdUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	operator fun invoke(): Flow<Int?> {
		return shoppingListRepository.getShoppingListSortMethodId()
	}
}