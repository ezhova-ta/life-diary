package com.example.lifediary.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.entities.DbEntity
import com.example.lifediary.data.domain.MemorableDate
import java.util.*

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

fun List<String>.isAllItemsBlank(): Boolean {
	forEach { if(it.isNotBlank()) return false }
	return true
}

fun List<MemorableDate>.sortBasedToday(): List<MemorableDate> {
	if(isEmpty()) return this

	val nearestDateInThisMonth = find { it.monthNumber == getNowMonthNumber() && it.dayNumber >= getNowDayNumber() }
	nearestDateInThisMonth?.let { return splitAndSwap(indexOf(it)) }

	val nearestDate = find { it.monthNumber > getNowMonthNumber() }
	nearestDate?.let { return splitAndSwap(indexOf(it)) }

	return this
}

private fun <T> List<T>.splitAndSwap(splitIndex: Int): List<T> {
	val head = this.subList(0, splitIndex)
	val tail = this.subList(splitIndex, lastIndex + 1)
	return tail.plus(head)
}

fun String.startWithCapitalLetter(): String {
	return when(length) {
		0 -> this
		1 -> toUpperCase(Locale.getDefault())
		else -> {
			val firstLetterInUpperCase = substring(0, 1).toUpperCase(Locale.getDefault())
			val otherLettersInLowercase = substring(1).toLowerCase(Locale.getDefault())
			firstLetterInUpperCase + otherLettersInLowercase
		}
	}
}