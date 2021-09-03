package com.example.lifediary.data.db.entities

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.ShoppingListItem
import java.util.*

@Entity(tableName = "shopping_list", indices = [Index(value = ["text"], unique = true)])
@TypeConverters(CalendarConverter::class)
data class ShoppingListItemEntity(
    @PrimaryKey
    val id: Long?,
    var text: String,
    @ColumnInfo(name = "is_high_priority")
    var isHighPriority: Boolean,
    @ColumnInfo(name = "is_crossed_out")
    var isCrossedOut: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
) : DbEntity<ShoppingListItem>() {
    companion object {
        fun fromDomain(shoppingListItem: ShoppingListItem): ShoppingListItemEntity {
            return ShoppingListItemEntity(
                id = shoppingListItem.id,
                text = shoppingListItem.text,
                isHighPriority = shoppingListItem.isHighPriority,
                isCrossedOut = shoppingListItem.isCrossedOut,
                createdAt = shoppingListItem.createdAt
            )
        }
    }

    override fun toDomain(): ShoppingListItem {
        return ShoppingListItem(
            id = id,
            text = text,
            isHighPriority = isHighPriority,
            isCrossedOut = isCrossedOut,
            createdAt = createdAt
        )
    }
}