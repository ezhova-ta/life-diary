package com.example.lifediary.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifediary.data.db.dao.*
import com.example.lifediary.data.db.entities.*

@Database(
    version = 16,
    exportSchema = true,
    entities = [
        ShoppingListItemEntity::class,
        LocationEntity::class,
        WeatherEntity::class,
        DateNoteEntity::class,
        PostAddressEntity::class,
        MainNoteEntity::class,
        ToDoListItemEntity::class,
        MemorableDateEntity::class,
        MenstruationDatesEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 11, to = 12),
        AutoMigration(from = 12, to = 13),
        AutoMigration(from = 13, to = 14),
        AutoMigration(from = 14, to = 15),
        AutoMigration(from = 15, to = 16)
    ]
)
abstract class MainDataBase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "main_database"
    }

    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun locationDao(): LocationDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun noteDao(): DateNoteDao
    abstract fun mainNotesDao(): MainNotesDao
    abstract fun postAddressDao(): PostAddressDao
    abstract fun toDoListDao(): ToDoListDao
    abstract fun memorableDateDao(): MemorableDateDao
    abstract fun menstruationDatesDao(): MenstruationDatesDao
}