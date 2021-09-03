package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.DateNoteDao
import javax.inject.Inject
import javax.inject.Provider

class DateNoteDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<DateNoteDao> {

    override fun get(): DateNoteDao {
        return mainDataBase.noteDao()
    }
}