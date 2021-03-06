package com.example.lifediary.data.datasources

import com.example.lifediary.data.CommonDataStoreManager
import com.example.lifediary.data.db.dao.ShoppingListDao
import com.example.lifediary.data.db.models.ShoppingListItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListLocalDataSource @Inject constructor(
    private val dao: ShoppingListDao,
    private val commonDataStoreManager: CommonDataStoreManager
) {
    fun getShoppingList(): Flow<List<ShoppingListItemEntity>> {
        return dao.getAll()
    }

    fun getShoppingListSortMethodId(): Flow<Int?> {
        return commonDataStoreManager.shoppingListSortMethodId
    }

    suspend fun addShoppingListItem(item: ShoppingListItemEntity) {
        dao.insert(item)
    }

    suspend fun clearShoppingList() {
        dao.deleteAll()
    }

    suspend fun inverseShoppingListItemPriority(id: Long) {
        val item = dao.get(id) ?: return
        val isHighPriority = item.isHighPriority
        item.isHighPriority = !isHighPriority
        dao.update(item)
    }

    suspend fun inverseListItemCrossedOut(id: Long) {
        val item = dao.get(id) ?: return
        val isCrossedOut = item.isCrossedOut
        item.isCrossedOut = !isCrossedOut
        dao.update(item)
    }

    suspend fun deleteShoppingListItem(id: Long) {
        dao.delete(id)
    }

    suspend fun setShoppingListSortMethodId(id: Int) {
        commonDataStoreManager.setShoppingListSortMethodId(id)
    }
}