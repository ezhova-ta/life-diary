package com.example.lifediary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifediary.data.db.dao.*
import com.example.lifediary.data.db.entities.*

@Database(
    version = 7,
    entities = [
        ShoppingListItemEntity::class,
        LocationEntity::class,
        WeatherEntity::class,
        NoteEntity::class,
        PostAddressEntity::class
    ]
)
abstract class MainDataBase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "main_database"
    }

    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun locationDao(): LocationDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun noteDao(): NoteDao
    abstract fun postAddressDao(): PostAddressDao
}