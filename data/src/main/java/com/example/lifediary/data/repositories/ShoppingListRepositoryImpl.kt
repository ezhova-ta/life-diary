package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.ShoppingListLocalDataSource
import com.example.lifediary.data.db.models.ShoppingListItemEntity
import com.example.lifediary.data.repositories.mappers.db.ShoppingListItemEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.ShoppingListItemEntityMapper.toEntity
import com.example.lifediary.domain.models.ShoppingListItem
import com.example.lifediary.domain.repositories.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepositoryImpl @Inject constructor(
    private val localDataSource: ShoppingListLocalDataSource
) : ShoppingListRepository {
    override fun getShoppingList(): Flow<List<ShoppingListItem>> {
        return localDataSource.getShoppingList().toDomain()
    }

    private fun Flow<List<ShoppingListItemEntity>>.toDomain(): Flow<List<ShoppingListItem>> {
        return map { entityList -> entityList.toDomain() }
    }

    private fun List<ShoppingListItemEntity>.toDomain(): List<ShoppingListItem> {
        return map { entity -> entity.toDomain() }
    }

    override fun getShoppingListSortMethodId(): Flow<Int?> {
        return localDataSource.getShoppingListSortMethodId()
    }

    override suspend fun addShoppingListItem(item: ShoppingListItem) {
        localDataSource.addShoppingListItem(item.toEntity())
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