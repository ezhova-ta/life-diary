package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
	fun getFlowShoppingList(): Flow<List<ShoppingListItem>>
	suspend fun getShoppingList(): List<ShoppingListItem>
	fun getShoppingListSortMethodId(): Flow<Int?>
	suspend fun addShoppingListItem(item: ShoppingListItem)
	suspend fun addShoppingList(list: List<ShoppingListItem>)
	suspend fun clearShoppingList()
	suspend fun inverseShoppingListItemPriority(id: Long)
	suspend fun inverseShoppingListItemCrossedOut(id: Long)
	suspend fun deleteShoppingListItem(id: Long)
	suspend fun saveShoppingListSortMethodId(id: Int)
}