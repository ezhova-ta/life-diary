package com.example.lifediary.di.providers

import android.content.Context
import androidx.room.Room
import com.example.lifediary.data.db.MainDataBase
import javax.inject.Inject
import javax.inject.Provider

class MainDatabaseProvider @Inject constructor(private val context: Context) : Provider<MainDataBase> {
    override fun get(): MainDataBase {
        return Room.databaseBuilder(
            context,
            MainDataBase::class.java,
            MainDataBase.DATABASE_NAME
        ).build()
    }
}