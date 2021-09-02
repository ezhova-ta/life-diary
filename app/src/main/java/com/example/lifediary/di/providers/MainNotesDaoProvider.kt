package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.MainNotesDao
import javax.inject.Inject
import javax.inject.Provider

class MainNotesDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<MainNotesDao> {

    override fun get(): MainNotesDao {
        return mainDataBase.mainNotesDao()
    }
}