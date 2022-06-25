package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
	fun getShoppingList(): Flow<List<ShoppingListItem>>
	fun getShoppingListSortMethodId(): Flow<Int?>
	suspend fun addShoppingListItem(item: ShoppingListItem)
	suspend fun clearShoppingList()
	suspend fun inverseShoppingListItemPriority(id: Long)
	suspend fun inverseShoppingListItemCrossedOut(id: Long)
	suspend fun deleteShoppingListItem(id: Long)
	suspend fun saveShoppingListSortMethodId(id: Int)
}