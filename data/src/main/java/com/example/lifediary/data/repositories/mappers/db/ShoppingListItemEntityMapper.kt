package com.example.lifediary.data.repositories.mappers.db

import com.example.lifediary.data.db.models.ShoppingListItemEntity
import com.example.lifediary.domain.models.ShoppingListItem

object ShoppingListItemEntityMapper : EntityMapper<ShoppingListItemEntity, ShoppingListItem> {
	override fun ShoppingListItemEntity.toDomain(): ShoppingListItem {
		return ShoppingListItem(
			id = id,
			text = text,
			isHighPriority = isHighPriority,
			isCrossedOut = isCrossedOut,
			createdAt = createdAt
		)
	}

	override fun ShoppingListItem.toEntity(): ShoppingListItemEntity {
		return ShoppingListItemEntity(
			id = id,
			text = text,
			isHighPriority = isHighPriority,
			isCrossedOut = isCrossedOut,
			createdAt = createdAt
		)
	}
}