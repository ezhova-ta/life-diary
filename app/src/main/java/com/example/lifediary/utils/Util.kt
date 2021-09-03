package com.example.lifediary.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.entities.DbEntity

fun Int.createStringWithPlusOrMinusSign(): String {
	if(this < 0) return toString()
	return String.format("+%d", this)
}

fun <T : DbEntity<R>, R> LiveData<List<T>>.toDomain(): LiveData<List<R>> {
	return map { entityList ->
		entityList.map { entity ->
			entity.toDomain()
		}
	}
}