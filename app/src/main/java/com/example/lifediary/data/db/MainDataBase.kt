package com.example.lifediary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifediary.data.db.dao.ShoppingListDao
import com.example.lifediary.data.db.entities.ShoppingListItemEntity

@Database(version = 1, entities = [ShoppingListItemEntity::class])
abstract class MainDataBase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "main_database"
    }

    abstract fun shoppingListDao(): ShoppingListDao
}