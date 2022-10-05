package com.example.lifediary.data.db

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.lifediary.data.db.dao.*
import com.example.lifediary.data.db.models.*

@Database(
    version = 21,
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
        MenstruationPeriodEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 11, to = 12),
        AutoMigration(from = 12, to = 13),
        AutoMigration(from = 13, to = 14),
        AutoMigration(from = 14, to = 15),
        AutoMigration(from = 15, to = 16),
        AutoMigration(from = 16, to = 17),
        AutoMigration(from = 17, to = 18, spec = MainDataBase.AutoMigrationFrom17To18::class),
        AutoMigration(from = 18, to = 19),
        AutoMigration(from = 19, to = 20),
        AutoMigration(from = 20, to = 21)
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
    abstract fun menstruationPeriodDao(): MenstruationPeriodDao

    @DeleteColumn(tableName = "current_weather", columnName = "short_description")
    @DeleteColumn(tableName = "current_weather", columnName = "description")
    @DeleteColumn(tableName = "current_weather", columnName = "icon")
    @DeleteColumn(tableName = "current_weather", columnName = "pressure")
    @DeleteColumn(tableName = "current_weather", columnName = "wind_speed")
    @DeleteColumn(tableName = "current_weather", columnName = "gust_speed")
    class AutoMigrationFrom17To18: AutoMigrationSpec
}