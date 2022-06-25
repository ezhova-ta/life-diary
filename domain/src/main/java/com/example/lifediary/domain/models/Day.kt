package com.example.lifediary.domain.models

data class Day(
	/**
	 * Int value for day of month, from 1 to 31
	 */
	val dayNumber: Int,
	/**
	 * Int value for month of year, from 1 to 12
	 */
	val monthNumber: Int,
	/**
	 * Int value for year
	 */
	val year: Int
)
