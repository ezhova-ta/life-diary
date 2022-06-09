package com.example.lifediary.data.domain

abstract class CalendarEvent {
    abstract val name: String
    abstract val dayNumber: Int
    abstract val monthNumber: Int
    abstract val year: Int?

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}