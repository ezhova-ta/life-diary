package com.example.lifediary.di.providers

import com.example.lifediary.data.db.MainDataBase
import com.example.lifediary.data.db.dao.ToDoListDao
import javax.inject.Inject
import javax.inject.Provider

class ToDoListDaoProvider @Inject constructor(
	private val mainDataBase: MainDataBase
) : Provider<ToDoListDao> {

	override fun get(): ToDoListDao {
		return mainDataBase.toDoListDao()
	}
}