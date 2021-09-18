package com.example.lifediary.utils

abstract class CalendarEvent {
    abstract var name: String
    abstract var dayNumber: Int
    abstract var monthNumber: Int
    abstract var year: Int?

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}