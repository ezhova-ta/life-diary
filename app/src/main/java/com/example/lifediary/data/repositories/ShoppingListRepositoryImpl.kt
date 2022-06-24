package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.ShoppingListLocalDataSource
import com.example.lifediary.domain.models.ShoppingListItem
import com.example.lifediary.domain.repositories.ShoppingListRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepositoryImpl @Inject constructor(
    private val localDataSource: ShoppingListLocalDataSource
) : ShoppingListRepository {
    override fun getShoppingList(): LiveData<List<ShoppingListItem>> {
        return localDataSource.getShoppingList()
    }

    override fun getShoppingListSortMethodId(): LiveData<Int?> {
        return localDataSource.getShoppingListSortMethodId()
    }

    override suspend fun addShoppingListItem(item: ShoppingListItem) {
        localDataSource.addShoppingListItem(item)
    }

    override suspend fun clearShoppingList() {
        localDataSource.clearShoppingList()
    }

    override suspend fun inverseShoppingListItemPriority(id: Long) {
        localDataSource.inverseShoppingListItemPriority(id)
    }

    override suspend fun inverseShoppingListItemCrossedOut(id: Long) {
        localDataSource.inverseListItemCrossedOut(id)
    }

    override suspend fun deleteShoppingListItem(id: Long) {
        localDataSource.deleteShoppingListItem(id)
    }

    override suspend fun saveShoppingListSortMethodId(id: Int) {
        localDataSource.setShoppingListSortMethodId(id)
    }
}