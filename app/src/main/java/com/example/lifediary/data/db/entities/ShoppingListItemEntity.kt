package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.ShoppingListItem

@Entity(tableName = "shopping_list", indices = [Index(value = ["text"], unique = true)])
data class ShoppingListItemEntity(
    @PrimaryKey
    val id: Long?,
    var text: String,
    @ColumnInfo(name = "is_high_priority")
    var isHighPriority: Boolean,
    @ColumnInfo(name = "is_crossed_out")
    var isCrossedOut: Boolean,
    @ColumnInfo(name = "created_timestamp")
    val createdTimestamp: Long
) {
    fun toDomain(): ShoppingListItem {
        return ShoppingListItem(
            id = id,
            text = text,
            isHighPriority = isHighPriority,
            isCrossedOut = isCrossedOut,
            createdTimestamp = createdTimestamp
        )
    }

    companion object {
        fun fromDomain(shoppingListItem: ShoppingListItem): ShoppingListItemEntity {
            return ShoppingListItemEntity(
                shoppingListItem.id,
                shoppingListItem.text,
                shoppingListItem.isHighPriority,
                shoppingListItem.isCrossedOut,
                shoppingListItem.createdTimestamp
            )
        }
    }
}