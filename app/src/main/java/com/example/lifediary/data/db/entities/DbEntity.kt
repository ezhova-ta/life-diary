package com.example.lifediary.data.db.entities

abstract class DbEntity<T> {
	abstract fun toDomain(): T
}