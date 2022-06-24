package com.example.lifediary.data.repositories.mappers

/**
 * Converts a data store entity to a domain entity and vice versa
 *
 * @param T data store entity type
 * @param R domain entity type
 */
interface EntityMapper<T, R> {
	fun T.toDomain(): R
	fun R.toEntity(): T
}