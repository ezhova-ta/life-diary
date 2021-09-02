package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.NoteDao
import javax.inject.Inject
import javax.inject.Provider

class NoteDaoProvider @Inject constructor(
    private val mainDataBase: MainDataBase
) : Provider<NoteDao> {

    override fun get(): NoteDao {
        return mainDataBase.noteDao()
    }
}