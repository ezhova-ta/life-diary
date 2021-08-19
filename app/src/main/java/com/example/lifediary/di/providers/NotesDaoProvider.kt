package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.NotesDao
import javax.inject.Inject
import javax.inject.Provider

class NotesDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<NotesDao> {

    override fun get(): NotesDao {
        return mainDataBase.notesDao()
    }
}