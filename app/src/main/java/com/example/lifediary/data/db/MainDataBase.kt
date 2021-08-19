package com.example.lifediary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifediary.data.db.dao.CurrentWeatherDao
import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.dao.NoteDao
import com.example.lifediary.data.db.dao.ShoppingListDao
import com.example.lifediary.data.db.entities.LocationEntity
import com.example.lifediary.data.db.entities.NoteEntity
import com.example.lifediary.data.db.entities.ShoppingListItemEntity
import com.example.lifediary.data.db.entities.WeatherEntity

@Database(
    version = 2,
    entities = [
        ShoppingListItemEntity::class,
        LocationEntity::class,
        WeatherEntity::class,
        NoteEntity::class
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
}