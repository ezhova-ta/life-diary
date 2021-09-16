package com.example.lifediary.di.providers

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lifediary.data.db.MainDataBase
import javax.inject.Inject
import javax.inject.Provider

class MainDatabaseProvider @Inject constructor(private val context: Context) : Provider<MainDataBase> {
    companion object {
        val MIGRATION_10_11 = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                        "CREATE TABLE memorable_date (" +
                            "id INTEGER, " +
                            "name TEXT NOT NULL, " +
                            "day INTEGER NOT NULL, " +
                            "month INTEGER NOT NULL, " +
                            "year INTEGER NOT NULL, " +
                            "PRIMARY KEY(id)" +
                            ")"
                )
            }
        }
    }

    override fun get(): MainDataBase {
        return Room
            .databaseBuilder(context, MainDataBase::class.java, MainDataBase.DATABASE_NAME)
            .addMigrations(MIGRATION_10_11)
            .build()
    }
}